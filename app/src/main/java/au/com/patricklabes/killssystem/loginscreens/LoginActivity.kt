package au.com.patricklabes.killssystem.loginscreens

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import au.com.patricklabes.killssystem.R
import au.com.patricklabes.killssystem.controlScreen.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)


        login_button_login_view.setOnClickListener {
            login()
        }


        backtoreg_textView_loginscreen.setOnClickListener {

            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)

        }



    }

    private fun login(){

        val email = email_editText_login.text.toString()
        val password = password_editText_password_login_view.text.toString()

        if( email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please enter an Email and Password", Toast.LENGTH_SHORT).show()
            Log.d("loginActivity", "failed Login")
            return
        }

        Log.d("loginActivity","Attempt login email/ password: $email / $password")

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    Toast.makeText(this, "Login was successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                .addOnFailureListener{

                    Toast.makeText(this, "Failed to login user ${it.message}", Toast.LENGTH_SHORT).show()

                }
    }



}