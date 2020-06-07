package com.example.storemanagement.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.storemanagement.R;
import com.example.storemanagement.tool.UserDao;

import java.sql.Connection;

public class SearchFragment extends Fragment {

    private String Id;
    private int restNumber = -1;
    private String position="";
    EditText et_Id;
    Button btn_confirm;

    ImageView imageView;
    TextView tv_result_text,tv_rest_text,tv_position_text;
    ImageView background;
    static Connection conn = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        et_Id = view.findViewById(R.id.et_search_input_id);
        btn_confirm = view.findViewById(R.id.btn_search_id);

        tv_rest_text=view.findViewById(R.id.tv_rest_text);
        tv_result_text=view.findViewById(R.id.tv_result_text);
        background=view.findViewById(R.id.iv_background);
        tv_position_text=view.findViewById(R.id.tv_position_text);

        //改变输入的ID
        et_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //将结果不可视
                tv_result_text.setVisibility(View.INVISIBLE);
                tv_rest_text.setVisibility(View.INVISIBLE);
                background.setVisibility(View.INVISIBLE);
                tv_position_text.setVisibility(View.INVISIBLE);
            }
        });

        //确认按钮，查询结果
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = et_Id.getText().toString();
                if(Id.isEmpty()){
                    Toast.makeText(view.getContext(), "请输入服装ID号", Toast.LENGTH_SHORT).show();
                }else {
                    UserDao userDao = new UserDao();
                    restNumber = userDao.getRestNumber(Id);  //获取查询信息
                    position = userDao.getPosition(Id);
                    if (restNumber == -1) {
                        Toast.makeText(view.getContext(), "服装ID错误", Toast.LENGTH_SHORT).show();
                    } else {
                        //显示结果
                        tv_result_text.setVisibility(View.VISIBLE);
                        tv_rest_text.setText("剩余数量:"+restNumber);
                        tv_rest_text.setVisibility(View.VISIBLE);
                        tv_position_text.setText("位置:"+position);
                        tv_position_text.setVisibility(View.VISIBLE);
                        background.setVisibility(View.VISIBLE);
                        // Toast.makeText(view.getContext(), "找到" + restNumber, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;
    }

}