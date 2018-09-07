package au.com.patricklabes.killssystem.controlScreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import au.com.patricklabes.killssystem.R
import au.com.patricklabes.killssystem.models.Light
import au.com.patricklabes.killssystem.models.User
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

        val newLight = Light()

    }

    //var usersLights: MutableList<Light> = mutableListOf()



//    private fun GetUsersLights(){
//
//        val uid = FirebaseAuth.getInstance().uid ?: return
//
//        val ref = FirebaseDatabase.getInstance().getReference("/lights/$uid")
//
//        ref.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(p0: DataSnapshot) {
//
////                usersLights = p0.getValue(Light::class.java)!!
//
//                p0.children.toList(usersLights){}
//
//
////                p0.children.List(usersLights){
////                    it.getValue<Light>(Light::class.java)
////                }
//
//
//
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//        })



   // }
}
