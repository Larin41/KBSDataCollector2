package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = false) val id: Long = 1, //ПОКА ЧТО ВСЕГДА 1, ТАК КАК ПОКА БУДЕТ ТОЛЬКО ОДНА ЗАПИСЬ
    var useHttps: Boolean = false,
    var representation: String = "Default", //Представление коллекции настроек. Предполагается, что настроек может быть много, например для каждой торговой точки
    var server: String = "192.168.1.50",
    var port: String = "80",
    var deviceId: Int = 1,
    var user: String = "tsd1",
    var password: String = "4444",
    var isCurrent: Boolean = true //TODO: в релизе сделать false
) {
}