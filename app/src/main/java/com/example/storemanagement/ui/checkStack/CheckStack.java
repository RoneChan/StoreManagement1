package com.example.storemanagement.ui.checkStack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.storemanagement.Control.CheckStackAdapter;
import com.example.storemanagement.R;
import com.example.storemanagement.tool.ExcelUtil;

import java.io.File;

import static com.example.storemanagement.ui.checkStore.CheckStoreFragment.inventoryArrayList;

public class CheckStack extends AppCompatActivity {
    private CheckStackAdapter adapter;
    ListView listView;
    ImageView img_export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stack);

        if (inventoryArrayList.size() == 0 || inventoryArrayList == null) {
            findViewById(R.id.tv_no_update).setVisibility(ListView.VISIBLE);
        } else {
            findViewById(R.id.tv_no_update).setVisibility(ListView.INVISIBLE);

            adapter = new CheckStackAdapter(this, R.layout.check_out_item, inventoryArrayList);
            listView = findViewById(R.id.lv_check_stack);
            listView.setAdapter(adapter);
        }

        img_export = findViewById(R.id.img_export);
        img_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(CheckStack.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(CheckStack.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
                    // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                    // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
                    ActivityCompat.requestPermissions(CheckStack.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    String filePath = "/sdcard/StoreManagement/export";
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    String excelFileName = "/demo.xls";
                    String[] title = {"服装ID", "盘盈/盘亏"};
                    String sheetName = "demoSheetName";
                    filePath = filePath + excelFileName;

                    ExcelUtil.initExcel(filePath , title);
                    ExcelUtil.writeObjListToExcel(inventoryArrayList, filePath, CheckStack.this);

                    findViewById(R.id.tv_no_update).setVisibility(ListView.INVISIBLE);
                    Toast.makeText(CheckStack.this, "excel已导出至：" + filePath, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
