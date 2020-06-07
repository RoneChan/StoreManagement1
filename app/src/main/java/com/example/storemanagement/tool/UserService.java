package com.example.storemanagement.tool;

import com.example.storemanagement.entity.Clothes;
import com.example.storemanagement.entity.Info;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.entity.Product;
import com.example.storemanagement.entity.Store;

import android.util.Pair;
import java.util.ArrayList;
import java.util.Map;

public class UserService {
    UserDao userDao = new UserDao();

    public int getRestNumber(String id){
        return userDao.getRestNumber(id);
    }

    public String getPosition(String Id) {
        return userDao.getPosition(Id);
    }

    public Store getWare() {
        return userDao.getWare();
    }

    public int updateClothes(String oriId,String curId,int cruRestNumber) {
        return userDao.updateClothes(oriId,curId,cruRestNumber);
    }


    public int deleteClothes(String Id) {
        return userDao.deleteClothes(Id);
    }

    public Clothes getClothesByPos(String shelf, String position) {
        return userDao.getClothesByPos(shelf,position);
    }

    public int changeInfo(String oriId,String curId,int cruRestNumber,int oriRestNumber){
        int result=-1;
        if(cruRestNumber==0){  //商品件数修改为0，从数据库中删除该商品信息
            result = userDao.deleteClothes(oriId);
        }else{   //商品属性修改
            result=userDao.updateClothes(oriId,curId,cruRestNumber);
            if(!oriId.equals(curId)) {  //商品id被修改,即原有信息错误，一个从数据库中删除就Id服装的信息
                deleteClothes(oriId);
            }
        }
        return result;
    }


    public Map<String, Info> getPositions(Order order){
        return userDao.getPositions(order);
    }


    public int ProductEntry(ArrayList<Product> list){
        //添加服装信息
        try {
            for (int i = 0; i < list.size(); i++) {
                Product p = list.get(i);
                userDao.insertClothes(p.getId(), p.getNumber());
            }

            ArrayList<Pair<String, String>> free = userDao.getFreePiles();
            //将服装放入货柜中
            for (int i = 0; i < list.size(); i++) {
                Product p = list.get(i);
                userDao.updateShelf(free.get(i).first, free.get(i).second, p.getId());
            }
        }catch (Exception e){
            return -1;
        }
        return 1;
    }

}
