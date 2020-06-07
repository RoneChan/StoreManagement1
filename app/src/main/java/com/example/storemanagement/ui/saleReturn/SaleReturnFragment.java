package com.example.storemanagement.ui.saleReturn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.storemanagement.Control.OrderAdapter;
import com.example.storemanagement.R;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.tool.ExcelUtil;
import com.example.storemanagement.tool.FileDataStream;
import com.example.storemanagement.ui.getOrder.OrderDetail;
import com.example.storemanagement.ui.returnDetail.ReturnDetailActivity;

import java.util.ArrayList;

import static com.example.storemanagement.tool.FileDataStream.ORDER_LIST_CODE;
import static com.example.storemanagement.tool.FileDataStream.RETURN_LIST_CODE;

public class SaleReturnFragment extends Fragment {
    ArrayList<Order> orderArrayList = new ArrayList<>();
    OrderAdapter adapter;
    ListView lv_order;
    FileDataStream fileDataStream;
    ExcelUtil excelUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =inflater.inflate(R.layout.fragment_get_order, container, false);


        fileDataStream =new FileDataStream(getContext());
        orderArrayList=fileDataStream.load(RETURN_LIST_CODE);

        if(orderArrayList.size() == 0){
            view.findViewById(R.id.tv_no_order).setVisibility(View.VISIBLE);
        }else {
            lv_order = view.findViewById(R.id.lv_order_show);
            adapter = new OrderAdapter(container.getContext(), orderArrayList);
            lv_order.setAdapter(adapter);

            lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), ReturnDetailActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        }
        return view;
    }

}
