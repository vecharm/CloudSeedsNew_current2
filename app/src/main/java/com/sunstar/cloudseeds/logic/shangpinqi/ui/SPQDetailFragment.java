package com.sunstar.cloudseeds.logic.shangpinqi.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TableLayout;

import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.helper.EditItemRuleHelper;
import com.sunstar.cloudseeds.logic.shangpinqi.SPQActivity;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.SPQDetailBean;
import com.sunstar.cloudseeds.logic.shangpinqi.contract.SPQDetailContract;
import com.sunstar.cloudseeds.logic.shangpinqi.presenter.SPQDetailPresenterImpl;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPQDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPQDetailFragment extends ClassicMvpFragment<SPQDetailPresenterImpl> implements SPQDetailContract.View<SPQDetailBean> {



    public SPQDetailFragment() {
        // Required empty public constructor
    }

    @Override
    protected SPQDetailPresenterImpl setupPresenter() {
        return new SPQDetailPresenterImpl(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SPQDetialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SPQDetailFragment newInstance(String param1, String param2) {
        SPQDetailFragment fragment = new SPQDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_spq_detail;
    }
    TableLayout id_tl_item_container;
    @Override
    protected void initView(View view) {
        id_tl_item_container=findById(R.id.id_tl_item_container);

        toRefreshData();
    }

    @Override
    protected void initListener() {
        //
        setOnNotFastClickListener(findById(R.id.id_btn_show_add), new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                startAty(SPQActivity.class,createBundleExtraInt1(AtyGoToWhere.ADD));
            }
        });
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainData(UrlDatas.YU_ZHONG_XUAN_ZHU_DETAIL);
    }
    @Override
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }
    @Override
    public void showProgress() {
        showSwipeRefreshLayout();
    }

    @Override
    public void hideProgress() {
        hideSwipeRefreshLayout();
    }

    @Override
    public void showMessage(String s) {

    }

    @Override
    public void setupData(SPQDetailBean spqDetailBean) {
        //
        List<SPQDetailBean.KeyValueBean> kvbList = spqDetailBean.getKey_value();
        //
        EditItemRuleHelper.generateSPQChildView(getActivity(),id_tl_item_container,kvbList,false);
    }

    @Override
    public void setupMoreData(SPQDetailBean spqDetailBean) {

    }
}
