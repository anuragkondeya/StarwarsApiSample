package com.kondeyan.swapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kondeyan.swapiapp.ui.SplashScreenFragment
import com.kondeyan.swapiapp.ui.base.ErrorFragment
import com.kondeyan.swapiapp.ui.base.LoadingFragment
import com.kondeyan.swapiapp.ui.base.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        beginSplashScreen()
    }

    private fun beginSplashScreen(){
        supportFragmentManager.beginTransaction()
            .add(R.id.container,SplashScreenFragment.newInstance(),null)
            .commit()
    }

    fun startNewFragment(fragment: Fragment, withName: String? = null, withTag: String? = null) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, withName)
            .addToBackStack(withTag)
            .commit()
    }

    private fun showGenericErrorMessage() = startNewFragment(ErrorFragment.newInstance())

    private fun showGenericLoadingScreen() = startNewFragment(LoadingFragment.newInstance())

    //Default implementation
    fun handleDefaultStates(state: State) {
        when (state) {
            State.ERROR -> showGenericErrorMessage()
            State.LOADING -> showGenericLoadingScreen()
            else -> { }
        }
    }

}