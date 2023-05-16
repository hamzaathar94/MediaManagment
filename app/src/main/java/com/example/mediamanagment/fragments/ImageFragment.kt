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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.mediamanagment.DataModels.Audios
import com.example.mediamanagment.R
import com.example.mediamanagment.databinding.FragmentImageBinding
import com.example.mediamanagment.factory.mediaFactory
import com.example.mediamanagment.interfaces.onItemClick
import com.example.mediamanagment.repository.mediaRepository
import com.example.mediamanagment.DataModels.Images
import com.example.mediamanagment.DataModels.Videos
import com.example.vmexternalstorage.adopter.MyAdopter
import com.example.vmexternalstorage.viewModel.ImagesViewModel

class ImageFragment : Fragment(), onItemClick {
        private var binding : FragmentImageBinding? = null
    private var recyclerView: RecyclerView? = null
    private var imagesViewModel: ImagesViewModel? = null
    private var popupMenu: PopupMenu? = null
    private var imagesUri: String? = null
    private var imagesTitle: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentImageBinding.inflate(LayoutInflater.from(context),container,false)
        // Inflate the layout for this fragment
        recyclerView = binding?.recylerView

        val imageRepository = mediaRepository(requireContext())
        imagesViewModel =
            ViewModelProvider(this, mediaFactory(imageRepository)).get(ImagesViewModel::class.java)
        imagesViewModel?.getImages()
        recyclerView?.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        imagesViewModel!!.getImagesLiveData().observe(requireActivity(), Observer { imgList ->
            recyclerView?.adapter = MyAdopter(requireContext(), imgList, imagesViewModel!!, this)
        })



        return binding?.root
    }

    override fun onClick(images: Images) {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.show_imgdailogue, null)
        builder.setView(view)
        builder.show()
        val title = view.findViewById<TextView>(R.id.imgTxtView)
        val img = view.findViewById<ImageView>(R.id.ViewimageView)
        val btnPopMore = view.findViewById<ImageView>(R.id.btnMore)
        title.text = images.title
        imagesUri= images.url
        imagesTitle = images.title
        Glide.with(requireContext()).load(images.url).thumbnail(0.1f).centerCrop().into(img)

        btnPopMore.setOnClickListener {
            popUpMenu(btnPopMore)
        }
    }

    override fun onAudioClick(audios: Audios) {
        TODO("Not yet implemented")
    }

    override fun onVideoClick(videos: Videos) {
        TODO("Not yet implemented")
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