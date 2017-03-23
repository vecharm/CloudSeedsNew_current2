package com.sunstar.cloudseeds.logic.login.model;


import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.factory.httprequest.HttpRequestManagerFactory;
import com.classichu.classichu.basic.factory.httprequest.abstracts.GsonHttpRequestCallback;
import com.sunstar.cloudseeds.bean.BasicBean;
import com.sunstar.cloudseeds.logic.helper.HeadsParamsHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.login.contract.LoginContract;

import java.util.HashMap;


/**
 * Created by Administrator on 2017/3/16.
 */

public class LoginModelImpl implements LoginContract.Model<UserLoginBean>{

    @Override
    public void loadData (String url,String username, String paw,final BasicCallBack<UserLoginBean> basicCallBack) {
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("username",username);
        paramsMap.put("password",paw);
        HttpRequestManagerFactory.getRequestManager().postUrlBackStr(url, HeadsParamsHelper.setupDefaultHeaders(),paramsMap,
                new GsonHttpRequestCallback<BasicBean<UserLoginBean>>() {
                    @Override
                    public BasicBean<UserLoginBean> OnSuccess(String result) {
                        BasicBean<UserLoginBean> BB= BasicBean.fromJson(result,UserLoginBean.class);
                        return BB;
                    }
                    @Override
                    public void OnSuccessOnUI(BasicBean<UserLoginBean> basicBean) {

                        //UserLoginBean userloginBean=UserLoginBean.getUserLoginBean();
                        UserLoginBean userloginBean=basicBean.getInfo().get(0);

                        if ("1".equals(basicBean.getCode())){
                            basicCallBack.onSuccess(userloginBean);
                        }else{
                            basicCallBack.onError(basicBean.getMessage());
                        }
                    }
                    @Override
                    public void OnError(String errorMsg) {
                        basicCallBack.onError(errorMsg);
                    }
                });
    }

    @Override
    public void loadData(String s, int i, int i1, String s1, BasicCallBack<UserLoginBean> basicCallBack) {

    }

}
