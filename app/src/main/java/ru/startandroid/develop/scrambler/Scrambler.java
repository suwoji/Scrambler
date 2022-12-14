package ru.startandroid.develop.scrambler;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Scrambler {
    private String ENC_EXT = ".cyp";
    private String KEY = "KERQIRUDYTHSGTYJ";
    public Bitmap decrypt(Context context, String encFilePath) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        // on below line creating and initializing variable for context wrapper.
        ContextWrapper contextWrapper = new ContextWrapper(context);

        if (!encFilePath.endsWith(ENC_EXT)) {
            return null;
        }
        // on below line creating a file for getting photo directory.
        File photoDir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM);

        // on below line creating a new file for encrypted image.
        File encFile = new File(encFilePath);

        // on below line creating input stream for file with file path.
        FileInputStream encFileInputStream = new FileInputStream(encFile.getPath());

        // on below line creating a file for decrypted image.
        File decFile = new File(encFilePath.replace(ENC_EXT, ""));

        // on below line creating an file output stream for decrypted image.
        FileOutputStream decFileOutputStream = new FileOutputStream(decFile.getPath());

        // creating a variable for secret key and passing our secret key
        // and algorithm for encryption.
        SecretKeySpec sks = new SecretKeySpec(KEY.getBytes(), "AES");

        // on below line creating a variable
        // for cipher and initializing it
        Cipher cipher = Cipher.getInstance("AES");

        // on below line initializing cipher and
        // specifying decrypt mode to decrypt.
        cipher.init(Cipher.DECRYPT_MODE, sks);

        // on below line creating a variable for cipher input stream.
        CipherInputStream cipherInputStream = new CipherInputStream(encFileInputStream, cipher);

        // on below line creating a variable b.
        int b;
        byte[] d = new byte[8];
        while ((b = cipherInputStream.read(d)) != -1) {
            decFileOutputStream.write(d, 0, b);
        }

        // on below line flushing our fos,
        // closing fos and closing cis.
        decFileOutputStream.flush();
        decFileOutputStream.close();
        cipherInputStream.close();

        // displaying toast message.
//        Toast.makeText(context, "File decrypted successfully..", Toast.LENGTH_SHORT).show();

        // on below line creating an image file
        // from decrypted image file path.
        File imgFile = new File(decFile.getPath());
        Bitmap bit = BitmapFactory.decodeFile(imgFile.getPath());
        imgFile.delete();
        if (imgFile.exists()) {
            // creating bitmap for image and displaying
            // that bitmap in our image view.
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getPath());
//            imageView.setImageBitmap(bitmap);

        }

        return bit;
    }

    // on below line creating a method to encrypt an image.
    public Boolean encrypt(Context context, String origFilePath) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        if (origFilePath.endsWith(ENC_EXT)){
            return false;
        }

        // on below line creating a variable for file input stream
        FileInputStream fis = new FileInputStream(origFilePath);

        // on below line creating a variable for context  wrapper.
        ContextWrapper contextWrapper = new ContextWrapper(context);

        // on below line creating a variable for file
        File photoDir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM);

        // on below line creating a file for encrypted file.
        File encFile = new File(origFilePath + ENC_EXT);

        // on below line creating a variable for file output stream.
        FileOutputStream encFileOutputStream = new FileOutputStream(encFile.getPath());

        // on below line creating a variable for secret key.
        // creating a variable for secret key and passing our secret key
        // and algorithm for encryption.
        SecretKeySpec sks = new SecretKeySpec(KEY.getBytes(), "AES");

        // on below line creating a variable for cipher and initializing it
        Cipher cipher = Cipher.getInstance("AES");

        // on below line initializing cipher and
        // specifying decrypt mode to encrypt.
        cipher.init(Cipher.ENCRYPT_MODE, sks);

        // on below line creating cos
        CipherOutputStream cos = new CipherOutputStream(encFileOutputStream, cipher);
        int b;
        byte[] d = new byte[8];
        while ((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }

        // on below line closing
        // our cos and fis.
        cos.flush();
        cos.close();
        fis.close();

        return true;
    }   
}
