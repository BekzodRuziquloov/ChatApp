package space.beka.newchatapp.models

import java.io.Serializable


class User : Serializable {
    var displayName: String =""
    var imageLink: String =""
    var uid: String= ""

    constructor(displayName: String, imageLink: String, uid: String) {
        this.displayName = displayName
        this.imageLink = imageLink
        this.uid = uid
    }
    constructor()
}
