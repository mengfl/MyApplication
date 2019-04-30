package com.example.administrator.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.UserInfo;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.AbsBaseActivity;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.DownloadUtils;
import com.example.administrator.myapplication.common.utils.PreferenceUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.http.LoginRequest;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * 登录
 */
public class LoginActivity extends AbsBaseActivity {


    @BindView(R.id.act_login_et_phone)
    EditText etPhone;
    @BindView(R.id.act_login_et_pwd)
    EditText etPwd;
    @BindView(R.id.act_login_cb_remember)
    CheckBox cbRemember;
    @BindView(R.id.act_login_btn_login)
    Button btnLogin;

    private String name;
    private String password;



    @Override
    public int layoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        applySD();
        initDefault();
    }

    @Override
    protected void onStart() {
        super.onStart();
        JPushInterface.resumePush(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JPushInterface.stopPush(this);
    }
    /**
     * 版本更新提示弹窗
     */
    public void showUpdateDialog(final String url) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this);
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_reminder, null);
        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_sure = (Button) view.findViewById(R.id.btn_sure);
        tv_content.setText("发现新版本，更新版本");
        btn_cancel.setVisibility(View.GONE);
        btn_sure.setText("更新");
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                uploadNewApk(url);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void applySD() {
        AndPermission.with(this)
                .requestCode(100)
                .permission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA,android.Manifest.permission.READ_PHONE_STATE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        ConstantUtil.init(MyApplication.get());
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    }
                })
                .start();
    }


    /**
     * 启动service下载
     *
     * @param downLoadUrl
     */
    private void uploadNewApk(String downLoadUrl) {
        DownloadUtils downloadUtils = new DownloadUtils(this);
        downloadUtils.downloadAPK(downLoadUrl, ConstantUtil.APK_NAME);
        Toast.makeText(this, getString(R.string.app_update), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void setListener() {
        super.setListener();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etPhone.getText().toString();
                password = etPwd.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    login();
                } else {
                    ViewInjectUtil.toast("请填写用户名和密码");

                }

            }
        });
    }
    private void initDefault() {
        boolean i = PreferenceUtil.readBoolean(LoginActivity.this, ConstantUtil.SHARE_KEY_REMEMBER, false);
        if (i) {
            name = PreferenceUtil.readString(LoginActivity.this, ConstantUtil.SHARE_KEY_ACCOUNT);
            password = PreferenceUtil.readString(LoginActivity.this, ConstantUtil.SHARE_KEY_PWD);
            etPhone.setText(name);
            etPwd.setText(password);
        }

    }

    private void login() {
        LoginRequest request = new LoginRequest(this, NetHelper.URL_LOGIN, name, password);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                UserInfo userInfo = (UserInfo) object;
                if (userInfo != null) {
                    if (cbRemember.isChecked()) {
                        PreferenceUtil.write(LoginActivity.this, ConstantUtil.SHARE_KEY_REMEMBER, true);
                        PreferenceUtil.write(LoginActivity.this, ConstantUtil.SHARE_KEY_ACCOUNT, name);
                        PreferenceUtil.write(LoginActivity.this, ConstantUtil.SHARE_KEY_PWD, password);
                    } else {
                        PreferenceUtil.write(LoginActivity.this, ConstantUtil.SHARE_KEY_REMEMBER, false);
                        PreferenceUtil.write(LoginActivity.this, ConstantUtil.SHARE_KEY_ACCOUNT, "");
                        PreferenceUtil.write(LoginActivity.this, ConstantUtil.SHARE_KEY_PWD, "");
                    }

                    JPushInterface.setAlias(LoginActivity.this, 0,JPushInterface.getRegistrationID(LoginActivity.this));

                    Intent intent = new Intent(LoginActivity.this, ChooseCarActivity.class);
                    intent.putExtra(ConstantUtil.BUNDLE_KEY_STRING, name);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0, request);
    }
}
