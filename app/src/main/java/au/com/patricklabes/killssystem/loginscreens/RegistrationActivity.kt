package au.com.patricklabes.killssystem.loginscreens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import au.com.patricklabes.killssystem.R
import au.com.patricklabes.killssystem.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*



class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        reg_button_reg_screen.setOnClickListener {
            preformReg()
        }

        already_have_account_textView_reg_screen.setOnClickListener {

            Log.d("RegisterActivity","textbutton Pressed")

            finish()
        }




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("RegisterActivity","photo was selected")
        }

    }

    private fun preformReg(){

        val email = email_edittext_reg_screen.text.toString()
        val password = password_edittext_reg_screen.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter text in Email/ Password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegisterActivity","Email is "+email)
        Log.d("RegisterActivity","Password: $password")

        //firebase auth
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if (!it.isSuccessful()) return@addOnCompleteListener

                    Toast.makeText(this, "${it.result.user.email} was created", Toast.LENGTH_SHORT).show()
                    Log.d("RegisterActivity","Successfully created user uid: ${it.result.user.uid}")

                    saveUserToDatabase()

                    finish()
                }
                .addOnFailureListener{
                    Log.d("RegisterActivity","Failed to create user ${it.message}")

                    Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_SHORT).show()
                }
    }

    private fun saveUserToDatabase(){

        val uid = FirebaseAuth.getInstance().uid ?: ""

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, username_edittext_reg_screen.text.toString(),false,16777215,16777215,16777215,16777215)

        ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("RegisterActivity","we have saved to the db")

                }
                .addOnFailureListener {
                    Log.d("RegisterActivity", "Failed to save details to database ${it.message}")

                }

    }




}




