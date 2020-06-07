package com.example.storemanagement.ui.getOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storemanagement.Control.OrderDetailAdapter;
import com.example.storemanagement.MainActivity;
import com.example.storemanagement.R;
import com.example.storemanagement.entity.Info;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.tool.FileDataStream;
import com.example.storemanagement.tool.UserDao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.storemanagement.tool.FileDataStream.ORDER_LIST_CODE;

public class OrderDetail extends AppCompatActivity{
    OrderDetailAdapter adapter;
    FileDataStream fileDataStream;
    ArrayList<Order> ordersList;
    UserDao userDao = new UserDao();
    Order order;
    Map<String, Info> result = new HashMap<>(); //Map<id,new Pair<>(pos, number))

    ListView listView;
    TextView tv_id;
    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();
        final int data = intent.getIntExtra("position",0);



        fileDataStream=new FileDataStream(this);
        ordersList=fileDataStream.load(ORDER_LIST_CODE);
        order=ordersList.get(data);

        tv_id = findViewById(R.id.tv_order_detail_id);
        tv_id.setText(order.getId());
        result =userDao.getPositions(order);

        adapter=new OrderDetailAdapter(this,order,result);
        listView = findViewById(R.id.lv_order_detail);
        listView.setAdapter(adapter);

        btn_confirm=findViewById(R.id.btn_order_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.noCheckedNumber==0){
                    finishOrder(data,adapter.info);
                    Toast.makeText(getApplicationContext(), "订单完成" , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "订单还未拣货完成" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void finishOrder( int position, ArrayList<Info> infos){
        UserDao userDao=new UserDao();
        // ArrayList<Product> clothes = order.getOderList();

        for(int i=0;i<infos.size();i++) {
            Info info = infos.get(i);
            userDao.updateClothes(info.id,info.id,info.resNumber-info.needNumber);
        }
        ordersList.remove(position);
        fileDataStream.setList(ordersList);
        fileDataStream.save(ORDER_LIST_CODE);


    }

}
