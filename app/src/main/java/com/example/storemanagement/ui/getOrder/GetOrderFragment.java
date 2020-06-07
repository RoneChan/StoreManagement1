package com.example.storemanagement.ui.getOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.storemanagement.Control.OrderAdapter;
import com.example.storemanagement.R;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.tool.FileDataStream;

import java.util.ArrayList;

import static com.example.storemanagement.tool.FileDataStream.ORDER_LIST_CODE;

public class GetOrderFragment extends Fragment {
    ArrayList<Order> orderArrayList = new ArrayList<>();
    OrderAdapter adapter;
    FileDataStream fileDataStream;
    ListView lv_order;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_get_order, container, false);

        fileDataStream =new FileDataStream(getContext());
        orderArrayList=fileDataStream.load(ORDER_LIST_CODE);
        if(orderArrayList.size() == 0){
            view.findViewById(R.id.tv_no_order).setVisibility(View.VISIBLE);
        }else {
            lv_order = view.findViewById(R.id.lv_order_show);
            adapter = new OrderAdapter(container.getContext(), orderArrayList);
            lv_order.setAdapter(adapter);

            lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), OrderDetail.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
}