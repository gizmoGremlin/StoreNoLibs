package com.pickledpepper.storenolibs

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.firebase.firestore.FirebaseFirestore
import com.pickledpepper.storenolibs.repository.Repository

class StoreApp : Application() {

    init {
        instance = this
    }
    companion object {
        private var instance : StoreApp? = null
        private lateinit var db: FirebaseFirestore
        private lateinit var repo: Repository
        fun applicationContext() : StoreApp{
            return instance as StoreApp
        }
        fun injectRepository() = repo

    }
    override protected fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this);
    }
    override fun onCreate() {
        super.onCreate()

        db = FirebaseFirestore.getInstance()
        repo = Repository(db)

    }
}