package com.example.storemanagement.Control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.storemanagement.R;
import com.example.storemanagement.entity.Info;
import com.example.storemanagement.entity.Order;

import java.util.ArrayList;
import java.util.Map;

public class OrderDetailAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<Info> info=new ArrayList<>();
    OrderViewHolder viewHolder;
    public int noCheckedNumber;

    public OrderDetailAdapter(Context context,Order order,Map<String, Info> infos) {
        this.context = context;
        //clothes=order.getOderList();
        for(Map.Entry<String, Info> entry : infos.entrySet()){
            info.add(entry.getValue());
        }
        noCheckedNumber=info.size();
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return  info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //若是第一次创建
            convertView = LayoutInflater.from(context).inflate(R.layout.order_detail_item, parent, false);
            viewHolder = new OrderViewHolder();
            viewHolder.id = convertView.findViewById(R.id.tv_detail_id);
            viewHolder.number = convertView.findViewById(R.id.tv_detail_number);
            viewHolder.position = convertView.findViewById(R.id.tv_detail_position);
            viewHolder.checkBox=convertView.findViewById(R.id.checkBox);

            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        noCheckedNumber--;

                    }else{
                        noCheckedNumber++;
                    }
                }
            });


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (OrderViewHolder) convertView.getTag();
        }

        Info clo = info.get(position);
        String clothesId= clo.id;
        viewHolder.id.setText("服装ID:"+clothesId);

        int number = clo.needNumber;
        int restNumber = clo.resNumber;
        viewHolder.number.setText("数量:"+number+"     库存数量:"+restNumber);
        String pos = clo.position;
        viewHolder.position.setText("位置:"+pos);

        return convertView;
    }

    //存放复用的组件
    class OrderViewHolder {
        TextView id;
        TextView number;
        TextView position;
        CheckBox checkBox;
    }
}


