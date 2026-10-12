package com.dhruva.auratech

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class AuraTechApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        //only to seed 1 time data to firestore, data seeded from mockdata
        //seedFirestore()
    }

    /*private fun seedFirestore() {
        applicationScope.launch {
            try {
                FirestoreSeeder.seedAllData()
                FirestoreSeeder.seedUserData("test_user_123")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }*/
}