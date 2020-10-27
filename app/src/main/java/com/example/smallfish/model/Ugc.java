package com.example.smallfish.model;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.io.Serializable;

/**
 * 关注、点赞、分享等数据的javaben对象
 * author : Iwen大大怪
 * create : 2020/10/27 0:08
 */
public class Ugc extends BaseObservable implements Serializable {

    public int likeCount;
    public int shareCount;
    public int commentCount;
    public boolean hasFavorite ;
    public boolean hasLiked;
    public boolean hasdiss;
    public boolean hasDissed;

    @Bindable
    public int getShareCount() {
        return shareCount;
    }

    @Bindable
    public boolean isHasdiss() {
        return hasdiss;
    }

    @Bindable
    public boolean isHasLiked() {
        return hasLiked;
    }

    @Bindable
    public boolean isHasFavorite() {
        return hasFavorite;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || !(obj instanceof Ugc))
            return false;
        Ugc newUgc = (Ugc) obj;
        return likeCount == newUgc.likeCount
                && shareCount == newUgc.shareCount
                && commentCount == newUgc.commentCount
                && hasFavorite == newUgc.hasFavorite
                && hasLiked == newUgc.hasLiked
                && hasdiss == newUgc.hasdiss;
    }
}
