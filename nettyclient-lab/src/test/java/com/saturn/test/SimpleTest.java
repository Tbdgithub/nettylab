package com.saturn.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by john.y on 2017-6-29.
 */
public class SimpleTest {

    @Test
    public void test1() {

        Connection conn = null;
        String driver = "org.apache.phoenix.jdbc.PhoenixDriver";
        String url = "jdbc:phoenix:10.10.220.117:2181";


        try {
            Class.forName(driver);


            if (conn == null) {
                try {
                    conn = DriverManager.getConnection(url);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            Statement statement = conn.createStatement();
            //String sql = "select * from \"cui_log_2\" limit 3";
            String sql = "select * from \"audit:audit-log\" order by \"happenTime\" desc limit 10";

            long time = System.currentTimeMillis();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String happenTime = rs.getString("happenTime");
                String arg=rs.getString("arg");
                System.out.println("happenTime:" + happenTime);
                System.out.println("arg:" + arg);
            }
            long timeUsed = System.currentTimeMillis() - time;
            System.out.println("time " + timeUsed + "mm");
            // 关闭连接
            rs.close();
            statement.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Throwable ex2) {
            ex2.printStackTrace();
        }
    }
}
