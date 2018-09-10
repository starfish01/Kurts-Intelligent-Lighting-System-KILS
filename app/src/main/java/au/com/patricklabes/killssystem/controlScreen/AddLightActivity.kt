package au.com.patricklabes.killssystem.controlScreen

import android.app.PendingIntent.getActivity
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

    val Tag = "AddLightActivity"



    private fun AddALight(){

        val newLight = Light()

        //for the time being im going to make it one user one light


        //val uid: String, -- take user uid+time stamp (to make this
        // easier im going to make this a field for now)
        newLight.uid = id_textfield_addbuttonactivity.text.toString()

        // val lightNickName: String, -- grabbed from ui
        newLight.lightNickName = nickname_textfield_addbuttonacvtivity.text.toString()
        // val switchState: Boolean,-- set to false
        newLight.switchState = false
        // var lightCurrentColour: Int, -- set to white
        newLight.lightCurrentColour = 16777215
        // var presetOne: Int, set to white
        newLight.presetOne = 16777215
        // var presetTwo: Int, set to white
        newLight.presetTwo = 16777215
        // var presetThree: Int, set to white
        newLight.presetThree = 16777215
        // var userConnectedWithLight: List<String> --if it cant find list add this device


        val uid = FirebaseAuth.getInstance().uid ?: ""

        val ref = FirebaseDatabase.getInstance().getReference("/lights/${newLight.uid}")

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    Log.d(Tag,"THE LIGHT EXISTS")

                    Toast.makeText(applicationContext,"This light already exists",Toast.LENGTH_LONG).show()

                }else{
                    Log.d(Tag,"THE LIGHT DOES NOT EXISTS")

                    ref.setValue(newLight)
                            .addOnSuccessListener {
                        Log.d(Tag,"A new light has been saved to the db")
                            }
                            .addOnFailureListener {
                              Log.d(Tag, "Not Saved to db ${it.message}")
                            }


                }
            }
            override fun onCancelled(p0: DatabaseError) {}
        })



    }

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
