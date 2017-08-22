package com.example.lixin.lixin1506a20170821.adapter;
/**
 * baseadapter 适配器 用来往想listview中添加数据
 * 姓名 李鑫
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lixin.lixin1506a20170821.R;
import com.example.lixin.lixin1506a20170821.bean.MenuInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by hua on 2017/8/21.
 */

public class MyBaseAdapter extends BaseAdapter {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageOnFail(R.mipmap.ic_launcher)
            .showImageOnLoading(R.mipmap.ic_launcher_round)
            .build();

    private Context context;
    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private List<MenuInfo.ResultBean.DataBean> data;
    private TextView delete_tv;
    private TextView close_tv;

    public MyBaseAdapter(List<MenuInfo.ResultBean.DataBean> data, Context context){
        this.data = data;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        initPopView();
    }
    public void loadMore(List<MenuInfo.ResultBean.DataBean> datas,boolean flag){

        for (MenuInfo.ResultBean.DataBean bean : datas){

            if (flag){
                data.add(0,bean);
            }else {
                data.add(bean);
            }

        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){

            convertView = View.inflate(context,R.layout.item,null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
            holder.iv = (ImageView) convertView.findViewById(R.id.item_iv);
            holder.iv2 = (ImageView) convertView.findViewById(R.id.item_iv2);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(data.get(position).getTitle());
        ImageLoader.getInstance().displayImage(data.get(position).getAlbums().get(0),holder.iv,options);
        holder.iv2.setOnClickListener(new PopAction(position));
        return convertView;
    }
    class ViewHolder{
        TextView tv;
        ImageView iv,iv2;
    }

    class PopAction implements View.OnClickListener {
        private int position;
        public PopAction(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            //操作对应的position数据
            int[] array = new int[2];
            v.getLocationOnScreen(array);
            int x = array[0];
            int y = array[1];
            showPop(v,position,x,y);
        }
    }
    private void initPopView() {

        View popupwindowLayout = mLayoutInflater.inflate(R.layout.activity_pop, null);
        mPopupWindow = new PopupWindow(popupwindowLayout,ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        delete_tv = (TextView) popupwindowLayout.findViewById(R.id.delete_tv);
        close_tv = (TextView) popupwindowLayout.findViewById(R.id.close_tv);
    }
    private void showPop(View v, final int position, int x, int y) {
        mPopupWindow.showAtLocation(v,0,x,y);
        mPopupWindow.showAsDropDown(v,0,-50);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);

        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                notifyDataSetChanged();
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });
        close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });
    }
}
