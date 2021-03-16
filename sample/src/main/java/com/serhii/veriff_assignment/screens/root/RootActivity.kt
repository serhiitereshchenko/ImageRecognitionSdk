package com.serhii.veriff_assignment.screens.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.serhii.veriff_assignment.R
import com.serhii.veriff_assignment.screens.recognize.RecognizeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, RecognizeFragment())
                .commit()
        }
    }
}
