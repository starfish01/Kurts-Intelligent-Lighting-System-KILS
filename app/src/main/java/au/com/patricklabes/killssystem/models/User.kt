package au.com.patricklabes.killssystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val uid: String, val username: String, val lightState: Boolean, var lightCurrentColour: Int, var presetOne: Int, var presetTwo: Int, var presetThree: Int): Parcelable{
    constructor(): this("","",false, 16777215, 16777215, 16777215, 16777215 )

}