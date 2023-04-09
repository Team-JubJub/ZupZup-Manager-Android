package com.example.zupzup_manager.ui.custom

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest


class CustomRoundedCornersTransformation(private val radius: Int
) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(
        pool: BitmapPool,
        original: Bitmap,
        width: Int,
        height: Int
    ): Bitmap {


        val orgWidth = original.width
        val orgHeight = original.height


        val scaleX = width.toFloat() / orgWidth
        val scaleY = height.toFloat() / orgHeight

        val scaledWidth: Float
        val scaledHeight: Float

        if (orgWidth >= orgHeight) {
            scaledWidth = scaleY * orgWidth
            scaledHeight = height.toFloat()
        } else {
            scaledWidth = width.toFloat()
            scaledHeight = scaleX * orgHeight
        }

        var result: Bitmap? = pool.get(width, height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        //캔버스 준비

        //캔버스 준비
        val canvas = Canvas(result!!)

        //크레파스 준비
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = 0xff424242.toInt()

        //모서리가 둥근 사각형(destination) 그리기
        val rect = Rect(0, 0, width, height)
        val rectF = RectF(rect)
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint)

        val rightRect = Rect(width / 2, 0, width, height)
        canvas.drawRect(rightRect, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        //블랙펜서 비트맵(source) 그리기
        val targetRect = RectF(0F, 0F, scaledWidth, scaledHeight)
        canvas.drawBitmap(original, null, targetRect, paint)
        return result
    }
}