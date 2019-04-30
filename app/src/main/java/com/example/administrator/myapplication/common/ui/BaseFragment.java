package com.example.administrator.myapplication.common.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.net.CallServer;

import butterknife.ButterKnife;

/**
 * 基类fragment
 */

public abstract class BaseFragment extends Fragment {
    public static final int RESULT_OK = Activity.RESULT_OK;
    public static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
    private static final int REQUEST_CODE_INVALID = AbsBaseActivity.REQUEST_CODE_INVALID;
    protected View convertView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResourceID(), container, false);
        ButterKnife.bind(this, view);

        initData();
        setListener();
        return view;
    }
//    public void juedgeNet(View view){
//        if (this instanceof EmptyLayout.OnClickEmptyLayoutListener) {
//            convertView = view.findViewById(R.id.layout_convertview);
//            emptyView = (EmptyLayout) view.findViewById(R.id.layout_emptylayout);
//
////            if (!SystemUtil.checkNet(getActivity())) {
////                hidConvertView();
////                emptyView.setEmptyType(EmptyLayout.TYPE_NO_NETWORK);
////            }else {
////                showConvertView();
////
////            }
//        }
//    }
//    public void showConvertView() {
//        if (convertView != null && emptyView != null) {
//            convertView.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
//        }
//    }
//
//    public void hidConvertView() {
//        if (convertView != null && emptyView != null) {
//            convertView.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//
//        }
//    }

    protected void setListener() {

    }

    public abstract int layoutResourceID();

    protected void initData() {

    }

    public static <T extends BaseFragment> T instantiate(Context context, Class<T> fragmentClass) {

        return (T) instantiate(context, fragmentClass.getName(), null);
    }

    public static <T extends BaseFragment> T instantiate(Context context, Class<T> fragmentClass, Bundle bundle) {

        return (T) instantiate(context, fragmentClass.getName(), bundle);
    }


    private AbsBaseActivity mActivity;

    protected final AbsBaseActivity getCompatActivity() {
        return mActivity;
    }

    protected final <T extends Activity> void startActivity(Class<T> clazz) {
        startActivity(new Intent(mActivity, clazz));
    }

    protected final <T extends Activity> void startActivityFinish(Class<T> clazz) {
        startActivity(new Intent(mActivity, clazz));
        mActivity.finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AbsBaseActivity) context;
    }

    public void finish() {
        mActivity.onBackPressed();
    }

    public <T> void addNetRequest(int what, BaseRequest request) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(request.getSign());
        CallServer.getInstance().add(what, request);
    }

    @Override
    public void onDestroyView() {
        // 在组件销毁的时候调用队列的按照sign取消的方法即可取消。
//        CallServer.getInstance().cancelAll();
        super.onDestroyView();
    }

    // ------------------------- Stack ------------------------- //

    private AbsBaseActivity.FragmentStackEntity mStackEntity;

    protected final void setResult(@ResultCode int resultCode) {
        mStackEntity.resultCode = resultCode;
    }

    protected final void setResult(@ResultCode int resultCode, @NonNull Bundle result) {
        mStackEntity.resultCode = resultCode;
        mStackEntity.result = result;
    }


    public final void setStackEntity(@NonNull AbsBaseActivity.FragmentStackEntity stackEntity) {
        this.mStackEntity = stackEntity;
    }


    public void onFragmentResult(int requestCode, @ResultCode int resultCode, @Nullable Bundle result) {
    }


    public final <T extends BaseFragment> void startFragment(Class<T> clazz) {
        try {
            BaseFragment targetFragment = clazz.newInstance();
            startFragment(targetFragment, true, REQUEST_CODE_INVALID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public final <T extends BaseFragment> void startFragment(Class<T> clazz, boolean stickyStack) {
        try {
            BaseFragment targetFragment = clazz.newInstance();
            startFragment(targetFragment, stickyStack, REQUEST_CODE_INVALID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final <T extends BaseFragment> void startFragment(T targetFragment) {
        startFragment(targetFragment, true, REQUEST_CODE_INVALID);
    }


    public final <T extends BaseFragment> void startFragment(T targetFragment, boolean stickyStack) {
        startFragment(targetFragment, stickyStack, REQUEST_CODE_INVALID);
    }

    public final <T extends BaseFragment> void startFragmentForResquest(Class<T> clazz, int requestCode) {
        try {
            BaseFragment targetFragment = clazz.newInstance();
            startFragment(targetFragment, true, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final <T extends BaseFragment> void startFragmentForResquest(T targetFragment, int requestCode) {
        startFragment(targetFragment, true, requestCode);
    }

    private <T extends BaseFragment> void startFragment(T targetFragment, boolean stickyStack, int requestCode) {
        mActivity.startFragment(this, targetFragment, stickyStack, requestCode);
    }
}
