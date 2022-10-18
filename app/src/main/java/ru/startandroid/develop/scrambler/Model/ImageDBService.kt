package ru.startandroid.develop.scrambler

import android.net.Uri
import io.realm.*
import io.realm.notifications.*
import io.realm.query.RealmQuery


object ImageDBService{
    private val configuration = RealmConfiguration.with(schema = setOf(ImageInfo::class))
    private val realm = Realm.open(configuration);

    fun writeImage( name : String, prewUri : Uri, origUri : Uri){
        val imageInfo = ImageInfo().apply{
            this.name = name
            previewUri = prewUri
            originalUri = origUri
        }
        val managedImage = realm.writeBlocking {
            copyToRealm(imageInfo)
        }
    }

    suspend fun writeImageAsync(name : String, prewUri : Uri, origUri : Uri){
        val imageInfo = ImageInfo().apply{
            this.name = name
            previewUri = prewUri
            originalUri = origUri
        }
        realm.write {
            copyToRealm(imageInfo)
        }
    }

    fun queryAllImages() : ArrayList<ImageInfo> {

        return ArrayList(realm.query<ImageInfo>().find());
    }

    //TODO: return ArrayList<ImageInfo>
    suspend fun queryAllImagesAsync() {
        realm.query<ImageInfo>().asFlow().collect{
            results: ResultsChange<ImageInfo> ->
            when(results){
                is InitialResults<ImageInfo> -> println("init")
                is UpdatedResults<ImageInfo> -> println("update")
            }
        }
    }

//    suspend fun updateImages(){
//        //find
//        realm.query<ImageInfo>("")
//    }

    suspend fun deleteAllImages() {
        realm.write{
            val query : RealmQuery<ImageInfo> = this.query<ImageInfo>()
            delete(query)

            val results: RealmResults<ImageInfo> = query.find()
            delete(results)

            results.forEach{ delete(it) }
        }
    }
}