package com.qlckh.chunlvv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.dao.MainDao;

import java.util.List;

/**
 * @author Andy
 * @date   2018/8/16 17:58
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    巡检分类adapter
 */
public class HomepageClassifyAdapter extends BaseAdapter {
    private  List<MainDao> mDatas;
    private Context mContext;

    public HomepageClassifyAdapter(Context mContext, List<MainDao> daos) {
        this.mContext = mContext;
        this.mDatas =daos;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HodlerView hodlerView = null;
        if (convertView == null){
            hodlerView = new HodlerView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_grigview_view,null);
            hodlerView.type_img = convertView.findViewById(R.id.type_img);
            hodlerView.type_name_tv = convertView.findViewById(R.id.type_name_tv);
            convertView.setTag(hodlerView);
        }else{
            hodlerView = (HodlerView) convertView.getTag();
        }
        hodlerView.type_img.setImageResource(mDatas.get(position).getIcon());
        hodlerView.type_name_tv.setText(mDatas.get(position).getTitle());
        return convertView;
    }
    class HodlerView{
        ImageView type_img;
        TextView type_name_tv;
    }
}
