package com.example.vmexternalstorage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediamanagment.DataModels.Audios
import com.example.mediamanagment.repository.mediaRepository
import com.example.mediamanagment.DataModels.Images
import com.example.mediamanagment.DataModels.Videos

class ImagesViewModel(application: Application,private val mediaRepository: mediaRepository) : AndroidViewModel(application) {
        //***********Images***********
    val data: MutableLiveData<List<Images>> = mediaRepository.imagesLiveData

    fun getImages() {
        mediaRepository.getImages()
    }

    fun getImagesLiveData(): LiveData<List<Images>> {
        mediaRepository.getImagesLiveData()
        return data
    }
    //******* Audio**************
    val audio: MutableLiveData<List<Audios>> = mediaRepository.audiLiveData

    fun getAudio() {
        mediaRepository.getAudio()
    }

    fun getAudioLiveData(): LiveData<List<Audios>> {
        mediaRepository.getAudioLiveData()
        return audio
    }

    //******* Videos**************

    val video: MutableLiveData<List<Videos>> = mediaRepository.videoLiveData

    fun getVideos() {
        mediaRepository.getVideos()
    }

    fun getVideoLiveData(): LiveData<List<Videos>> {
        mediaRepository.getVideosLiveData()
        return video
    }


}