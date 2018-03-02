package com.example.radioplayer

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        Player().execute("http://stream.xfmnetwork.com:8613/;stream.mp3")
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class Player : AsyncTask<String, Void, Boolean>() {

        override fun doInBackground(vararg strings: String): Boolean? {
            val prepared: Boolean?
            prepared = try {
                Log.d("POND", strings[0])
                mediaPlayer?.setDataSource(strings[0])
                mediaPlayer?.setOnCompletionListener({ mediaPlayer ->
                    Log.d("POND", mediaPlayer.trackInfo.toString())
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                })
                mediaPlayer?.prepare()
                true
            } catch (e: Exception) {
                Log.e("MyAudioStreamingApp", e.message)
                false
            }
            return prepared
        }

        override fun onPostExecute(aBoolean: Boolean?) {
            super.onPostExecute(aBoolean)
            mediaPlayer?.start()
        }
    }
}
