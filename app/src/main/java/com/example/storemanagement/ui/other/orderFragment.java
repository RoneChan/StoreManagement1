package com.example.storemanagement.ui.other;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.storemanagement.R;
import com.example.storemanagement.ui.InputOrder.InputOrder;
import com.example.storemanagement.ui.checkStack.CheckStack;
import com.example.storemanagement.ui.orderImport.OrderImport;

import static com.example.storemanagement.tool.FileDataStream.ENTRY_LIST_CODE;
import static com.example.storemanagement.tool.FileDataStream.ORDER_LIST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class orderFragment extends Fragment {

    TextView tv_export,tv_order,tv_entry,tv_import;


    public orderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        tv_export=view.findViewById(R.id.tv_export);
        tv_order=view.findViewById(R.id.tv_order);
        tv_entry=view.findViewById(R.id.tv_entry);
        tv_import=view.findViewById(R.id.tv_import);

        tv_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckStack.class);
                startActivity(intent);
            }
        });

        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InputOrder.class);
                intent.putExtra("state",ORDER_LIST_CODE);
                startActivity(intent);
            }
        });

        tv_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InputOrder.class);
                intent.putExtra("state",ENTRY_LIST_CODE);
                startActivity(intent);
            }
        });

        tv_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderImport.class);
                startActivity(intent);
            }
        });

        return view;


    }

}
