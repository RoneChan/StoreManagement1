package com.example.storemanagement.ui.orderImport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storemanagement.MainActivity;
import com.example.storemanagement.R;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.tool.ExcelUtil;
import com.example.storemanagement.tool.FileDataStream;
import com.example.storemanagement.tool.XmlUtil;
import com.example.storemanagement.ui.getOrder.OrderDetail;

import java.util.ArrayList;

import static com.example.storemanagement.tool.FileDataStream.ORDER_LIST_CODE;
import static com.example.storemanagement.tool.FileDataStream.RETURN_LIST_CODE;

public class OrderImport extends AppCompatActivity {
    ExcelUtil excelUtil;
    XmlUtil xmlUtil;
    ArrayList<Order> orderArrayList = new ArrayList<>();
    FileDataStream fileDataStream = new FileDataStream(this);

    EditText et_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_import);

        et_path = findViewById(R.id.et_path_show);

        findViewById(R.id.btn_import_excel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderArrayList = fileDataStream.load(ORDER_LIST_CODE);
                excelUtil = new ExcelUtil();
                String path = et_path.getText().toString();
                ArrayList<Order> temp = excelUtil.readOrderExcel("/sdcard/StoreManagement/Order/order.xls");

                for (Order r : temp) {
                    orderArrayList.add(r);
                }

                fileDataStream.setList(orderArrayList);
                fileDataStream.save(ORDER_LIST_CODE);
                Toast.makeText(getBaseContext(), "导入成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_import_xml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderArrayList = fileDataStream.load(ORDER_LIST_CODE);
                xmlUtil = new XmlUtil();
                String path = et_path.getText().toString();
                ArrayList<Order> temp = xmlUtil.readOrderXml("/sdcard/StoreManagement/Order/Orders.xml");

                for (Order r : temp) {
                    orderArrayList.add(r);
                }
                fileDataStream.setList(orderArrayList);
                fileDataStream.save(ORDER_LIST_CODE);
                Toast.makeText(getBaseContext(), "导入成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_import_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderArrayList = fileDataStream.load(RETURN_LIST_CODE);
                excelUtil = new ExcelUtil();
                String path = et_path.getText().toString();
                ArrayList<Order> temp = excelUtil.readOrderExcel("/sdcard/StoreManagement/Order/return.xls");

                for (Order r : temp) {
                    orderArrayList.add(r);
                }

                fileDataStream.setList(orderArrayList);
                fileDataStream.save(RETURN_LIST_CODE);
                Toast.makeText(getBaseContext(), "导入成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(OrderImport.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getBaseContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
                    // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                    // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
                    ActivityCompat.requestPermissions(OrderImport.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    Intent imgIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    imgIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    imgIntent.setType("*/xls");
                    startActivityForResult(imgIntent, RETURN_LIST_CODE);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RETURN_LIST_CODE) {
            if (data == null) {
                // 用户未选择任何文件，直接返回
                return;
            }

            Uri uri = data.getData(); // 获取用户选择文件的URI
            String path = uri.getPath();
            et_path.setText(path);
        }
    }
}
