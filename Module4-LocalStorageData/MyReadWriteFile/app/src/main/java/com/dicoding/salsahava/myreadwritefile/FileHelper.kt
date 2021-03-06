package com.dicoding.salsahava.myreadwritefile

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStreamWriter

internal object FileHelper {
    private val TAG = FileHelper::class.java.simpleName

    fun writeToFile(fileModel: FileModel, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(fileModel.fileName.toString(), Context.MODE_PRIVATE))
            outputStreamWriter.write(fileModel.data.toString())
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e(TAG, "File write failed: ", e)
        }
    }

    fun readFromFile(context: Context, fileName: String): FileModel {
        val fileModel = FileModel()

        try {
            val inputStream = context.openFileInput(fileName)

            if (inputStream != null) {
                val receiveString = inputStream.bufferedReader().use(BufferedReader::readText)
                inputStream.close()
                fileModel.data = receiveString
                fileModel.fileName = fileName
            }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "File not found: ", e)
        } catch (e: IOException) {
            Log.e(TAG, "Cannot read file: ", e)
        }
        return fileModel
    }
}