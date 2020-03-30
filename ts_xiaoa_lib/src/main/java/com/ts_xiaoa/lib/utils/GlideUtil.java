package com.ts_xiaoa.lib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ts_xiaoA on 2019/9/23 09:40
 * E-Mail Address：443502578@qq.com
 * Desc: Glide加载图片工具类
 */
public class GlideUtil {

    private static int defaultImagePlaceHolder;
    private static int defaultImageHead;
    private static int defaultImageError;

    /**
     * 初始化
     *
     * @param defaultImagePlaceHolder 占位图
     * @param defaultImageHead        默认头像
     * @param defaultImageError       默认加载失败图片
     */
    public static void init(int defaultImagePlaceHolder, int defaultImageHead, int defaultImageError) {
        GlideUtil.defaultImagePlaceHolder = defaultImagePlaceHolder;
        GlideUtil.defaultImageHead = defaultImageHead;
        GlideUtil.defaultImageError = defaultImageError;
    }

    /*
     *加载图片(centerCrop)
     */
    public static void loadCenterCropImage(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(defaultImagePlaceHolder) //占位图
                .error(defaultImageError)       //错误图
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /*
     *加载图片(centerCrop)
     */
    public static void loadRoundImage(Context context, Object url, ImageView imageView, int radiusDP) {
        RequestOptions options = RequestOptions.bitmapTransform(new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCorners(DensityUtil.dpToPx(radiusDP))
        )).placeholder(defaultImagePlaceHolder) //占位图
                .error(defaultImageError)       //错误图
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /*
     *加载图片(centerCrop)
     */
    public static void loadRoundImage(Context context, Object url, ImageView imageView, int radius, RoundedCornersTransformation.CornerType cornerType) {
        RequestOptions options = RequestOptions.bitmapTransform(new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(DensityUtil.dpToPx(radius), 0, cornerType)
        )).placeholder(defaultImagePlaceHolder) //占位图
                .error(defaultImageError)       //错误图
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /*
     *加载图片(centerCrop)
     */
    public static void loadFitCenterImage(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(defaultImagePlaceHolder) //占位图
                .error(defaultImageError)       //错误图
                .dontAnimate()
                .override(imageView.getWidth(), imageView.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(defaultImagePlaceHolder)
                .error(defaultImageError)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).asBitmap().load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadHeadImage(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(defaultImageHead)
                .error(defaultImageHead)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).asBitmap().load(url)
                .apply(options)
                .into(imageView);
    }

}
