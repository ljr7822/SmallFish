package com.example.libcommon;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 提供全局的Application对象实例
 * author : Iwen大大怪
 * create : 2020/10/26 21:50
 */
public class AppGlobals {
    private static Application sApplication;

    public static Application getApplication() {

        if (sApplication == null) {
            try {
                Method method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                sApplication = (Application) method.invoke(null, null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return sApplication;
    }
}
