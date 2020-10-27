package com.example.smallfish.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smallfish.R;
import com.example.smallfish.model.BottomBar;
import com.example.smallfish.model.Destination;
import com.example.smallfish.utils.AppConfig;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.List;

/**
 * 自定义view承载底部导航栏
 *
 * author iwen大大怪
 * Create to 2020/10/14 16:31
 */
public class AppBottomBar extends BottomNavigationView {
    // 保存图标的数组
    private static int[] sIcons = new int[]{R.drawable.icon_tab_home_black, R.drawable.icon_tab_sofa_black,
            R.drawable.icon_tab_add_pink, R.drawable.icon_tab_find_black, R.drawable.icon_tab_me_black};


    private final BottomBar bottomBar;

    public AppBottomBar(@NonNull Context context) {
        this(context, null);
    }

    public AppBottomBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public AppBottomBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bottomBar = AppConfig.getBottomBar();
        List<BottomBar.Tabs> tabs = bottomBar.tabs;

        // 定义一个二维数组来保存选中和未选择的颜色
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};

        int[] colors = new int[]{Color.parseColor(bottomBar.activeColor), Color.parseColor(bottomBar.inActiveColor)};
        ColorStateList stateList = new ColorStateList(states, colors);
        setItemTextColor(stateList);
        setItemIconTintList(stateList);
        // LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
        // LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
        // LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
        // LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        // 设置默认选中
        setSelectedItemId(bottomBar.selectTab);
        // 遍历集合，将按钮添加到BottomBar上
        for (int i = 0; i < tabs.size(); i++) {
            BottomBar.Tabs tab = tabs.get(i);
            // 如果显示为false，就不显示
            if (!tab.isEnable())
                return;
            int id = getId(tab.getPageUrl());
            if (id < 0) {
                return;
            }
            MenuItem item = getMenu().add(0, id, tab.getIndex(), tab.getTitle());
            // 设置资源图标
            item.setIcon(sIcons[tab.getIndex()]);
        }
        // 此处给按钮icon设置大小
        for (int i = 0; i < tabs.size(); i++) {
            BottomBar.Tabs tab = tabs.get(i);
            int iconSize = dp2Px(tab.getSize());

            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(tab.getIndex());
            itemView.setIconSize(iconSize);

            if (TextUtils.isEmpty(tab.getTitle())){
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.getTintColor())));
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false);
            }
        }
    }

    /**
     * 计算按钮的大小
     *
     * @param dpValue
     * @return
     */
    private int dp2Px(int dpValue) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) (metrics.density * dpValue + 0.5f);
    }

    private int getId(String pageUrl) {
        Destination destination = AppConfig.getDestConfig().get(pageUrl);
        if (destination == null) {
            return -1;
        }
        return destination.id;
    }
}
