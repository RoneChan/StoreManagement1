package com.example.storemanagement.Control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.storemanagement.R;
import com.example.storemanagement.entity.Order;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    ArrayList<Order> orderArrayList;
    private Context context;

    public OrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orderArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderViewHolder viewHolder;
        if (convertView == null) {
            //若是第一次创建
            convertView = LayoutInflater.from(context).inflate(R.layout.get_order_item, parent, false);
            viewHolder = new OrderViewHolder();
            viewHolder.id = convertView.findViewById(R.id.tv_order_id_show);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (OrderViewHolder) convertView.getTag();
        }

        String id = orderArrayList.get(position).getId();
        viewHolder.id.setText(id);

        return convertView;
    }

    //存放复用的组件
    class OrderViewHolder {
        TextView id;
    }

}


