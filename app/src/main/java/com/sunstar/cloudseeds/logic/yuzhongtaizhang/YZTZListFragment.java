package com.sunstar.cloudseeds.logic.yuzhongtaizhang;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.widget.ClassicEmptyView;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicMvpFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.adapter.YZTZListAdapter;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.contract.YZTZListContract;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.presenter.YZTZListPresenterImpl;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.XuanZhuActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YZTZListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YZTZListFragment extends ClassicMvpFragment<YZTZListPresenterImpl> implements YZTZListContract.View<List<YZTZListBean.ListBean>>{



    public YZTZListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YZTZListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YZTZListFragment newInstance(String param1, String param2) {
        YZTZListFragment fragment = new YZTZListFragment();
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
        return R.layout.fragment_yztz_list;
    }

    @Override
    protected void initView(View view) {
        toRefreshData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected YZTZListPresenterImpl setupPresenter() {
        return new YZTZListPresenterImpl(this);
    }

    @Override
    protected int configSwipeRefreshLayoutResId() {
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    protected int configRecyclerViewResId() {
        return R.id.id_recycler_view;
    }

    @Override
    protected void toLoadMoreData() {
        super.toLoadMoreData();
        mPresenter.gainMoreData(mClassicRVHeaderFooterAdapter.getNextPageNum());
    }

    @Override
    protected void toRefreshData() {
        super.toRefreshData();
        mPresenter.gainCountData(mClassicRVHeaderFooterAdapter.getNowPageCount());
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
    public void showMessage(String msg) {
     //###   ToastTool.showShortCenter(msg);
        DialogManager.showTipDialog(getActivity(),"提示",msg,null);
    }

    @Override
    public void setupData(List<YZTZListBean.ListBean> yztzBeanList) {
        mClassicRVHeaderFooterAdapter.refreshDataList(yztzBeanList);
        //
        mRecyclerView.setVisibility(View.VISIBLE);//返回数据后 显示
    }

    @Override
    public void setupMoreData(List<YZTZListBean.ListBean> yztzBeanList) {
        mClassicRVHeaderFooterAdapter.addDataListAtEnd(yztzBeanList);
        if (yztzBeanList.size() == 0) {
            //所有数据加载完毕
            mClassicRVHeaderFooterAdapter.showFooterViewLoadComplete();
        } else {
            //一次加载完成
            mClassicRVHeaderFooterAdapter.turnNextPageNum();
            mClassicRVHeaderFooterAdapter.showFooterViewNormal();
        }
    }

    @Override
    protected ClassicRVHeaderFooterAdapter configClassicRVHeaderFooterAdapter() {
        List<YZTZListBean.ListBean> listBeanList = new ArrayList<>();
        YZTZListAdapter adapter
                = new YZTZListAdapter(mContext,listBeanList, R.layout.item_list_yu_zhong_tai_zhang);
        ClassicEmptyView classicEmptyView = new ClassicEmptyView(getContext());
        classicEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter.setEmptyView(classicEmptyView);
        adapter.setOnItemClickListener(new ClassicRVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                super.onItemClick(itemView, position);
                //
                startAty(XuanZhuActivity.class);
            }
        });
        adapter.setOnItemOperationListener(new YZTZListAdapter.OnItemOperationListener() {
            @Override
            public void onItemShowDetail(int position) {
                super.onItemShowDetail(position);
                //
                startAty(YZTZActivity.class,createBundleExtraInt1(AtyGoToWhere.DETAIL));
            }

            @Override
            public void onItemShowXuanZhu(int position) {
                super.onItemShowXuanZhu(position);
                ToastTool.showShortCenter("选株"+position);
            }

            @Override
            public void onItemShowQrcode(int position) {
                super.onItemShowQrcode(position);
                ToastTool.showShortCenter("绑定二维码"+position);
            }
        });
        mRecyclerView.setVisibility(View.GONE);//初始化 不显示
        return adapter;
    }

}
