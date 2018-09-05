package au.com.patricklabes.killssystem.controlScreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import au.com.patricklabes.killssystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_add_light.*

class AddLightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_light)

        AddLightConfirmButton.setOnClickListener {
            AddALight()
        }

    }

    private fun AddALight(){

        val uid = FirebaseAuth.getInstance().uid ?: return

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })



    }
}
