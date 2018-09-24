package au.com.patricklabes.killssystem.controlScreen

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import au.com.patricklabes.killssystem.R
import au.com.patricklabes.killssystem.loginscreens.LoginActivity
import au.com.patricklabes.killssystem.models.Light
import au.com.patricklabes.killssystem.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*




class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        var currentUser: User? = null
    }
    val listOfLights = ArrayList<String>()

    lateinit var spinner:Spinner

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

        disableButtons(true)

        if(userLoginStatus == null){
            finish()
        }else{
            grabUserData();
        }

        add_light_button.setOnClickListener {
            val intent = Intent(this, AddLightActivity::class.java)
            startActivity(intent)
        }

    }

    private fun disableButtons(b: Boolean){
        if(b){
            colour_select_button.isEnabled = false
            lightmain_button_preset_1.isEnabled = false
            lightmain_button_preset_2.isEnabled = false
            lightmain_button_preset_3.isEnabled = false
            lightmain_togglebutton_on_off.isEnabled = false
        }else
        {
            colour_select_button.isEnabled = true
            lightmain_button_preset_1.isEnabled = true
            lightmain_button_preset_2.isEnabled = true
            lightmain_button_preset_3.isEnabled = true
            lightmain_togglebutton_on_off.isEnabled = true
        }


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG,"ITEM SELECTED")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(TAG,"ITEM NOT SELECTED")
    }

    private fun populateArray(){

        Log.d(TAG, "adding to spinner")

        spinner.setOnItemSelectedListener(this)
        Log.d(TAG, "1")

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfLights)
        Log.d(TAG, "2")

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Log.d(TAG, "3")

        spinner.setAdapter(aa)
        Log.d(TAG, "4")

        //lightmain_spinner_lightselect.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfLights)


    }

    private fun getListOfLights(){

        val uid = FirebaseAuth.getInstance().uid

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid/lights")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach { listOfLights.add(it.value.toString()) }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        populateArray()

    }

    private fun grabeListOfLights(p0: DataSnapshot){

    }



    private fun grabUserData(){

        val uid = FirebaseAuth.getInstance().uid

        Log.d(TAG,"user uid $uid")

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)

                Log.d(TAG,"current user ${currentUser}")

                getListOfLights()

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




