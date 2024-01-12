package com.example.autenticacion

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityBienvenidaBinding
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        val extras = intent.extras
        val nombre = extras?.getString("nombreusuario")
        binding.bienvenida.text = "BIENVENID@, $nombre"

        binding.bCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnGuardarCoche.setOnClickListener {
            // Si ningun campo está vacío:
            if (binding.marca.text.isNotEmpty() && binding.modelo.text.isNotEmpty() &&
                binding.matricula.text.isNotEmpty() && binding.color.text.isNotEmpty()) {
                db.collection("coches")
                    .add(mapOf(
                        "marca" to binding.marca.text.toString(),
                        "modelo" to binding.modelo.text.toString(),
                        "matricula" to binding.matricula.text.toString(),
                        "color" to binding.color.text.toString()))

                    .addOnSuccessListener {documento ->
                        Log.d(TAG, "Nuevo coche añadido con id: ${documento.id}")
                    }

                    .addOnFailureListener {
                        Log.d(TAG, "Error en la inserción del nuevo registro")
                    }
            } else {
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnEditarCoche.setOnClickListener {
            db.collection("coches")
                .whereEqualTo("matricula", binding.matricula.text.toString())
                .get().addOnSuccessListener {
                    it.forEach {
                        binding.marca.setText(it.get("marca") as String?)
                        binding.modelo.setText(it.get("modelo") as String?)
                        binding.color.setText(it.get("color") as String?)
                    }
                }
        }

        binding.btnEliminarCoche.setOnClickListener {
            db.collection("coches")
                .get()
                .addOnSuccessListener {
                    it.forEach {
                        it.reference.delete()
                    }
                }
        }
    }
}