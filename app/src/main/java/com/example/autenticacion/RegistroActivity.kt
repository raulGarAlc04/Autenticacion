package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        binding.bRegistrar.setOnClickListener {
            // Comprobamos que ningun campo está vacío
            if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty() && binding.nombre.text.isNotEmpty() && binding.apellidos.text.isNotEmpty()) {
                // Accedemos a Firebase, con el metodo createuser... y le pasamos el correo
                // y la constraseña
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        db.collection("usuarios").document(binding.email.text.toString())
                            .set(mapOf(
                                "nombre" to binding.nombre.text.toString(),
                                "apellidos" to binding.apellidos.text.toString()
                            ))

                        // Si el usuario se ha registrado correctamente, mostramos al activity de bienvenida
                        val intent = Intent(this, InicioActivity::class.java).apply {
                            putExtra("nombreusuario", binding.nombre.text.toString())
                        }
                        startActivity(intent)
                    } else {
                        // Si no, nos avisa de un error
                        Toast.makeText(this, "Error en el registro del nuevo usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}