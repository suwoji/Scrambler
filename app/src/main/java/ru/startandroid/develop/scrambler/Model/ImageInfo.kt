package ru.startandroid.develop.scrambler

import android.net.Uri
import io.realm.RealmObject

class ImageInfo : RealmObject {

    var name: String = ""
    var previewUri: Uri? = null
    var originalUri: Uri? = null
    var owner: String? = null

    fun setPrevUri (uri: Uri){
        this.previewUri = uri
    }
    fun setOrigUri (uri: Uri){
        this.originalUri = uri
    }
    fun setImageName (name: String){
        this.name = name;
    }
    fun getPrevImageUri () : Uri? {
        return this.originalUri
    }
}

