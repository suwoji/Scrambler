//package ru.startandroid.develop.scrambler
//
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.content.ContextWrapper
//import android.graphics.BitmapFactory
//import android.util.Log
//import android.widget.*
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import javax.crypto.Cipher
//import javax.crypto.CipherInputStream
//import javax.crypto.CipherOutputStream
//import javax.crypto.spec.SecretKeySpec
//
//
//private const val ENC_EXT = ".cyp"
//private const val KEY: String = "KERQIRUDYTHSGTYJ"
//
//class Scrambler2: ScramblerInterface {
//    override fun decrypt(context: Context, encFilePath: String): Pair<String?, Boolean> {
//        // on below line creating and initializing
//        // variable for context wrapper.
//        if (!encFilePath.endsWith(ENC_EXT)) {
//            return null to false
//        }
//        val contextWrapper = ContextWrapper(context)
//
//        // on below line creating a new file for encrypted image.
//        val encFile = File(encFilePath)
//
//        // on below line creating input stream for file with file path.
//        val encFileInputStream = FileInputStream(encFile.path)
//
//        // on below line creating a file for decrypted image.
//        val decFile = File(encFilePath.removeSuffix(ENC_EXT))
//
//        // on below line creating an file output
//        // stream for decrypted image.
//        val decFileOutputStream = FileOutputStream(decFile.path)
//        val bytes = KEY.toByteArray()
//
//        // creating a variable for secret key and passing
//        // our secret key and algorithm for encryption.
//        val sks = SecretKeySpec(bytes, "AES")
//
//        // on below line creating a variable for
//        // cipher and initializing it
//        val cipher = Cipher.getInstance("AES")
//
//        // on below line initializing cipher and
//        // specifying decrypt mode to decrypt.
//        cipher.init(Cipher.DECRYPT_MODE, sks)
//
//        // on below line creating a variable
//        // for cipher input stream.
//        val cipherInputStream = CipherInputStream(encFileInputStream, cipher)
//
//        // on below line creating a variable b.
//        var b: Int
//        val d: ByteArray = ByteArray(8)
//        b = cipherInputStream.read(d)
//        var temp = 0
//        while (b != -1) {
//            decFileOutputStream.write(d, 0, b)
//            temp++
//            Log.d(TAG, "Itter:" + b.toString())
//            b = cipherInputStream.read(d)
//        }
//
//        // on below line flushing our fos,
//        // closing fos and closing cis.
//        decFileOutputStream.flush()
//        decFileOutputStream.close()
//        cipherInputStream.close()
//
//        // displaying toast message.
//        Toast.makeText(context, "Image decrypted successfully..", Toast.LENGTH_SHORT).show()
//
//        // on below line creating an image file
//        // from decrypted image file path.
//        val imgFile = File(decFile.path)
//        if (imgFile.exists()) {
//            // creating bitmap for image and displaying
//            // that bitmap in our image view.
//            val bitmap = BitmapFactory.decodeFile(imgFile.path)
//        }
//        return Pair(decFile.path, true)
//    }
//
//    // on below line creating a method to encrypt an image.
//    override fun encrypt(context: Context, origFilePath: String): Boolean {
//        // on below line creating a
//        // variable for file input stream
//        if (origFilePath.endsWith(ENC_EXT)){
//            return false
//        }
//        val originalFileInputStream = FileInputStream(origFilePath)
//
//        // on below line creating a variable for context  wrapper.
//        val contextWrapper = ContextWrapper(context)
//
//        // on below line creating a variable for file
////        val photoDir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM)
//
//        // on below line creating a file for encrypted file.
//        val encFile = File(origFilePath + ENC_EXT)
//
//        // on below line creating a variable for file output stream.
//        val encFileOutputStream = FileOutputStream(encFile.path)
//
//        // on below line creating a variable for secret key.
//        // creating a variable for secret key and passing our
//        // secret key and algorithm for encryption.
//        val sks = SecretKeySpec(KEY.toByteArray(), "AES")
//
//
//        // on below line creating a variable for
//        // cipher and initializing it
//        val cipher = Cipher.getInstance("AES")
//
//        // on below line initializing cipher and
//        // specifying decrypt mode to encrypt.
//        cipher.init(Cipher.ENCRYPT_MODE, sks)
//
//        // on below line creating cos
//        val cipherOutputStream = CipherOutputStream(encFileOutputStream, cipher)
//        var b: Int
//        val d = ByteArray(8)
//        b = originalFileInputStream.read(d)
//        while (b != -1) {
//            encFileOutputStream.write(d, 0, b)
//            b = originalFileInputStream.read(d)
//        }
//
//        // on below line
//        // closing our cos and originalFileInputStream.
//        cipherOutputStream.flush()
//        cipherOutputStream.close()
//        originalFileInputStream.close()
//
//        val originalFile: File = File(origFilePath)
//        if (originalFile.exists()) {
//            if (originalFile.delete()) {
//                println("file Deleted :$origFilePath")
//            } else {
//                println("file not Deleted :$origFilePath")
//            }
//        }
//
//        return true
//    }
//}