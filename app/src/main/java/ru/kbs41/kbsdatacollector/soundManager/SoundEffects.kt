package ru.kbs41.kbsdatacollector.soundManager

import android.content.Context
import android.media.MediaPlayer
import ru.kbs41.kbsdatacollector.R

class SoundEffects {

    suspend fun playError(context: Context){
        val sound = MediaPlayer.create(context, R.raw.error)
        sound.setOnPreparedListener{
            sound.start()
        }
    }

    suspend fun playSuccess(context: Context){
        val sound = MediaPlayer.create(context, R.raw.success)
        sound.setOnPreparedListener{
            sound.start()
        }
    }

}