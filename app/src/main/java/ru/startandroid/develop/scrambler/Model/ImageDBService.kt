package ru.startandroid.develop.scrambler.Model

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
            previewUri = prewUri.toString()
            originalUri = origUri.toString()
        }
        val managedImage = realm.writeBlocking {
            copyToRealm(imageInfo)
        }
    }

    suspend fun writeImageAsync(name : String, prewUri : Uri, origUri : Uri){
        val imageInfo = ImageInfo().apply{
            this.name = name
            previewUri = prewUri.toString()
            originalUri = origUri.toString()
        }
        realm.write {
            copyToRealm(imageInfo)
        }
    }

    fun queryAllImages() : ArrayList<ImageInfo> {

        return ArrayList(realm.query<ImageInfo>().find());
    }

    //TODO: optimize in future
    fun queryAllImagesCount() : Int {
        //realm.objects(Category.self).filter("dateCreated != ''").count
        //return realm.query<ImageInfo>().count()
            return queryAllImages().count()
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