package com.midian.ppaddress.datasource;

import android.content.Context;

import com.midian.ppaddress.bean.EntercarrierPageOwnerCarrierBean;
import com.midian.ppaddress.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

public class MyPropertyDataResource extends BaseListDataSource {
    private int pageSize=10;
    public MyPropertyDataResource(Context context) {
        super(context);
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        this.page=page;
        ArrayList<NetResult> models = new ArrayList<>();
        EntercarrierPageOwnerCarrierBean bean= AppUtil.getPpApiClient(ac).memberEntercarrierPageOwnerCarrier(ac.user_id,page,pageSize);
        if(bean.isOK()){
//            for(EntercarrierPageOwnerCarrierBean.Lists item : bean.getData().getList()){
//                models.add(bean);
//            }
            models.addAll(bean.getData().getList());
            if(bean.getData().getList().size()<10){
                hasMore=false;
            }else{
                hasMore=true;
            }
        }else{
            ac.handleErrorCode(context,bean.errorcode);
        }
        return models;
    }
}
