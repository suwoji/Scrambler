package ru.startandroid.develop.scrambler

import android.content.Context

interface ScramblerInterface  {
    //path - полный путь до зашифрованного файла с расширением ".cyp"
    //Возвращает путь до расшифрованного файла отсекая ".cyp" + success статус
    fun decrypt(context: Context, encFilePath: String): Pair<String?, Boolean>
    //path - полный путь до оригинального файла(оригинальный файл не может быть с расширением .cyp)
    //Возвращает success статус
    fun encrypt(context: Context, origFilePath: String): Boolean
}