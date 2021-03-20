package ru.kbs41.kbsdatacollector.retrofit


import android.app.Application
import android.content.Context
import android.media.RingtoneManager
import android.util.Base64
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.*
import retrofit2.*
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.retrofit.models.DataIncome
import ru.kbs41.kbsdatacollector.retrofit.models.DataOutgoing
import ru.kbs41.kbsdatacollector.retrofit.models.SendingStatus
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.dao.ProductDao
import ru.kbs41.kbsdatacollector.room.db.*
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderFullRepository

class ExchangeMaster {


    private var baseUrl: String = ""
    private var auth: String = ""

    private fun fetchData() {
        val settingsDao = App().database.settingsDao()
        val settings = settingsDao.getCurrentSettings()

        //АДРЕС СЕРВЕРА
        baseUrl = if (settings.useHttps == true) {
            "https://"
        } else {
            "http://"
        }
        baseUrl += settings.server + ":" + settings.port

        //АВТОРИЗАЦИЯ
        auth = "Basic " + Base64.encodeToString(
            "${settings.user}:${settings.password}".toByteArray(),
            Base64.NO_WRAP
        )

    }


    private fun notifyAboutNewOrder(context: Context) {
        val CHANNEL_ID = "Download is done"
        val NOTIFICATION_ID = 1

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_list_24)
            .setSound(soundUri)
            .setContentTitle("Новый заказ")
            //.setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Поступили новые заказы")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun getData(application: Application) {

        val context = application.applicationContext
        val deviceId = 1

