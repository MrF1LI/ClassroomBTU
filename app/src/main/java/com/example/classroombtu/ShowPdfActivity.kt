package com.example.classroombtu

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.example.classroombtu.databinding.ActivityShowPdfBinding
import java.net.MalformedURLException
import java.net.URL

class ShowPdfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPdfBinding

    private val filepath = "https://firebasestorage.googleapis.com/v0/b/classroombtu-68355.appspot.com/o/BIT-02.2018_%E1%83%9B%E1%83%9D%E1%83%91%E1%83%98%E1%83%9A%E1%83%A3%E1%83%A0%E1%83%98%20%E1%83%90%E1%83%9E%E1%83%9A%E1%83%98%E1%83%99%E1%83%90%E1%83%AA%E1%83%98%E1%83%94%E1%83%91%E1%83%98%20(5).pdf?alt=media&token=51356ece-2dec-4688-a324-b0e854e2bf38"
    private var url: URL? = null
    private lateinit var fileName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPdfBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        try {
            url = URL(filepath)
        } catch (e: MalformedURLException) {
            Log.d("MY_TAG", e.toString())
        }

        fileName = url!!.path
        binding.syllabus.text = fileName

        binding.syllabus.setOnClickListener {
            val request = DownloadManager.Request(Uri.parse(url.toString()))
                .setTitle("Syllabus")
                .setMimeType("application/pdf")

            request.allowScanningByMediaScanner()
            request.setAllowedOverMetered(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

            var dm: DownloadManager? = getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
            dm?.enqueue(request)

        }

    }
}