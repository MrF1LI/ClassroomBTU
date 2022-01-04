package com.example.classroombtu.fragments.courseinfofragments

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.classroombtu.R
import com.example.classroombtu.databinding.FragmentSyllabusBinding
import com.google.android.material.snackbar.Snackbar
import java.net.MalformedURLException
import java.net.URL

class SyllabusFragment: Fragment(R.layout.fragment_syllabus) {

    private var _binding: FragmentSyllabusBinding? = null
    private val binding get() = _binding!!

    private val filepath = "https://firebasestorage.googleapis.com/v0/b/classroombtu-68355.appspot.com/o/syllabusfiles%2FMobile%20Applications%20-%20Syllabus.pdf?alt=media&token=2c7a0faa-fffa-4fa7-9712-bc152a832a59"
    private var url: URL? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSyllabusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            url = URL(filepath)
        } catch (e: MalformedURLException) {
            Log.d("MY_TAG", e.toString())
        }

        binding.syllabus.setOnClickListener {
            val request = DownloadManager.Request(Uri.parse(url.toString()))
                .setTitle("Syllabus")
                .setMimeType("application/pdf")

            request.allowScanningByMediaScanner()
            request.setAllowedOverMetered(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Syllabus")

            val dm: DownloadManager? = activity?.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager?
            dm?.enqueue(request)
        }

        //

        val onCompleteDownload: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                    intent.extras?.let {
                        Snackbar.make(requireView(), "Download Completed.", Snackbar.LENGTH_LONG)
                            .setAction("Open") {
                                val downloadedFileId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
                                val downloadManager = activity?.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
                                val uri: Uri = downloadManager.getUriForDownloadedFile(downloadedFileId)

                                val intent1 = Intent(Intent.ACTION_VIEW, uri)
                                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                context?.startActivity(intent1)
                            }
                            .show()
                    }
                }
            }
        }

        activity?.registerReceiver(onCompleteDownload, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}