package com.example.accessingandroidfeatures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.accessingandroidfeatures.databinding.ActivityMakeAcallBinding
import com.example.accessingandroidfeatures.databinding.ActivitySpeechToTextBinding
import java.util.*

class SpeechToText : AppCompatActivity() {

    lateinit var speechToTextBinding: ActivitySpeechToTextBinding

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speechToTextBinding = ActivitySpeechToTextBinding.inflate(layoutInflater)
        val view = speechToTextBinding.root
        setContentView(view)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == RESULT_OK && data != null) {
                val speakResult : ArrayList<String> = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                speechToTextBinding.textView.text = speakResult[0]
            }
        })

        speechToTextBinding.imageButton.setOnClickListener {
            convertSpeech()
        }
    }

    fun convertSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        activityResultLauncher.launch(intent)
    }
}