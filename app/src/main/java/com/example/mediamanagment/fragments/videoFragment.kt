package com.example.mediamanagment.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediamanagment.DataModels.Audios
import com.example.mediamanagment.DataModels.Images
import com.example.mediamanagment.DataModels.Videos
import com.example.mediamanagment.R
import com.example.mediamanagment.adopter.VideoAdopter
import com.example.mediamanagment.databinding.FragmentVideoBinding
import com.example.mediamanagment.factory.mediaFactory
import com.example.mediamanagment.interfaces.onItemClick
import com.example.mediamanagment.repository.mediaRepository
import com.example.vmexternalstorage.viewModel.ImagesViewModel

class videoFragment : Fragment(), onItemClick {
    private var binding : FragmentVideoBinding? = null
    private var recyclerView: RecyclerView? = null
    private var imagesViewModel: ImagesViewModel? = null
    private var popupMenu: PopupMenu? = null
    private var imagesUri: String? = null
    private var imagesTitle: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentVideoBinding.inflate(LayoutInflater.from(context),container,false)
        // Inflate the layout for this fragment
        recyclerView = binding?.recylerView

        val imageRepository = mediaRepository(requireContext())
        imagesViewModel =
            ViewModelProvider(this, mediaFactory(imageRepository)).get(ImagesViewModel::class.java)
        imagesViewModel?.getVideos()
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        imagesViewModel?.getVideoLiveData()?.observe(requireActivity(), Observer {
            recyclerView?.adapter = VideoAdopter(requireContext(),it , imagesViewModel!!, this)
        })



        return binding?.root
    }

    override fun onClick(images: Images) {
        TODO("Not yet implemented")
    }

    override fun onAudioClick(audios: Audios) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onVideoClick(videos: Videos) {

   val playerFragment = ExoplayerFragment.newInstance(videos.url)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fagContainerView,playerFragment)
            ?.addToBackStack(null)
            ?.commit()

/*
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.show_imgdailogue, null)
        builder.setView(view)
        builder.show()
        val title = view.findViewById<TextView>(R.id.imgTxtView)
        val img = view.findViewById<ImageView>(R.id.ViewimageView)
        val btnPopMore = view.findViewById<ImageView>(R.id.btnMore)
        title.text = videos.title
        imagesUri= videos.url
        imagesTitle = videos.title
        Glide.with(requireContext()).load(videos.url).thumbnail(0.1f).centerCrop().into(img)

        btnPopMore.setOnClickListener {
            popUpMenu(btnPopMore)
        }*/
    }

    @SuppressLint("MissingInflatedId")
    private fun popUpMenu(imageView: View) {
        popupMenu = PopupMenu(imageView.context, imageView)
        popupMenu!!.inflate(R.menu.show_menu)
        popupMenu!!.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    Toast.makeText(requireContext(), "Delete is not implemented", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Share -> {
                    try {
                        val share = Intent(Intent.ACTION_SEND)
                        share.type = "image/*"
//                        val path = imagesUri?.let { it1 -> File(it1).absolutePath }
                        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagesUri))
                        startActivity(Intent.createChooser(share, "Share via"))
                        //                       Toast.makeText(applicationContext, "Share is not implemented", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    true
                }
                R.id.Edit -> {
                    val builder = AlertDialog.Builder(requireContext())
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.rename_dailogue, null)
                    builder.setView(view)
                    val alert = builder.show()
                    alert.create()
                    val titleRename = view.findViewById<EditText>(R.id.txtRename)
                    val btnRename = view.findViewById<Button>(R.id.btnRename)
                    titleRename.setText(imagesTitle)
                    btnRename.setOnClickListener {
                        alert.dismiss()
                    }
                    Toast.makeText(requireContext(), "Edit is not implemented", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.Detail -> {
                    Toast.makeText(
                        requireContext(),
                        "Detail is not implemented",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> {
                    false
                }
            }
        }
        popupMenu!!.show()
    }
    fun rename(uri: Uri?, rename: String?) {

        //create content values with new name and update
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, rename)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireContext().getContentResolver().update(uri!!, contentValues, null)
        }
    }
}