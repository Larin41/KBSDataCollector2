package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = false) val id: Long = 1, //ПОКА ЧТО ВСЕГДА 1, ТАК КАК ПОКА БУДЕТ ТОЛЬКО ОДНА ЗАПИСЬ
    var useHttps: Boolean = false,
    var representation: String = "Default", //Представление коллекции настроек. Предполагается, что настроек может быть много, например для каждой торговой точки
    var server: String = "127.0.0.1",
    var port: String = "80",
    var deviceId: Int = 1,
    var user: String = "",
    var password: String = "",
    var isCurrent: Boolean = false
) {
}