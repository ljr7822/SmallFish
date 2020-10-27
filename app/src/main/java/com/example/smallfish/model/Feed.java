package com.example.smallfish.model;

import java.io.Serializable;

/**
 * 帖子的javaben对象
 *
 * author : Iwen大大怪
 * create : 2020/10/27 0:13
 */
public class Feed implements Serializable {
    public static final int TYPE_IMAGE_TEXT = 1;//图文
    public static final int TYPE_VIDEO = 2;//视频
    /**
     * id : 364
     * itemId : 6739143063064549000
     * itemType : 2
     * createTime : 1569079017
     * duration : 299.435
     * feeds_text : 当中国地图出来那一幕，我眼泪都出来了！
     * 太震撼了！
     * authorId : 3223400206308231
     * activityIcon : null
     * activityText : null
     * width : 640
     * height : 368
     * url : https://pipijoke.oss-cn-hangzhou.aliyuncs.com/6739143063064549643.mp4
     * cover : https://p3-dy.byteimg.com/img/mosaic-legacy/2d676000e36289f35f70c~640x368_q80.webp
     */

    public int id;
    public long itemId;
    public int itemType;
    public long createTime;
    public double duration;
    public String feeds_text;
    public long authorId;
    public String activityIcon;
    public String activityText;
    public int width;
    public int height;
    public String url;
    public String cover;

    public User author;
    public Comment topComment;
    public Ugc ugc;
}
