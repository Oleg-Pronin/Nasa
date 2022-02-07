package oleg_pronin.nasa.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import oleg_pronin.nasa.R
import oleg_pronin.nasa.ui.picture.PictureFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureFragment.newInstance())
                .commitNow()
        }
    }
}