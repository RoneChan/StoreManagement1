package com.example.storemanagement.ui.InputOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.storemanagement.MainActivity;
import com.example.storemanagement.R;
import com.example.storemanagement.entity.Clothes;
import com.example.storemanagement.entity.ClothesOrder;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.entity.Product;
import com.example.storemanagement.tool.FileDataStream;

import java.util.ArrayList;

import static com.example.storemanagement.tool.FileDataStream.ORDER_LIST_CODE;

public class InputOrder extends AppCompatActivity implements View.OnClickListener{

    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addHotelNameView;
    FileDataStream fileDataStream;
    int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_order);

        fileDataStream=new FileDataStream(this);
        state=getIntent().getIntExtra("state",-1);

        addHotelNameView = (LinearLayout) findViewById(R.id.ll_addView);
        findViewById(R.id.btn_addData).setOnClickListener(this);
        findViewById(R.id.btn_confirmData).setTag("add");
        findViewById(R.id.btn_confirmData).setOnClickListener(this);

        //默认添加一个Item
        addViewItem(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addData://点击添加按钮就动态添加Item
                addViewItem(v);
                break;
            case R.id.btn_confirmData://打印数据
                printData();
                break;
        }
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addHotelNameView.getChildCount(); i++) {
            final View childAt = addHotelNameView.getChildAt(i);
            final Button btn_remove = (Button) childAt.findViewById(R.id.btn_remove);
            btn_remove.setText("删除");
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    addHotelNameView.removeView(childAt);
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        String str;
        if(view !=null){
            str  =  (String) view.getTag();
        }
        if (addHotelNameView.getChildCount() == 0) {//如果一个都没有，就添加一个
            View orderEvaluateView = View.inflate(this, R.layout.input_order_item, null);
            Button btn_remove = (Button) orderEvaluateView.findViewById(R.id.btn_remove);
            btn_remove.setText("删除");
            btn_remove.setTag("remove");
            btn_remove.setOnClickListener(this);
            addHotelNameView.addView(orderEvaluateView);
            //sortHotelViewItem();
        } else{//如果有一个以上的Item,点击为添加的Item则添加
            View hotelEvaluateView = View.inflate(this, R.layout.input_order_item, null);
            addHotelNameView.addView(hotelEvaluateView);
            sortHotelViewItem();
        }

    }

    //获取所有动态添加的Item，找到控件的id，获取数据
    private void printData() {
        ArrayList<Product> clothes=new ArrayList<>();

        for (int i = 0; i < addHotelNameView.getChildCount(); i++) {
            View childAt = addHotelNameView.getChildAt(i);
            EditText et_id =childAt.findViewById(R.id.et_clothes_id);
            EditText et_number=childAt.findViewById(R.id.et_clothes_number);

            String id = et_id.getText().toString();
            Integer number = Integer.parseInt(et_number.getText().toString());

            clothes.add(new Clothes(id,number));
            //Log.e(TAG, "ID：" + id + "---数量：" + number);
        }

        ArrayList<Order> orderlist = fileDataStream.load(state);

        String id = (orderlist.size()+1)+"";
        Order order=new ClothesOrder(id ,clothes);
        orderlist.add(order);
        fileDataStream.setList(orderlist);
        fileDataStream.save(state);

        Toast.makeText(this, "添加成功" , Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
