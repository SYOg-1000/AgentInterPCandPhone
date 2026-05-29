package com.agentinter.pcphone.chat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

/**
 * 鍥剧墖閫夋嫨涓庡帇缂╁伐鍏枫€? * 璐熻矗锛氭媿鐓?鐩稿唽 URI 鈫?閲囨牱缂╂斁 鈫?EXIF 鏃嬭浆 鈫?JPEG 鍘嬬缉 鈫?缂撳瓨鏂囦欢銆? */
object ImagePicker {

    /** 鏈€澶ц竟闀?(px)锛岃秴杩囩瓑姣旂缉鏀?*/
    private const val MAX_DIMENSION = 2048

    /** JPEG 鍘嬬缉璐ㄩ噺 */
    private const val JPEG_QUALITY = 80

    /**
     * 鍘嬬缉骞剁紦瀛樺浘鐗囧埌 app 绉佹湁鐩綍銆?     *
     * @param context Android Context
     * @param uri 鍘熷鍥剧墖 URI锛堢浉鏈烘垨鐩稿唽锛?     * @return 鍘嬬缉鍚庣殑缂撳瓨鏂囦欢缁濆璺緞锛屽け璐ヨ繑鍥?null
     */
    fun compressAndCache(context: Context, uri: Uri): String? {
        return try {
            // 1. 浠呰鍙栧昂瀵革紙涓嶅姞杞藉畬鏁翠綅鍥撅級
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }

            // 2. 璁＄畻閲囨牱鐜囷紙2 鐨勫箓娆★級
            val sampleSize = calculateSampleSize(options.outWidth, options.outHeight)

            // 3. 瑙ｇ爜
            val decodeOptions = BitmapFactory.Options().apply {
                inSampleSize = sampleSize
            }
            val bitmap = context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, decodeOptions)
            } ?: return null

            // 4. 浜屾绮剧‘缂╂斁锛堝鏋滀粛瓒呴檺锛?            val scaled = scaleIfNeeded(bitmap, MAX_DIMENSION)

            // 5. EXIF 鏂瑰悜鏍℃
            val rotated = rotateFromExif(context, uri, scaled)

            // 6. 鍐欏叆缂撳瓨
            val cacheDir = File(context.cacheDir, "images").apply { mkdirs() }
            val file = File(cacheDir, "${UUID.randomUUID()}.jpg")
            FileOutputStream(file).use { out ->
                rotated.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, out)
            }

            // 7. 鍥炴敹涓棿 bitmap
            if (scaled !== rotated && scaled !== bitmap) scaled.recycle()
            if (rotated !== bitmap) rotated.recycle()
            bitmap.recycle()

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calculateSampleSize(width: Int, height: Int): Int {
        var sampleSize = 1
        val maxDim = maxOf(width, height)
        while (maxDim / sampleSize > MAX_DIMENSION) {
            sampleSize *= 2
        }
        return sampleSize
    }

    private fun scaleIfNeeded(bitmap: Bitmap, maxDim: Int): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        if (w <= maxDim && h <= maxDim) return bitmap
        val ratio = minOf(maxDim.toFloat() / w, maxDim.toFloat() / h)
        return Bitmap.createScaledBitmap(bitmap, (w * ratio).toInt(), (h * ratio).toInt(), true)
    }

    private fun rotateFromExif(context: Context, uri: Uri, bitmap: Bitmap): Bitmap {
        val rotation = try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val exif = ExifInterface(stream)
                when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                    else -> 0f
                }
            } ?: 0f
        } catch (e: Exception) {
            0f
        }
        return if (rotation != 0f) {
            val matrix = Matrix().apply { postRotate(rotation) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else {
            bitmap
        }
    }
}
