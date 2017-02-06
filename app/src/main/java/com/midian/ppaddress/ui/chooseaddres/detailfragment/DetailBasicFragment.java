package com.midian.ppaddress.ui.chooseaddres.detailfragment;

import com.midian.ppaddress.datasource.BasicFragemntDataSource;
import com.midian.ppaddress.itemtpl.BasicFragemntTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseListFragment;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * 基本参数fragment
 * Created by chu on 2016/3/22.
 */
public class DetailBasicFragment extends BaseListFragment {

    private String carrierid;

    public String getCarrierid() {
        return carrierid;
    }

    public void setCarrierid(String carrierid) {
        this.carrierid = carrierid;
    }


    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new BasicFragemntDataSource(_activity,carrierid);
    }

    @Override
    protected Class getTemplateClass() {
        return BasicFragemntTpl.class;
    }



    @Override
    public void onEndRefresh(IDataAdapter adapter, Object result) {

    }

    @Override
    public void onEndLoadMore(IDataAdapter adapter, Object result) {

    }
}
