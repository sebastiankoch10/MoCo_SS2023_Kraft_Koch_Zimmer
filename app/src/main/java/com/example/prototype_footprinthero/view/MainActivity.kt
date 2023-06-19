package com.example.prototype_footprinthero.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.example.prototype_footprinthero.model.FirestoreDatabase
import com.example.prototype_footprinthero.model.MainViewModel
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.view.MainScreen
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var firestoreDatabase: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        firestoreDatabase = FirebaseFirestore.getInstance()

        setContent {
            Prototype_FootPrintHeroTheme {
                Surface(color = Color.White) {
                    MainScreen(
                        viewModel = viewModel,
                        firestoreDatabase = firestoreDatabase
                    )
                }
            }
        }
    }
}
