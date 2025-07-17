package ph.niferium.codexarca

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.InputStream

fun getFileName(context: Context, uri: Uri): String {
    var fileName = ""
    try {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex) ?: ""
                }
            }
        }

        if (fileName.isEmpty()) {
            fileName = uri.lastPathSegment ?: "unknown_file"
        }
    } catch (e: Exception) {
        fileName = "unknown_file"
        e.printStackTrace()
    }
    return fileName
}

fun getFilePath(context: Context, uri: Uri): String {
    return try {
        when (uri.scheme) {
            "content" -> {
                if (DocumentsContract.isDocumentUri(context, uri)) {
                    uri.toString()
                } else {
                    // Try to get real path for MediaStore URIs
                    getRealPathFromURI(context, uri) ?: uri.toString()
                }
            }
            "file" -> uri.path ?: uri.toString()
            else -> uri.toString()
        }
    } catch (e: Exception) {
        uri.toString()
    }
}

fun getRealPathFromURI(context: Context, uri: Uri): String? {
    return try {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val dataIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
                if (dataIndex != -1) {
                    return it.getString(dataIndex)
                }
            }
        }
        null
    } catch (e: Exception) {
        null
    }
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            BitmapFactory.decodeStream(stream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}