package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autenticacion.databinding.ActivityBienvenidaBinding
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val nombre = extras?.getString("nombreusuario")
        binding.bienvenida.text = "BIENVENID@, $nombre"

        binding.bCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}