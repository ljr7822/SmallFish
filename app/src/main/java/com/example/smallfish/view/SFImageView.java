package com.example.smallfish.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.libcommon.PixUtils;

/**
 * author : Iwen大大怪
 * create : 2020/10/27 0:47
 */
public class SFImageView extends AppCompatImageView {
    public SFImageView(@NonNull Context context) {
        super(context);
    }

    public SFImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SFImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 标记一个方法，给布局文件使用,添加图片
    @BindingAdapter(value = {"image_url", "isCircle"}, requireAll = false)
    public static void setImageUrl(SFImageView view, String imageUrl, boolean isCircle) {
        RequestBuilder<Drawable> builder = Glide.with(view).load(imageUrl);
        if (isCircle) {
            builder.transform(new CircleCrop());
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (view.getLayoutParams() != null && layoutParams.width > 0 && layoutParams.height > 0) {
            builder.override(layoutParams.width, layoutParams.height);
        }
        builder.into(view);
    }

    public void bindData(int widthPx, int heightPx, int marginLeft,  String imageUrl) {
        bindData(widthPx,heightPx,marginLeft,PixUtils.getScreenWidth(),PixUtils.getScreenHeight(),imageUrl);
    }

    public void bindData(int widthPx, int heightPx, int marginLeft, int maxWidth, int maxHeight, String imageUrl) {
        if (widthPx <= 0 || heightPx <= 0) {
            Glide.with(this).load(imageUrl).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    int width = resource.getIntrinsicWidth();
                    int height = resource.getIntrinsicHeight();
                    setSize(width, height, marginLeft, maxWidth, maxHeight);
                    setImageDrawable(resource);
                }
            });
            return;
        }
        setSize(widthPx,heightPx,marginLeft,maxWidth,maxHeight);
        setImageUrl(this,imageUrl,false);
    }

    // 设置imageView的大小
    private void setSize(int width, int height, int marginLeft, int maxWidth, int maxHeight) {
        int finalWidth, finalHeight;
        if (width > height) {
            // 宽度大于高度，高度自适应
            finalWidth = maxWidth;
            finalHeight = (int) (height / (width * 1.0f / finalWidth));
        } else {
            // 高度大于宽度，宽度自适应
            finalHeight = maxHeight;
            finalWidth = (int) (width / (height * 1.0f / finalHeight));
        }
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(finalWidth, finalHeight);
        params.leftMargin = height > width ? PixUtils.dp2px(marginLeft) : 0;
        setLayoutParams(params);
    }
}
