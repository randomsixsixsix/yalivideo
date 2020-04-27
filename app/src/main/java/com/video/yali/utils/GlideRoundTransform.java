package com.video.yali.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * User:    Xiaoc
 * Date:    2016/9/19.
 * Time:    11:36
 * 类描述： 切位圆角图片
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;
    static int borderWidth = 0;
    static int borderColor = Color.WHITE;

    public GlideRoundTransform(Context context) {
        this(context, 5);
    }


    public GlideRoundTransform(Context context, int dp) {
//        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    /**
     * @param context
     * @param dp
     * @param borderWidth
     * @param borderColor
     */
    public GlideRoundTransform(Context context, int dp, int borderWidth, int borderColor) {
//        super(context);
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform, outWidth, outHeight);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) return null;
        // 先缩放 ， 在剪切
        int sWidth = source.getWidth();
        int sHeight = source.getHeight();
        float sScan = 1.0f * sWidth / sHeight;   //原始的长宽比
        float outScan = 1.0f * outWidth / outHeight;  //输出的长宽比
        if (outScan < sScan) {
            //按照  heigh  来 缩放
            int nWidth = (int) (outHeight * sScan);
            source = Bitmap.createScaledBitmap(source, nWidth, outHeight, true);
            source = Bitmap.createBitmap(source, (nWidth - outWidth) / 2, 0, outWidth, outHeight);

        } else {
            //按照  width 来缩放
            int nHeight = (int) (outWidth / sScan);
            source = Bitmap.createScaledBitmap(source, outWidth, nHeight, true);
            source = Bitmap.createBitmap(source, 0, (nHeight - outHeight) / 2, outWidth, outHeight);
        }
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        //画边框
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setColor(borderColor);
        RectF rectF = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        //画图片
        paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        rectF = new RectF(borderWidth, borderWidth, source.getWidth() - borderWidth, source.getHeight() - borderWidth);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        return result;
    }

//    @Override
//    public String getId() {
//        return getClass().getName() + Math.round(radius);
//    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
