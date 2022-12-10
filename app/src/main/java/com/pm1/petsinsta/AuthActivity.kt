package com.pm1.petsinsta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        findViewById<Button>(R.id.singUpButton)!!.setOnClickListener { singUp() }
        findViewById<Button>(R.id.loginButton)!!.setOnClickListener { login() }
        title = "Autenticación"

        session()
    }

    override fun onStart() {
        super.onStart()
        findViewById<LinearLayout>(R.id.authLayout).visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            findViewById<LinearLayout>(R.id.authLayout).visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }
    }

    private fun singUp() {
        val email = findViewById<EditText>(R.id.emailEditText).text.toString().trim()
        val password = findViewById<EditText>(R.id.etPassword).text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            /*FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                } else {
                    showAlert(email, password)
                    showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                }
            }*/
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.putString("email", email)
            prefs.putString("provider", "BASIC")
            prefs.apply()

            showHome(email, ProviderType.BASIC)
        }
        else
        {
            showAlert(email, password)
        }
    }

    private fun login() {
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        if (email != "" && password != "") {
            /*FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    //showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                } else {
                    showAlert(email, password)
                    //showHome(it.result?.user?.email ?:"", ProviderType.GOOGLE)
                }
            }*/
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.putString("email", email)
            prefs.putString("provider", "BASIC")
            prefs.apply()

            showHome(email, ProviderType.BASIC)
        }
        else
        {
            showAlert(email, password)
        }
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun showAlert(email: String, pass: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Verifique correo y contraseña; $email, $pass")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}


/*

private fun showAlert() {
val builder = AlertDialog.Builder(this)
builder.setTitle("Los botones se accionan?")
builder.setMessage("Se ha producido un error de autenticacion al usuario")
builder.setPositiveButton("Aceptar", null)
val dialog: AlertDialog = builder.create()
dialog.show()
}

private fun showHome(email: String, provider: ProviderType) {
val homeIntent = Intent(this, HomeActivity::class.java).apply {
    putExtra("email", email)
    putExtra("provider", provider.name)
}
startActivity(homeIntent)
}*/
