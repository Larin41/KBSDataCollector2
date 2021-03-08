package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val useHttps: Boolean = false,
    val representation: String, //Представление коллекции настроек. Предполагается, что настроек может быть много, например для каждой торговой точки
    val server: String,
    val port: String,
    val deviceId: Int,
    val user: String,
    val password: String
) {
}