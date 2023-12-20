package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bIniciarSesion.setOnClickListener {
            login()
        }

        binding.bRegistrar.setOnClickListener {
            startActivity(Intent(this,RegistroActivity::class.java))
        }
    }

    private fun login() {
        if (binding.correo.text.isNotEmpty() && binding.passwd.text.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.correo.text.toString(),
                binding.passwd.text.toString()
            )

                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this,InicioActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this,"Algun campo está vacío", Toast.LENGTH_SHORT).show()
        }
    }
}