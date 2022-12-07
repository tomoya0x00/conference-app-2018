package io.github.droidkaigi.confsched2018.presentation.dummy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.confsched2018.R

@AndroidEntryPoint
class DummyActivity : AppCompatActivity() {

    private val viewModel: DummyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dummy)
        viewModel.greet()
    }
}