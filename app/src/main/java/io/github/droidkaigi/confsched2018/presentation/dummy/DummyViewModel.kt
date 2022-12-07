package io.github.droidkaigi.confsched2018.presentation.dummy

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2018.data.db.SessionDatabase
import io.github.droidkaigi.confsched2018.data.repository.DummyRepository
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val dummyRepository: DummyRepository, // 新規にDummyで足したClass
    private val sessionDatabase: SessionDatabase, // 元々DatabaseModuleでprovideしているClass
): ViewModel() {

    fun greet() {
        println("Hello! from DummyViewModel")
    }
}
