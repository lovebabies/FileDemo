package com.example.filedemo

import android.webkit.MimeTypeMap

enum class FileTypes(val mimeTypes: List<String?>) {
    PDF(
        listOf(
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"),
        ),
    ),
    WORD(
        listOf(
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc"),
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx"),
        ),
    ),
    PPT(
        listOf(
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt"),
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx"),
        ),
    ),
    EXCEL(
        listOf(
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls"),
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx"),
        ),
    ),
}

enum class MediaTypes(val mimeTypes: List<String?>) {
    MP4(
        listOf(
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp4"),
        ),
    ),
    MP3(
        listOf(
            MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3"),
        ),
    ),

}