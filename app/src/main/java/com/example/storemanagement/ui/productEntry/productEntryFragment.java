package com.example.storemanagement.ui.productEntry;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.storemanagement.MainActivity;
import com.example.storemanagement.R;
import com.example.storemanagement.entity.Clothes;
import com.example.storemanagement.entity.ClothesFactory;
import com.example.storemanagement.entity.Factory;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.entity.Product;
import com.example.storemanagement.tool.ExcelUtil;
import com.example.storemanagement.tool.FileDataStream;
import com.example.storemanagement.tool.UserDao;
import com.example.storemanagement.tool.UserService;

import java.util.ArrayList;

import static com.example.storemanagement.tool.FileDataStream.ENTRY_LIST_CODE;

public class productEntryFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout entryClothesView;
    FileDataStream fileDataStream;
    ArrayList<Product> list = new ArrayList<>();
    ArrayList<Product> p=new ArrayList<>();

    private ArrayList<Order> orderlist = new ArrayList<Order>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_entry, container, false);

        fileDataStream = new FileDataStream(getContext());
        entryClothesView = (LinearLayout) view.findViewById(R.id.ll_entry_list);


        //获取权限
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};    //请求状态码
        int REQUEST_PERMISSION_CODE = 2;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }


        //读取入库商品的excel
        String filePath = "/sdcard/StoreManagement/import/new_product.xls";
        list = ExcelUtil.readObjListFromExcel(filePath, getContext());

        //显示入库商品信息
        addViewItem(list);

        view.findViewById(R.id.btn_confirm_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //确定入库，更新数据库
                EntryProduct(list);
            }
        });

        return view;
    }

    //显示入库商品信息
    private void addViewItem(ArrayList<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            View entryEvaluateView = View.inflate(getContext(), R.layout.entry_product_item, null);
            TextView tv_id = entryEvaluateView.findViewById(R.id.tv_entry_id);
            TextView tv_number = entryEvaluateView.findViewById(R.id.tv_entry_number);
            tv_id.setText(list.get(i).getId());
            tv_number.setText(list.get(i).getNumber() + "");

            entryClothesView.addView(entryEvaluateView);
        }
    }


    public void EntryProduct(ArrayList<Product> list) {
        UserService userService = new UserService();
        userService.ProductEntry(list);
    }
}