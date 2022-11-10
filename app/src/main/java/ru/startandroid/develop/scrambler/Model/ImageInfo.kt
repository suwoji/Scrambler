package ru.startandroid.develop.scrambler.Model

import android.net.Uri
import io.realm.RealmObject

class ImageInfo : RealmObject {

    var name: String = ""
    var previewUri: String? = null
    var originalUri: String? = null
    var owner: String? = null

    fun setPrevUri (uri: String){
        this.previewUri = uri
    }
    fun setOrigUri (uri: String){
        this.originalUri = uri
    }
    fun setImageName (name: String){
        this.name = name;
    }
    fun getPrevImageUri () : String? {
        return this.originalUri
    }
}

