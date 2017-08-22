package com.example.lixin.lixin1506a20170821;
/**
 * fragment界面 用来请求数据 上下拉刷新
 * 姓名 李鑫
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lixin.lixin1506a20170821.adapter.MyBaseAdapter;
import com.example.lixin.lixin1506a20170821.bean.MenuInfo;
import com.example.lixin.lixin1506a20170821.database.DbUtils;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 2017/8/21.
 */

public class MyFragment extends Fragment implements XListView.IXListViewListener {

    private View view;
    private int index = 1;
    private boolean flag;
    private MyBaseAdapter adapter;
    private XListView xListView;
    private DbUtils dbUtils;
    private MyBaseAdapter adapter1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_myfragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xListView = (XListView) view.findViewById(R.id.xlistview);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        dbUtils = new DbUtils(getActivity());
        SharedPreferences sp = getActivity().getSharedPreferences("key",Context.MODE_PRIVATE);
        boolean isfer = sp.getBoolean("isfer", true);
        SharedPreferences.Editor edit = sp.edit();
        if (isfer){
            getData();
            edit.putBoolean("isfer",false);
            edit.commit();
        }else {
            ArrayList<MenuInfo.ResultBean.DataBean> data = dbUtils.findAll();
            adapter1 = new MyBaseAdapter(data,getActivity());
            xListView.setAdapter(adapter1);
        }
    }
    public void getData() {
        String url = "http://apis.juhe.cn/cook/query.php";
        final RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("menu","鱼香肉丝");
        params.addQueryStringParameter("key","5281ef53545fe39b3421ed43324798c9");
        params.addQueryStringParameter("pn",index+"");
        params.addQueryStringParameter("rn",5+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MenuInfo menuInfo = gson.fromJson(result, MenuInfo.class);
                List<MenuInfo.ResultBean.DataBean> data = menuInfo.getResult().getData();

                if (adapter == null){
                    adapter = new MyBaseAdapter(data,getActivity());
                    xListView.setAdapter(adapter);
                }else {
                    adapter.loadMore(data,flag);
                    adapter.notifyDataSetChanged();
                }
                ArrayList<MenuInfo.ResultBean.DataBean> all = dbUtils.findAll();
                if (all == null||all.size()<1){
                   for (int i = 0 ; i<data.size();i++) {
                       dbUtils.add(data.get(i).getTitle(),data.get(i).getAlbums().get(0));
                   }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "请求失败！！！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onRefresh() {
        index++;
        getData();
        flag = true;
        xListView.stopRefresh(true);
    }

    @Override
    public void onLoadMore() {
        index++;
        getData();
        flag = false;
        xListView.stopLoadMore();
    }
}
