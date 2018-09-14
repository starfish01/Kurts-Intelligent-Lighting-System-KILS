package au.com.patricklabes.killssystem.controlScreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import au.com.patricklabes.killssystem.R
import au.com.patricklabes.killssystem.loginscreens.LoginActivity
import au.com.patricklabes.killssystem.models.Light
import au.com.patricklabes.killssystem.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*




class MainActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }
    val listOfLights = ArrayList<User>()

    var spinner:Spinner?=null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            R.id.login_settings_button_three_dots -> {
                 val intent = Intent(this, LoginActivity::class.java)
//                 intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                 startActivity(intent)
             }
            R.id.log_out_settings_button_three_dots ->{
                FirebaseAuth.getInstance().signOut()
                if (FirebaseAuth.getInstance().currentUser == null){
                    Log.d("mainactivitymessage","LogOut successful")
                }else{
                    Log.d("mainactivitymessage","LogOut unsuccessful")
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        return false
    }


    val TAG = "LIGHTSDASHBOARD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userLoginStatus = FirebaseAuth.getInstance().currentUser;

        if(userLoginStatus == null){
            finish()
        }else{
            grabUserData();
        }


        //getListOfLights();

        //populate spinner
        populateArray()


        // lightmain_spinner_lightselect.onItemSelectedListener(this)

        //enable eveything onces a light has been choosen from spinner


        lightmain_button_preset_1.setOnClickListener {
            setPresetColour(1)
        }
        lightmain_button_preset_2.setOnClickListener {
            setPresetColour(2)
        }
        lightmain_button_preset_3.setOnClickListener {
            setPresetColour(3)
        }

        //colour_select_button.setOnClickListener { launchColourSelector() }

        add_light_button.setOnClickListener {
            val intent = Intent(this, AddLightActivity::class.java)
            startActivity(intent)
        }


    }

    private fun populateArray(){

        val myStrings = arrayOf("One", "Two", "Three", "Four", "Five")

        //Adapter for spinner
        lightmain_spinner_lightselect.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myStrings)

        //item selected listener for spinner
        lightmain_spinner_lightselect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@MainActivity, myStrings[p2], Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getListOfLights(){

        Log.d(TAG, "just before2 the light print")
        val listOfLights: MutableList<Light> = mutableListOf<Light>()

        Log.d(TAG, "just before1 the light print")

        val ref = FirebaseDatabase.getInstance().getReference("/users/${currentUser!!.uid}/lights")

        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                val chatMessage = p0.getValue(Light::class.java) ?: return

                listOfLights.add(chatMessage)


                Log.d(TAG, "just before the light print")
                Log.d(TAG, listOfLights.size.toString())
              // var size = listOfLightsMap.entries.toString()


            }
            override fun onCancelled(p0: DatabaseError) {      Log.d(TAG, "lights were cancelledp1@gmail")      }
        })


    }


    private fun grabUserData(){

        val uid = FirebaseAuth.getInstance().uid

        Log.d(TAG,"user uid $uid")

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)



                Log.d(TAG,"current user ${currentUser}")
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


    }

    private fun setPresetColour(i: Int) {
//
//        when(i){
//            1 ->  {
//                user.presetOne = color
//                lightmain_button_preset_1.setBackgroundColor(user.presetOne)}
//            2 ->  {
//                user.presetTwo = color
//                lightmain_button_preset_2.setBackgroundColor(user.presetTwo)}
//            3 ->  {
//                user.presetThree = color
//                lightmain_button_preset_3.setBackgroundColor(user.presetThree)}
//            4->{
//                lightmain_button_preset_1.setBackgroundColor(user.presetOne)
//                lightmain_button_preset_2.setBackgroundColor(user.presetTwo)
//                lightmain_button_preset_3.setBackgroundColor(user.presetThree)
//            }
//        }

        if(i != 4){
            updateDB()
        }

    }


    private fun updateDB() {

        val uid = FirebaseAuth.getInstance().uid ?: ""

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.setValue(currentUser)
                .addOnSuccessListener {
                    Log.d("mainactivitymessage","we have saved to the db")

                    Log.d("mainactivitymessage","${currentUser!!.uid}, ${currentUser!!.username}")

                    Log.d("mainactivitymessage",FirebaseDatabase.getInstance().getReference("/users/$uid").toString())

                }
                .addOnFailureListener {
                    Log.d("mainactivitymessage", "Failed to save details to database ${it.message}")

                }


    }




//    private fun  launchColourSelector()  {
//
//        val dialog = AmbilWarnaDialog(this, color, false, object : AmbilWarnaDialog.OnAmbilWarnaListener {
//            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
//
////                this@MainActivity.color = color
////
////                lightmain_togglebutton_on_off.setBackgroundColor(color)
////
////                user.lightCurrentColour = color
//
//            }
//
//            override fun onCancel(dialog: AmbilWarnaDialog) {
//                Toast.makeText(applicationContext, "cancel", Toast.LENGTH_SHORT).show()
//            }
//        })
//        dialog.show()
//    }




}



