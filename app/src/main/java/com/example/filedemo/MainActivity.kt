package com.example.filedemo

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ReadExternalStoragePermission(this) {
            if (it) {
                loadAllFilesToDatabase()
            }
        }.requestReadExternalStoragePermission()





    }

    fun getFilesFromStorage2() {

        val orderBy = MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        val contentResolver = contentResolver
        val uri = MediaStore.Files.getContentUri("external")

        val columns = arrayOf(
            MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE
        )

        val extensions: MutableList<String> = ArrayList()
        extensions.add("pdf")
        extensions.add("csv")
        extensions.add("doc")
        extensions.add("docx")
        extensions.add("xls")
        extensions.add("xlsx")

        val mimes: MutableList<String?> = ArrayList()
        for (ext in extensions) {
            mimes.add(MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext))
        }

        val cursor: Cursor?
        cursor = contentResolver.query(uri, columns, null, null, orderBy)
        if (cursor != null) {
            val mimeColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)
            val pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()) {
                val mimeType = cursor.getString(mimeColumnIndex)
                val filePath = cursor.getString(pathColumnIndex)
                if (mimeType != null && mimes.contains(mimeType)) {
                    Log.d("FileChecker", "MimeType $mimeType path $filePath")
                    // handle cursor
//                    makeFile(cursor)
                } else {
                    // need to check extension, because the Mime Type is null
//                    val extension: String = getExtensionByPath(filePath)
//                    if (extensions.contains(extension)) {
//                        // handle cursor
//                        makeFile(cursor)
//                    }
                }
            }
            cursor.close()
        }
    }

    fun getFile4() {
        var selection = "_data LIKE '%.pdf'"
        var cursor = getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), null, selection, null, "_id DESC")
        if (cursor== null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            // this means error, or simply no results found
            return;
        }
        do {
            Log.d("FileChecker", "Check Success" )
            // your logic goes here
        } while (cursor.moveToNext());
    }

    fun getFile3()  {
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.SIZE
        )

        val mimeType = "application/pdf"

        val whereClause = MediaStore.Files.FileColumns.MIME_TYPE + " IN ('" + mimeType + "')"
        val orderBy = MediaStore.Files.FileColumns.SIZE + " DESC"
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            whereClause,
            null,
            orderBy
        )

        if (cursor != null) {
            var titleIndex = cursor.getColumnIndex( MediaStore.Files.FileColumns.TITLE)
            var displayNameIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                var displayName = cursor.getString(displayNameIndex)
                Log.d("FileChecker", "DisplayName is $displayName")
            }

            cursor.close()
        }
    }

    fun getFilesFromStorage () {
        val pdf = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
        val doc = MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc")
        val docx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx")
        val xls = MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls")
        val xlsx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx")
        val ppt = MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt")
        val pptx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx")
        val txt = MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt")
        val rtx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtx")
        val rtf = MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtf")
        val html = MimeTypeMap.getSingleton().getMimeTypeFromExtension("html")

        //Table

        //Table
        val table = MediaStore.Files.getContentUri("external")
        //Column
        //Column
        val column = arrayOf(MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATE_MODIFIED)
        //Where
        //Where
        val where = (MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                + " OR " + MediaStore.Files.FileColumns.MIME_TYPE + "=?")
        //args
        //args
        val args = arrayOf(pdf, doc, docx, xls, xlsx, ppt, pptx, txt, rtx, rtf, html)

        val fileCursor: Cursor? =
            getContentResolver().query(table, column, where, args, null)

        var nameIndex = fileCursor?.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
        var dateModifiedIndex = fileCursor?.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)
        var sizeIndex = fileCursor?.getColumnIndex(MediaStore.Files.FileColumns.SIZE)

        while (fileCursor?.moveToNext() == true) {
            //your code
            var name = nameIndex?.let { fileCursor.getString(it) }
            var date = dateModifiedIndex?.let { fileCursor.getString(it) }
            var size = sizeIndex?.let { fileCursor.getString(it) }
            Log.d("FileChecker", "Name ${name} Date $date Size $size")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE) {
            Log.d("PermissionCheck", "Request Success")
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadAllFilesToDatabase()
            }
        }
    }

    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 41
    fun checkPermissionForReadExtertalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result: Int = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    fun checkPermissionForWriteExtertalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result: Int = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    @Throws(Exception::class)
    fun requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_REQUEST_CODE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun getExtensionByPath(path: String): String? {
        var result: String? = "%20"
        val i = path.lastIndexOf('.')
        if (i > 0) {
            result = path.substring(i + 1)
        }
        return result
    }

    private fun getAllMediaFilesCursor2(): Cursor? {
        val projections =
            arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA, //TODO: Use URI instead of this.. see official docs for this field
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE
            )

        val sortBy = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

        val selectionArgs =
            MediaTypes.values().map { it.mimeTypes }.flatten().filterNotNull().toTypedArray()

        val args = selectionArgs.joinToString {
            "?"
        }

        val selection =
            MediaStore.Files.FileColumns.MIME_TYPE + " IN (" + args + ")"

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }

        return contentResolver.query(
            collection,
            projections,
            selection,
            selectionArgs,
            sortBy
        )
    }

    private fun getAllMediaFilesCursor(): Cursor? {
        val projections =
            arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA, //TODO: Use URI instead of this.. see official docs for this field
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE
            )

        val sortBy = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

        val selectionArgs =
            FileTypes.values().map { it.mimeTypes }.flatten().filterNotNull().toTypedArray()

        val args = selectionArgs.joinToString {
            "?"
        }

        val selection =
            MediaStore.Files.FileColumns.MIME_TYPE + " IN (" + args + ")"

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }

        return contentResolver.query(
            collection,
            projections,
            selection,
            selectionArgs,
            sortBy
        )
    }

    fun loadAllFilesToDatabase() {
        val cursor = getAllMediaFilesCursor2()

        if (true == cursor?.moveToFirst()) {
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val pathCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
            val nameCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dateCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val mimeType = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
            val sizeCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)

            do {
                val id = cursor.getLong(idCol)
                val path = cursor.getStringOrNull(pathCol) ?: continue
                val name = cursor.getStringOrNull(nameCol) ?: continue
                val dateTime = cursor.getLongOrNull(dateCol) ?: continue
                val type = cursor.getStringOrNull(mimeType) ?: continue
                val size = cursor.getLongOrNull(sizeCol) ?: continue
                val contentUri = ContentUris.appendId(
                    MediaStore.Files.getContentUri("external").buildUpon(),
                    id
                ).build()

                val media =   "Uri:$contentUri,\nPath:$path,\nFileName:$name,\nFileSize:$size,\nDate:$dateTime,\ntype:$type"

                Log.d("FileChecker", "Media: $media")

            } while (cursor.moveToNext())
        }
        cursor?.close()
    }
//
//    private fun makeFile(cursor: Cursor) {
//        val mimeColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)
//        val pathColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
//        val sizeColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.SIZE)
//        val titleColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.TITLE)
//        val nameColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
//        val fileId = cursor.getInt(pathColumnIndex)
//        val fileSize = cursor.getString(sizeColumnIndex)
//        val fileDisplayName = cursor.getString(nameColumnIndex)
//        val fileTitle = cursor.getString(titleColumnIndex)
//        val filePath = cursor.getString(pathColumnIndex)
//        var mimeType = cursor.getString(mimeColumnIndex)
//        var type = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
//        if (type == null) {
//            type = FileUtils.getExtensionByPath(filePath)
//            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(type)
//        }
//        val result = MyCustomFile()
//        result.setFileId(fileId)
//        result.setFilePath(filePath)
//        result.setFileSize(fileSize)
//        result.setFileTitle(fileTitle)
//        result.setFileDisplayName(fileDisplayName)
//        result.setMimeType(mimeType)
//        result.setFileExtension(type)
//        return result
//    }
}