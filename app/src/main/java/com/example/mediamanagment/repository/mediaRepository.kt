package com.example.mediamanagment.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediamanagment.DataModels.Audios
import com.example.mediamanagment.DataModels.Images
import com.example.mediamanagment.DataModels.Videos

class mediaRepository(val context: Context) {

    //      *******Images***********
val imagesLiveData = MutableLiveData<List<Images>>()
fun getImages() {
    val images = mutableListOf<Images>()
    val projection = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA)
    val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
    val selcetionArgs = arrayOf("images")
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selcetionArgs,
        sortOrder
    )
    if (cursor != null && cursor.moveToFirst()) {
        do {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            images.add(Images(title,image))
        } while (cursor.moveToNext())
        cursor.close()
    }
    imagesLiveData.postValue(images)
}

fun getImagesLiveData(): LiveData<List<Images>> {
    return imagesLiveData
}


    //*****Audios************

    val audiLiveData = MutableLiveData<List<Audios>>()
    @SuppressLint("Range")
    fun getAudio() {
        val audio = mutableListOf<Audios>()
        // Use content resolver to query for music files on device
        val musicCursor = context.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        // Iterate through cursor to get music file information
        if (musicCursor != null && musicCursor.moveToFirst())  {
            do {
                val title =
                    musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
               // val musicArtist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
               // val musicAlbum =musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC))
                val path = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                // Create Music object and add to list
               audio.add(Audios(title,path))
            }while (musicCursor.moveToNext())
            musicCursor.close()
        }
        audiLiveData.postValue(audio)
    }
    fun getAudioLiveData(): LiveData<List<Audios>> {
        return audiLiveData
    }

    //********Videos*********

    val videoLiveData = MutableLiveData<List<Videos>>()
    @SuppressLint("Range")
    fun getVideos() {
        val videos = mutableListOf<Videos>()
        val projection = arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATA)
        val selection = "${MediaStore.Video.Media.BUCKET_DISPLAY_NAME} = ?"
        val selcetionArgs = arrayOf("WhatsApp Video")
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selcetionArgs,
            sortOrder
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                val image = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                videos.add(Videos(title,image))
            } while (cursor.moveToNext())
            cursor.close()
        }


        // Use content resolver to query for music files on device
/*        val musicCursor = context.contentResolver?.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        // Iterate through cursor to get music file information
        if (musicCursor != null && musicCursor.moveToFirst())  {
            do {
                val title =
                    musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                // val musicArtist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Video.Media.ARTIST))
                // val musicAlbum =musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Video.Media.IS_MUSIC))
                val path = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Video.Media.DATA))

                // Create Music object and add to list
                videos.add(Videos(title,path))
            }while (musicCursor.moveToNext())
            musicCursor.close()
        }*/
        videoLiveData.postValue(videos)
    }
    fun getVideosLiveData(): LiveData<List<Videos>> {
        return videoLiveData
    }

}