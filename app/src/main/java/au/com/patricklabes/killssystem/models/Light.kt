package au.com.patricklabes.killssystem.models;

class Light(val uid: String,val lightNickName: String, val switchState: Boolean,var lightCurrentColour: Int, var presetOne: Int, var presetTwo: Int, var presetThree: Int, var userConnectedWithLight: List<String>) {

    constructor() : this("", "",false,16777215, 16777215, 16777215, 16777215, mutableListOf<String>())

}