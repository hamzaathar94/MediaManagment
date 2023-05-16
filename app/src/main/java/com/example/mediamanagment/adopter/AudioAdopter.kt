package com.example.mediamanagment.adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediamanagment.DataModels.Audios
import com.example.mediamanagment.DataModels.Images
import com.example.mediamanagment.R
import com.example.mediamanagment.databinding.AudioRecyclerBinding
import com.example.mediamanagment.databinding.RecyclerLayoutBinding
import com.example.mediamanagment.interfaces.onItemClick

class AudioAdopter(val context: Context, var data : List<Audios>, val onItemClick: onItemClick) : RecyclerView.Adapter<AudioAdopter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioAdopter.MyViewHolder {
        val binding = AudioRecyclerBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AudioAdopter.MyViewHolder, position: Int) {
        val data = data[position]
        holder.binding.imgTxt.text = data.title
        holder.binding.imageView.setImageResource(R.drawable.audio_file)
        //val path = File(data.url)
//       Glide.with(context).load(data.url.toString()).thumbnail(0.1f).centerCrop().into(holder.binding.imageView)
        holder.binding.audioRecycler.setOnClickListener {
            onItemClick.onAudioClick(data)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class MyViewHolder(var binding: AudioRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}