        RetrofitClient.instance.getData(deviceId)
            ?.enqueue(object : Callback<DataIncome> {
                override fun onResponse(
                    call: Call<DataIncome>,
                    response: Response<DataIncome>
                ) {

                    GlobalScope.launch(Dispatchers.IO) {

                        val body: DataIncome? = response.body()

                        if (body == null) {
                            return@launch
                        }

                        if (body.errorDescription != "") {
                            Log.d("1C_TO_APP", "No data")
                            return@launch
                        }

                        if (response.isSuccessful) {

                            //DATABASE
                            val database = (application as App).database

                            //GOODS / PRODUCTS
                            body.goods?.forEach { insertProduct(it, database) }

                            //ORDERS
                            body.orders?.forEach { insertAssemblyOrder(context, it, database) }

                            //ЕСЛИ ЗАКАЗЫ ВСЁ ТАКИ ПРИШЛИ, ТО ОПОВЕСТИМ ОБ ЭТОМ ПОЛЬЗОВАТЕЛЯ
                            if (body.orders != null) {
                                notifyAboutNewOrder(context)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<DataIncome>, t: Throwable) {
                    Log.d("GET_FROM_1C", "HUEVO")
                }
            })

    }

    private suspend fun insertAssemblyOrder(
        context: Context,
        i: DataIncome.Order,
        database: AppDatabase
    ) {

        val repository = AssemblyOrderFullRepository()

        val assemblyOrderDao = database.assemblyOrderDao()
        val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
        val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()
        val productDao = database.productDao()

        //ПОПРОБУЕМ ПОЛУЧИТЬ ПО ГУИДУ, ЕСЛИ НЕ ПОЛУЧАЕТСЯ, ТО ПРОСТО СОЗДАСТЬСЯ НОВЫЙ ОБЪЕКТ
        var assemblyOrder = assemblyOrderDao.getAssemblyOrderByGuid(i.guid)

        if (assemblyOrder == null) {
            assemblyOrder = AssemblyOrder()
        }

        assemblyOrder.guid = i.guid
        assemblyOrder.date = i.date
        assemblyOrder.number = i.number
        assemblyOrder.counterpart = i.contractor
        assemblyOrder.comment = i.comment

        //ДАЖЕ ЕСЛИ У НАС ВСЁ И ТАК СОБРАНО, ТО СТАТУС СОБРАННОСТИ МЫ СБРАСЫВАЕМ
        assemblyOrder.isCompleted = false
        assemblyOrder.isSent = false

        //СИСТЕМА СДЕЛАЕТ UPDATE, ТАК КАК ПОЛИТИКА РЕШЕНИЯ КОНФЛИКТА - REPLACE
        val orderId = assemblyOrderDao.insert(assemblyOrder)


        //ОЧИЩАЕМ ТАБЛИЦУ ТОВАРОВ, ТАК КАК ОНА МОГЛА ИЗМЕНИТЬСЯ
        //В ДАЛЬНЕЙШЕМ ПОПРОБУЕМ ПЕРЕПРЕВЯЗАТЬ УЖЕ СУЩЕСТВУЮЩИЕ МАРКИ
        assemblyOrderTableGoodsDao.deleteByAssemblyOrderId(orderId)

        i.tableGoods.forEach { tg ->

            //ПОЛУЧИМ ТОВАР ПО ГУИДу
            //ОН НИКОГДА НЕ МОЖЕТ БЫТЬ NULL, ТАК КАК ЕГО УЖЕ ЗАПИСАЛИ В БАЗУ РАНЕЕ
            val product = productDao.getProductByGuid(tg.productSourceId)

            //ДЛЯ НАЧАЛА ПЛОСМОТРИМ ЧТО ТАМ С МАРКАМИ,
            //ДАЛЕЕ ПОПРОБУЕМ ИХ ПЕРЕПРЕВЯЗАТЬ
            //ИЩЕМ ПО ГУИДу, ТАК КАК ID ДОКУМЕНТА МОГ ИЗМЕНИТЬСЯ, А ГУИД ТОЧНО НЕТ


            //СОЗДАДИМ НОВУЮ СТРОКУ В ТАБЛИЦЕ ТОВАРОВ
            val newRowTableGoods = AssemblyOrderTableGoods(
                0,
                tg.sourceGuid,
                tg.rowNumber,
                tg.qty,
                0.0,
                product!!.hasStamp!!,
                orderId,
                product!!.id!!
            )

            //ПОПРОБУЕМ НАЙТИ УЖЕ ПОЛУЧЕННЫЕ МАРКИ, А ЕСЛИ НАХОДИМ, ТО СЧИТАЕМ ИХ КОЛИЧЕСТВО
            //И КОРРЕКТИРУЕМ КОЛИЧЕСТВО ПОДОБРАННЫХ МАРОК qtyCollected
            val tableStamps = repository.getTableStampsByGuidAndProduct(
                assemblyOrder.guid,
                product.id
            )

            //ИЗМЕНИМ КОЛИЧЕСТВО ПОДОБРАННЫХ МАРОК
            newRowTableGoods.qtyCollected = tableStamps.size.toDouble()

            //ЗАПИСЫВАЕМ СТРОКУ В ТАБЛИЦЕ ТОВАРОВ
            assemblyOrderTableGoodsDao.insert(newRowTableGoods)

            //СКОРРЕКТИРУЕМ ПРИНАДЛЕЖАЩИЕ ДОКУМЕНТУ МАРКИ
            tableStamps.forEach { item ->
                item!!.assemblyOrderId = orderId
                assemblyOrderTableStampsDao.run { update(item) }
            }
        }


        //УДАЛИМ ЛИШНИЕ МАРКИ, ПО ТОВАРАМ КОТОРЫЕ НАМ НЕ НУЖНЫ
        //ТАКОЙ СЛУЧАЙ МОЖЕТ ВОЗНИКНУТЬ, КОГДА ЗАКАЗ НА СБОРКУ ПРИХОДИТ ЕЩЁ ОДИН РАЗ
        val tableStamps = assemblyOrderTableStampsDao.getTableStampsByDocId(orderId)
        tableStamps.forEach {

            val tableGoods = assemblyOrderTableGoodsDao.getTableGoodsByDocAndProduct(
                it.assemblyOrderId,
                it.productId
            )

            if (tableGoods.isEmpty()) {
                assemblyOrderTableStampsDao.delete(it)
            }
        }
    }

    private suspend fun insertProduct(i: DataIncome.Good, database: AppDatabase) {

        //НАЗНАЧИМ ПЕРЕМЕННЫЕ ДЛЯ РАБОТЫ С БАЗОЙ ДАННЫХ
        val productDao = database.productDao()
        val barcodeDao = database.barcodeDao()
        val stampsDao = database.stampDao()

        //ПРОБУЕМ НАЙТИ ТОВАР ПО ГУИД
        var product = productDao.getProductByGuid(i.guid)
        if (product == null) {
            product = Product()
        } // оставляем как есть, упрощать здесь не нужно

        //ПОЛУЧИВШИЕЕСЯ ДАННЫЕ ЗАПИШЕМ
        product.name = i.name
        product.unit = i.unit
        product.isAlcohol = i.isAlcohol
        product.hasStamp = i.hasStamp
        product.guid = i.guid
        product.isFolder = i.isFolder
        product.parentGuid = i.parentGuid
        product.parentId = getParentProduct(i.parentGuid, productDao)

        //ОБНОВИМ ИЛИ ДОБАВИМ
        val productId = productDao.insert(product)


        //ШТРИХКОДЫ
        if (i.barcodes != null) {
            i.barcodes.forEach { bc ->
                val barcodes = barcodeDao.getNoteByBarcode(bc.barcode)
                var barcodeId: Long = 0
                if (barcodes.size > 0) {
                    barcodeId = barcodes[0].id
                }

                val barcode: Barcode = Barcode(
                    barcodeId,
                    bc.barcode,
                    productId
                )

                barcodeDao.insert(barcode)
            }

            //STAMPS
            i.stamps.forEach { st ->
                val stamps = stampsDao.getNoteByStamp(st.stamp)
                var stampId: Long = 0
                if (stamps.size > 0) {
                    stampId = stamps[0].id
                }

                val stamp: Stamp = Stamp(
                    stampId,
                    st.stamp,
                    productId
                )

                stampsDao.insert(stamp)
            }
        }
    }

    private fun getParentProduct(guid: String, productDao: ProductDao): Long {
        val product = productDao.getProductByGuid(guid) ?: return 0
        return product.id
    }

    fun sendOrdersTo1C(order: AssemblyOrder) {

        val repository = AssemblyOrderFullRepository()
        val tableGoods = repository.getTableGoodsForSending(order.id)
        val tableStamps = repository.getTableStampsForSending(order.id)

        //ПОДГОТОВИМ ДАННЫЕ ДЛЯ ОТПРАВКИ
        val data = DataOutgoing(
            DataOutgoing.OrderModel(
                order.guid,
                order.date,
                order.number,
                tableGoods,
                tableStamps
            )
        )

        //ПОПРОБУЕМ ОТПРАВИТЬ ДАННЫЕ
        RetrofitClient.instance.sendOrder(data)
            ?.enqueue(object : Callback<SendingStatus> {
                override fun onResponse(
                    call: Call<SendingStatus>,
                    response: Response<SendingStatus>
                ) {

                    Log.d("POST_TO_1C", "PIZDATO")
                }

                override fun onFailure(call: Call<SendingStatus>, t: Throwable) {
                    Log.d("POST_TO_1C", "HUEVO")
                }
            })
    }
}

