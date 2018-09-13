package au.com.patricklabes.killssystem.models;

class Light(var uid: String,var lightNickName: String, var switchState: Boolean,var lightCurrentColour: Int, var presetOne: Int, var presetTwo: Int, var presetThree: Int) {

    constructor() : this("", "",false,16777215, 16777215, 16777215, 16777215)

}