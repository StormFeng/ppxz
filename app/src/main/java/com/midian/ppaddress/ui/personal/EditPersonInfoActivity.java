package com.midian.ppaddress.ui.personal;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.midian.login.R;
import com.midian.ppaddress.utils.AppUtil;

import java.io.FileNotFoundException;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 编辑个人资料页面
 *
 * @author chu
 */
public class EditPersonInfoActivity extends BaseActivity {

    private BaseLibTopbarView topbar;
    private EditText content_ed;

    private String title;
    private String content;
    private int memberid,roletype;
    private int sex;
    private String declaration;
    // 完成事件
//    private OnClickListener rightClickListener = new OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            // 把获取到的输入内容返回前一页
//            String content = content_ed.getText().toString().trim();
//            Bundle bundle = new Bundle();
//            bundle.putString("text", content);
//            setResult(RESULT_OK, bundle);
//            finish();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person_info);
        // Bundle mBundle=getIntent().getExtras();
        // 获取要编辑的标题、内容，显示在控件上
        title = mBundle.getString("title");
        content = mBundle.getString("content");
        memberid=mBundle.getInt("memberid");
        roletype=mBundle.getInt("roletype");
        declaration=mBundle.getString("declaration");
        sex=mBundle.getInt("sex");
        topbar = findView(R.id.topbar);
        topbar.setTitle(title).setLeftImageButton(R.drawable.icon_back, UIHelper.finish(_activity)).setRightText("完成", this);
        content_ed = findView(R.id.content_et);
        if ("未填写地址".equals(content)) {
            content_ed.setHint("请输入" + title);
        } else {
            content_ed.setHint("请输入" + title);
            content_ed.setText(content);
        }


        // 如果为isPhone,则只能输入数字
        if (mBundle.getBoolean("isPhone")) {
            // 设置限制输入的数字长度
            content_ed.setSelection(content.length());
            content_ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            content_ed.setInputType(InputType.TYPE_CLASS_PHONE);
        }


    }

    @Override
    public void onClick(View v) {
        content = content_ed.getText().toString().trim();
        System.out.println("点击后修改的内容：：：" + content);
        super.onClick(v);
        int id = v.getId();
        int userId=Integer.valueOf(ac.user_id);
        if (id == R.id.right_tv) {
            if ("姓名".equals(title)) {
                try {
                    AppUtil.getPpApiClient(ac).memberMembershipInfoUpdate(userId,content,ac.sex,null,null,null,ac.user_type,null,declaration,null,null,this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ("个人宣言".equals(title)) {
                try {
                    AppUtil.getPpApiClient(ac).memberMembershipInfoUpdate(userId,null,ac.sex,null,null,null,ac.user_type,null,content,null,null,this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg();
        topbar.showProgressBar();
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        if (res.isOK()) {
            hideLoadingDlg();
            topbar.hideProgressBar();
            // 把获取到的输入内容返回前一页
            Bundle bundle = new Bundle();
            bundle.putString("content", content);
            UIHelper.t(_activity, "修改成功!");
            setResult(RESULT_OK, bundle);
            finish();
        }
    }
}
