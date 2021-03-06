package com.midian.ppaddress.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.midian.login.api.LoginApiClient;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;
import com.midian.ppaddress.R;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * Created by Administrator on 2016/4/28.
 */
public class ChangeBankCardActivity_1 extends BaseActivity implements View.OnClickListener{
    private BaseLibTopbarView topbar;
    private EditText et_Number,et_CheckCode;
    private Button btn_Next,btn_CheckCode;
    private LinearLayout ll_phone,ll_code;
    private String phone,code;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changebankcard_1);
        initView();
    }

    private void initView() {
        topbar=findView(R.id.topbar);
        topbar.setTitle("更换支付信息").setLeftImageButton(R.drawable.icon_back, UIHelper.finish(this));
        ll_phone=findView(R.id.ll_phone);
        ll_code=findView(R.id.ll_code);
        btn_Next=findView(R.id.btn_next);
        btn_CheckCode=findView(R.id.btn_checkcode);
        et_Number=findView(R.id.et_number);
        et_CheckCode=findView(R.id.et_checkcode);
        btn_Next.setOnClickListener(this);
        btn_CheckCode.setOnClickListener(this);
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        if (res.isOK()) {
            if ("sendCode".equals(tag)) {
                UIHelper.t(_activity, "发送成功");
            }
            if ("validateCode".equals(tag)) {
                Bundle mBundle = new Bundle();
                mBundle.putString("phone", phone);
                mBundle.putInt("type", type);
                mBundle.putString("code", code);
                UIHelper.jumpForResult(_activity,ChangeBankCardActivity_2.class,mBundle,1005);
            }
        } else {
            ac.handleErrorCode(_activity, res.message);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = new Bundle();
            setResult(RESULT_OK, bundle);
            finish();
        }
    }

    @Override
    public void onClick(View arg0) {
        phone=et_Number.getText().toString().trim();
        type=5;//必填；1:注册, 2:忘记密码, 3:更换手机, 4:修改密码, 5:更改支付信息
        code=et_CheckCode.getText().toString().trim();
        super.onClick(arg0);
        switch (arg0.getId()){
            case R.id.btn_next://下一步
                if (ValidateTools.isEmptyString(phone)) {
                ObjectAnimatorTools.onVibrationView(ll_phone);
                UIHelper.t(_activity, com.midian.login.R.string.hint_phone_not_empty);
                return;
            }
                if (!Func.isMobileNO(phone)) {
                    ObjectAnimatorTools.onVibrationView(ll_phone);
                    UIHelper.t(_activity, com.midian.login.R.string.hint_phone_error);
                    return;
                }
                if (ValidateTools.isEmptyString(code)) {
                    ObjectAnimatorTools.onVibrationView(ll_code);
                    UIHelper.t(_activity, com.midian.login.R.string.hint_auth_code_not_empty);
                    return;
                }
                ac.api.getApiClient(LoginApiClient.class).validateCode(phone,type,code, this);// 验证验证码
                btn_Next.setClickable(false);
                break;
            case R.id.btn_checkcode://获取验证码
                if (ValidateTools.isEmptyString(phone)) {
                    ObjectAnimatorTools.onVibrationView(ll_phone);
                    UIHelper.t(this, R.string.hint_phone_not_empty);
                    return;
                }
                if (!ValidateTools.isPhoneNumber(phone)) {
                    ObjectAnimatorTools.onVibrationView(ll_phone);
                    UIHelper.t(this, com.midian.login.R.string.hint_phone_error);
                    return;
                }
                ac.api.getApiClient(LoginApiClient.class).sendCode(phone, type, this);//发送验证码
                break;
        }
    }
}
