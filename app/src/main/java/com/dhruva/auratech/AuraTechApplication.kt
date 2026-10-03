package com.dhruva.auratech

import android.app.Application
import com.dhruva.auratech.data.firestore.FirestoreSeeder
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuraTechApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        //only to seed 1 time data to firestore, data seeded from mockdata
        //seedFirestore()
    }

    private fun seedFirestore() {
        applicationScope.launch {
            try {
                FirestoreSeeder.seedAllData()
                FirestoreSeeder.seedUserData("test_user_123")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}