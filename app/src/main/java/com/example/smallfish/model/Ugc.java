package com.example.smallfish.model;

import java.io.Serializable;

/**
 * 关注、点赞、分享等数据的javaben对象
 * author : Iwen大大怪
 * create : 2020/10/27 0:08
 */
public class Ugc implements Serializable {

    public int likeCount;
    public int shareCount;
    public int commentCount;
    public boolean hasFavorite ;
    public boolean hasLiked;
    public boolean hasdiss;
    public boolean hasDissed;
}
