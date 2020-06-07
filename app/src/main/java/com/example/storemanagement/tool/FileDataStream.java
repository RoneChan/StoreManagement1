package com.example.storemanagement.tool;

import android.content.Context;

import com.example.storemanagement.entity.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileDataStream {
    public final static int ORDER_LIST_CODE = 200;
    public final static int ENTRY_LIST_CODE = 201;
    public final static int RETURN_LIST_CODE = 202;

    private Context context;
    private ArrayList<Order> list = new ArrayList<Order>();

    public FileDataStream(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Order> getList() {
        return list;
    }

    public void setList(ArrayList<Order> list) {
        this.list = list;
    }

    //保存数据
    public void save(int state) {
        //不是sdk卡上的，所以不要用FileOutputStream
        //Serializable读取的是手机内存上的
        ObjectOutputStream outputStream = null;
        try {
            //写入文件
            if (state == ORDER_LIST_CODE) {
                outputStream = new ObjectOutputStream(
                        context.openFileOutput("OrderLists.txt", Context.MODE_PRIVATE));
            } else if (state == ENTRY_LIST_CODE) {
                outputStream = new ObjectOutputStream(
                        context.openFileOutput("EntryProductLists.txt", Context.MODE_PRIVATE));
            } else if (state == RETURN_LIST_CODE) {
                outputStream = new ObjectOutputStream(
                        context.openFileOutput("ReturnProductLists.txt", Context.MODE_PRIVATE));
            }
            //context.openFileOutput(file, Context.MODE_PRIVATE)

            outputStream.writeObject(list);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> load(int state) {
        try {
            ObjectInputStream inputStream = null;
            if (state == ORDER_LIST_CODE) {
                inputStream = new ObjectInputStream(context.openFileInput("OrderLists.txt"));
            } else if (state == ENTRY_LIST_CODE) {
                inputStream = new ObjectInputStream(context.openFileInput("EntryProductLists.txt"));
            } else if (state == RETURN_LIST_CODE) {
                inputStream = new ObjectInputStream(context.openFileInput("ReturnProductLists.txt"));
            }

            list = (ArrayList<Order>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
