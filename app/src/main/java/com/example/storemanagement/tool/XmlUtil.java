package com.example.storemanagement.tool;

import com.example.storemanagement.entity.Clothes;
import com.example.storemanagement.entity.ClothesOrder;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.entity.Product;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlUtil {
    public ArrayList<Order> readOrderXml(String path){
        ArrayList<Order> orderList=new ArrayList<>();
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            doc=builder.parse(new File(path));

            //获取所有Order节点的集合
            Element root =doc.getDocumentElement();

            NodeList nl = root.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (node instanceof Element) {
                    Element ele = (Element) node;
                    String id=ele.getAttribute("id");
                    ArrayList<Product> productArrayList=new ArrayList<>();
                    NodeList productList = ele.getElementsByTagName("Product");
                    for(int j=0;j<productList.getLength();j++){
                        Node node1 = productList.item(j);
                        Element ele2 = (Element) node1;
                        String pid=ele2.getAttribute("id");
                        String number = ele2.getAttribute("needNumber");
                        productArrayList.add(new Clothes(pid,Integer.parseInt(number)));
                    }
                    orderList.add(new ClothesOrder(id,productArrayList));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return orderList;
    }


}
