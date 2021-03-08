package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val useHttps: Boolean = false,
    val representation: String = "", //Представление коллекции настроек. Предполагается, что настроек может быть много, например для каждой торговой точки
    val server: String = "",
    val port: String = "80",
    val deviceId: Int = 0,
    val user: String = "",
    val password: String = "",
    val isCurrent: Boolean = false
) {
}