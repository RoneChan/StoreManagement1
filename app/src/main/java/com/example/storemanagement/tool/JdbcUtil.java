package com.example.storemanagement.tool;


import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//简单工厂模式
public class JdbcUtil {
    private static JdbcUtil instance;
    private  static Connection connection = null;

    public static JdbcUtil getInstance() {
        if (instance == null) {
            instance = new JdbcUtil();
        }
        return instance;
    }

    public static Connection createConnection() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(connection != null )
            return connection;

       // Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connURL = "jdbc:mysql://10.0.2.2:3306/storemanagement?useUnicode=true&characterEncoding=utf8";
            connection = DriverManager.getConnection(connURL, "root", "123");
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return connection;
    }
}

