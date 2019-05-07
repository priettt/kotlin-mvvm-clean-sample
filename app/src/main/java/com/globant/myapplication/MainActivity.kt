package com.globant.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.globant.di.useCaseModule
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startKoin { modules(useCaseModule) } //we should do this in a base activity
    }
}
