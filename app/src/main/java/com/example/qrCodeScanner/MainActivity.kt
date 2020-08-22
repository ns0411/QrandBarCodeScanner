package com.example.qrCodeScanner

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var scannerView:ZXingScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        scannerView=findViewById(R.id.scanner)
        init()
    }

     private fun init()
     {
         scannerView.setResultHandler(this)
         scannerView.startCamera()
     }

    override fun handleResult(p: Result?) {

        if(p!=null)
        {
            MaterialAlertDialogBuilder(this,R.style.RoundShapeTheme)
                .setTitle(resources.getString(R.string.result))
                .setMessage(p.text.toString())
                .setNeutralButton(resources.getString(R.string.okay)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.copy)) { dialog, _ ->
                    val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText(label.toString(), p.text.toString())
                    clipboard.setPrimaryClip(clip)
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    init()
                }
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        init()
    }
}