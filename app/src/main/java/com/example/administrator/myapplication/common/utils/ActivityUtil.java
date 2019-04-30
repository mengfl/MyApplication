package com.example.administrator.myapplication.common.utils;

import android.app.Activity;
import android.content.Context;


import com.example.administrator.myapplication.common.ui.AbsBaseActivity;

import java.util.Iterator;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class ActivityUtil {
    private static Stack<AbsBaseActivity> activityStack;
    private static final ActivityUtil instance = new ActivityUtil();

    private ActivityUtil() {

    }

    public static ActivityUtil create() {
        return instance;
    }

    public int getCount() {
        return activityStack.size();
    }

    public void addActivity(AbsBaseActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }

        activityStack.add(activity);
    }

    public Activity topActivity() {
        if (activityStack == null) {
            throw new NullPointerException("Activity stack is Null,your Activity must extend KJActivity");
        } else if (activityStack.isEmpty()) {
            return null;
        } else {
            AbsBaseActivity activity = (AbsBaseActivity) activityStack.lastElement();
            return (Activity) activity;
        }
    }

    public Activity findActivity(Class<?> cls) {
        AbsBaseActivity activity = null;
        Iterator var4 = activityStack.iterator();

        while (var4.hasNext()) {
            AbsBaseActivity aty = (AbsBaseActivity) var4.next();
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }

        return (Activity) activity;
    }

    public void finishActivity() {
        AbsBaseActivity activity = (AbsBaseActivity) activityStack.lastElement();
        this.finishActivity((Activity) activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (activity != null) {
                activity.finish();
                activity = null;
            }
        }

    }

    public void finishActivity(Class<?> cls) {
        Iterator var3 = activityStack.iterator();

        while (var3.hasNext()) {
            AbsBaseActivity activity = (AbsBaseActivity) var3.next();
            if (activity.getClass().equals(cls)) {
                this.finishActivity((Activity) activity);
            }
        }

    }

    public void finishOthersActivity(Class<?> cls) {
//        Iterator var3 = activityStack.iterator();
//
//        while (var3.hasNext()) {
//            AbsBaseActivity activity = (AbsBaseActivity) var3.next();
//            Log.e("aaaaaaa", activity.getClass() + "----"+cls);
//            if (!activity.getClass().equals(cls)) {
//                this.finishActivity((Activity) activity);
//            }
//        }
        int i = 0;

        for (int size = activityStack.size(); i < size; ++i) {
            if (activityStack.get(i) != null) {
                if (!activityStack.get(i).getClass().equals(cls)) {
                    ((Activity) activityStack.get(i)).finish();
                }
            }
        }

        activityStack.clear();
    }

    public void finishAllActivity() {
        int i = 0;

        for (int size = activityStack.size(); i < size; ++i) {
            if (activityStack.get(i) != null) {
                ((Activity) activityStack.get(i)).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void AppExit(Context cxt) {
        this.appExit(cxt);
    }

    public void appExit(Context context) {

        try {
            this.finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception var3) {
            Runtime.getRuntime().exit(-1);
        }

    }
}
