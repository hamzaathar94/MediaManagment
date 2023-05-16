package com.example.mediamanagment.interfaces

import com.example.mediamanagment.DataModels.Audios
import com.example.mediamanagment.DataModels.Images
import com.example.mediamanagment.DataModels.Videos

interface onItemClick {
    fun onClick(images: Images)
    fun onAudioClick(audios: Audios)
    fun onVideoClick(videos: Videos)
}