package com.example.administrator.myapplication.common.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.net.CallServer;
import com.example.administrator.myapplication.common.utils.ActivityUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.ButterKnife;


/**
 * 抽象的activity，所有activity的基类
 */
public abstract class AbsBaseActivity extends FragmentActivity {
    public Activity aty;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResourceID());
        this.aty = this;
        ButterKnife.bind(this);
        ActivityUtil.create().addActivity(this);
        initView();
        setListener();
    }




    protected void initView(){

    }
    protected void setListener() {
    }

    public abstract  int layoutResourceID();

    public <T> void addNetRequest(int what, BaseRequest request) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(request.getSign());
        CallServer.getInstance().add(what, request);
    }

    @Override
    protected void onDestroy() {
        // 在组件销毁的时候调用队列的按照sign取消的方法即可取消。
//        CallServer.getInstance().cancelAll();
        super.onDestroy();
        ActivityUtil.create().finishActivity(this);
    }

    public void skipActivity(Activity aty, Class<?> cls) {
        this.showActivity(aty, cls);
        aty.finish();
    }

    public void skipActivity(Activity aty, Intent it) {
        this.showActivity(aty, it);
        aty.finish();
    }

    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        this.showActivity(aty, cls, extras);
        aty.finish();
    }

    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }

    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }




    public static final int REQUEST_CODE_INVALID = -1;

    private AtomicInteger mAtomicInteger = new AtomicInteger();
    private List<BaseFragment> mFragmentStack = new ArrayList<BaseFragment>();
    private Map<BaseFragment, FragmentStackEntity> mFragmentEntityMap = new HashMap<BaseFragment, FragmentStackEntity>();

    public static class FragmentStackEntity {
        private FragmentStackEntity() {
        }

        private boolean isSticky = false;
        private int requestCode = REQUEST_CODE_INVALID;
        @ResultCode
        public
        int resultCode = RESULT_CANCELED;
        public Bundle result = null;
    }

    public final <T extends BaseFragment> void startFragment(Class<T> clazz) {
        try {
            BaseFragment targetFragment = clazz.newInstance();
            startFragment(null, targetFragment, true, REQUEST_CODE_INVALID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final <T extends BaseFragment> void startFragment(Class<T> clazz, boolean stickyStack) {
        try {
            BaseFragment targetFragment = clazz.newInstance();
            startFragment(null, targetFragment, stickyStack, REQUEST_CODE_INVALID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final <T extends BaseFragment> void startFragment(T targetFragment) {
        startFragment(null, targetFragment, true, REQUEST_CODE_INVALID);
    }

    public final <T extends BaseFragment> void startFragment(T targetFragment, boolean stickyStack) {
        startFragment(null, targetFragment, stickyStack, REQUEST_CODE_INVALID);
    }

    public <T extends BaseFragment> void startFragmentForResquest(Class<T> clazz, int requestCode) {
        if (requestCode == REQUEST_CODE_INVALID)
            throw new IllegalArgumentException("The requestCode must be positive integer.");
        try {
            BaseFragment targetFragment = clazz.newInstance();
            startFragment(null, targetFragment, true, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends BaseFragment> void startFragmentForResquest(T targetFragment, int requestCode) {
        if (requestCode == REQUEST_CODE_INVALID)
            throw new IllegalArgumentException("The requestCode must be positive integer.");
        startFragment(null, targetFragment, true, requestCode);
    }

    public <T extends BaseFragment> void startFragment(T nowFragment, T targetFragment, boolean stickyStack, int
            requestCode) {


        FragmentStackEntity fragmentStackEntity = new FragmentStackEntity();
        fragmentStackEntity.isSticky = stickyStack;
        fragmentStackEntity.requestCode = requestCode;
        targetFragment.setStackEntity(fragmentStackEntity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (nowFragment != null) {
            if (mFragmentEntityMap.get(nowFragment).isSticky) {
                nowFragment.onPause();
                nowFragment.onStop();
                fragmentTransaction.hide(nowFragment);
            } else {
                fragmentTransaction.remove(nowFragment);
                fragmentTransaction.commit();
                mFragmentStack.remove(nowFragment);
                mFragmentEntityMap.remove(nowFragment);
                fragmentTransaction = fragmentManager.beginTransaction();
            }
        }
        String fragmentTag = targetFragment.getClass().getSimpleName() + mAtomicInteger.incrementAndGet();

        if (mFragmentStack.size()>1){
            BaseFragment outFragment = mFragmentStack.get(mFragmentStack.size() - 1);
            mFragmentStack.remove(outFragment);
            mFragmentEntityMap.remove(outFragment);
            fragmentTransaction.remove(outFragment);

        }
        fragmentTransaction.replace(fragmentLayoutId(), targetFragment, fragmentTag);
//        fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commitAllowingStateLoss();

        mFragmentStack.add(targetFragment);
        mFragmentEntityMap.put(targetFragment, fragmentStackEntity);


    }


    /**
     * 判断栈里是否还有fragment
     * @return
     */
    private boolean onBackStackFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mFragmentStack.size() > 1) {
            fragmentManager.popBackStack();
            BaseFragment inFragment = mFragmentStack.get(mFragmentStack.size() - 2);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.show(inFragment);
            fragmentTransaction.commit();

            BaseFragment outFragment = mFragmentStack.get(mFragmentStack.size() - 1);
            inFragment.onResume();

            FragmentStackEntity fragmentStackEntity = mFragmentEntityMap.get(outFragment);
            mFragmentStack.remove(outFragment);
            mFragmentEntityMap.remove(outFragment);

            if (fragmentStackEntity.requestCode != REQUEST_CODE_INVALID) {
                inFragment.onFragmentResult(fragmentStackEntity.requestCode, fragmentStackEntity.resultCode,
                        fragmentStackEntity.result);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!onBackStackFragment()) {
            finish();
        }
    }
    protected abstract
    @IdRes
    int fragmentLayoutId();

}
