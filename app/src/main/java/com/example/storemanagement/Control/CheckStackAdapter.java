package com.example.storemanagement.Control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.storemanagement.R;
import com.example.storemanagement.entity.Inventory;

import java.util.ArrayList;
import java.util.Calendar;

public class CheckStackAdapter extends ArrayAdapter<Inventory> {
    int resource;
   ArrayList<Inventory> list=new ArrayList<>();
    public CheckStackAdapter(@NonNull Context context, int resource,@NonNull ArrayList<Inventory> objects) {
        super(context, resource);
        this.resource=resource;
        this.list=objects;
    }

    @Nullable
    @Override
    public Inventory getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            //若是第一次创建
            convertView = LayoutInflater.from(this.getContext()).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.id =(TextView) convertView.findViewById(R.id.tv_item_id);
            viewHolder.output=(TextView) convertView.findViewById(R.id.tv_item_output);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置控件的值
        Inventory inventory=this.getItem(position);

        if (inventory != null) {
            String oriId = inventory.getOriId();
            String curId = inventory.getCurId();
            int oriNumber = inventory.getOriNumber();
            int curNumber=inventory.getCurNumber();

            viewHolder.id.setText(curId);
            if(oriId.equals(curId)){  //Id没有改变
                viewHolder.output.setText((curNumber-oriNumber)+"");
            }else{  //id改变了，即原来的id数量变为0,现有id的数量变化为修改的数量
                viewHolder.output.setText(curNumber+"");
                list.add(position+1,new Inventory(oriId,oriId,oriNumber,0));
            }
        }
        return convertView;
    }

    //存放复用的组件
    class ViewHolder {
        TextView id, output;
    }
}
