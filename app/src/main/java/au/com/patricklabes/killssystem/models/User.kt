package au.com.patricklabes.killssystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val uid: String, val username: String, var listOfLightsConnectedToUser: List<String> ): Parcelable{
    constructor(): this("","", mutableListOf<String>() )

}