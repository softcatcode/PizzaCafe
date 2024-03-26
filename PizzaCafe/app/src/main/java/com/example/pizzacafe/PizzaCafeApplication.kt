package com.example.pizzacafe

import android.app.Application
import com.example.pizzacafe.di.DaggerApplicationComponent

class PizzaCafeApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.create()
    }
}