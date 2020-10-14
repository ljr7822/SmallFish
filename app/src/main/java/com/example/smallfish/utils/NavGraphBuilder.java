package com.example.smallfish.utils;

import android.content.ComponentName;

import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

import com.example.smallfish.model.Destination;

import java.util.HashMap;

/**
 * @author iwen大大怪
 * Create to 2020/10/14 16:03
 */
public class NavGraphBuilder {

    public static void build(NavController controller) {
        // 从NavController中提取出NavigatorProvider的hashMap对象，因为里面存储了两个navigator
        NavigatorProvider provider = controller.getNavigatorProvider();

        FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);
        //NavGraphNavigator也是页面路由导航器的一种，只不过他比较特殊。
        //它只为默认的展示页提供导航服务,但真正的跳转还是交给对应的navigator来完成的
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        HashMap<String, Destination> destConfig = AppConfig.getDestConfig();
        for (Destination value : destConfig.values()) {
            // 判断页面类型，再创建对像
            if (value.isFragment()){
                FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
                destination.setId(value.getId());
                destination.setClassName(value.getClassName());
                destination.addDeepLink(value.getPageUrl());
                navGraph.addDestination(destination);
            }else {
                ActivityNavigator.Destination destination = activityNavigator.createDestination();
                destination.setId(value.getId());
                destination.setComponentName(new ComponentName(AppGlobals.getApplication().getPackageName(), value.getClassName()));
                destination.addDeepLink(value.getPageUrl());
                navGraph.addDestination(destination);
            }

            //给APP页面导航结果图 设置一个默认的展示页的id
            if (value.isAsStarter()) {
                navGraph.setStartDestination(value.getId());
            }
        }
        controller.setGraph(navGraph);
    }
}
