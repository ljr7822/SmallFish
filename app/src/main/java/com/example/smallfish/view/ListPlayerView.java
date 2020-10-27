package com.example.smallfish.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.libcommon.PixUtils;
import com.example.smallfish.R;

/**
 * 视频类
 * author : Iwen大大怪
 * create : 2020/10/27 14:51
 */
public class ListPlayerView extends FrameLayout {
    private View bufferView;
    private SFImageView cover, blur;
    private ImageView playBtn;
    private String mCategory;
    private String mVideoUrl;

    public ListPlayerView(@NonNull Context context) {
        this(context, null);
    }

    public ListPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_player_view, this, true);
        bufferView = findViewById(R.id.buffer_view);
        cover = findViewById(R.id.cover);
        blur = findViewById(R.id.blur_background);
        playBtn = findViewById(R.id.play_btn);
    }

    /**
     * 视频数据绑定
     *
     * @param category 页面的生命标志 因为需要和页面相关联，所以在绑定视频数据时，传入页面的生命标志
     * @param widthPx  宽度
     * @param heightPx 高度
     * @param coverUrl 封面url
     * @param videoUrl 视频url
     */
    public void bindData(String category, int widthPx, int heightPx, String coverUrl, String videoUrl) {
        mCategory = category;
        mVideoUrl = videoUrl;

        // 加载封面
        cover.setImageUrl(cover, coverUrl, false);
        if (widthPx < heightPx) {
            // 如果宽度小于高度，要加载背景
            blur.setBlurImageUrl(coverUrl, 10);
            blur.setVisibility(VISIBLE);
        } else {
            // 不加载模糊背景
            blur.setVisibility(INVISIBLE);
        }
        setSize(widthPx, heightPx);
    }

    /**
     * 设定listPlayerView的真实宽高
     *
     * @param widthPx  宽度
     * @param heightPx 高度
     */
    protected void setSize(int widthPx, int heightPx) {
        int maxWidth = PixUtils.getScreenWidth();
        int maxHeight = PixUtils.getScreenHeight();

        int layoutWidth = maxWidth;
        int layoutHeight = 0;

        int coverWidth;
        int coverHeight;
        if (widthPx >= heightPx) {
            coverWidth = maxWidth;
            layoutHeight = coverHeight = (int) (heightPx / (widthPx * 1.0f / maxWidth));
        } else {
            // 如果高度大于宽度，就让组件的高度等于屏幕的高度maxHeight，封面的高度也等于maxHeight
            layoutHeight = coverHeight = maxHeight;
            coverWidth = (int) (widthPx / (heightPx * 1.0f / maxHeight));
        }
        // 设置lestPlayerView组件布局参数
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = layoutWidth;
        params.height = layoutHeight;
        setLayoutParams(params);

        // 给高斯模糊背景设置布局参数
        ViewGroup.LayoutParams blurParams = blur.getLayoutParams();
        blurParams.width = layoutWidth;
        blurParams.height = layoutHeight;
        blur.setLayoutParams(blurParams);

        // 给封面设置布局参数
        FrameLayout.LayoutParams coverParams = (LayoutParams) cover.getLayoutParams();
        coverParams.width = coverWidth;
        coverParams.height = coverHeight;
        coverParams.gravity = Gravity.CENTER;
        cover.setLayoutParams(coverParams);

        // 给播放按钮设置布局参数
        FrameLayout.LayoutParams playBtnParams = (LayoutParams) playBtn.getLayoutParams();
        playBtnParams.gravity = Gravity.CENTER;
        playBtn.setLayoutParams(playBtnParams);
    }

}
