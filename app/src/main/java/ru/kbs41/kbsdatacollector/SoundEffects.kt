package ru.kbs41.kbsdatacollector

import android.content.Context
import android.media.MediaPlayer

class SoundEffects {

    suspend fun playError(context: Context){
        val sound = MediaPlayer.create(context, R.raw.error)
        sound.setOnPreparedListener{
            sound.start()
        }
    }

}