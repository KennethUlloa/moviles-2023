package com.example.movilessoftware2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirabaseUIAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirabase_uiauth)
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            respuestaLoginAuthUi.launch(signInIntent)
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener {
            seDeslogueo()
        }

        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null) {
            val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
            val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
            val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
            btnLogout.visibility = View.VISIBLE
            btnLogin.visibility = View.INVISIBLE
            tvBienvenido.text = usuario.displayName
        }
    }

    fun seDeslogueo() {
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = "Bienvenido"
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signOut()
    }

    private val respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
        res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode == RESULT_OK) {
            if (res.idpResponse != null) {
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(res: IdpResponse) {
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = FirebaseAuth.getInstance().currentUser?.displayName
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if(res.isNewUser) {
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse) {

    }
}