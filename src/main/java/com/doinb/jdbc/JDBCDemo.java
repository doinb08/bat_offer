package com.doinb.jdbc;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class JDBCDemo {
    public static void main(String[] args) throws Exception{
        JDBCDemo jdbcDemo = new JDBCDemo();

        for (int i = 0; i < 15; i++) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(()->{
                try {
                    // jdbcDemo.findAllUsersByDataSource();
                    jdbcDemo.findAllUsers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "第"+ i + "个线程").start();

            // jdbcDemo.findAllUsers();
            // jdbcDemo.findAllUsersByDataSource();

        }

    }


    /**
     * 早期进行数据库操作
     *   1、原理：一般来说，java 应用程序访问数据库的过程是：
     *   ①、加载数据库驱动程序；
     *   ②、通过 jdbc 建立数据库连接；
     *   ③、访问数据库，执行 sql 语句；
     *   ④、断开数据库连接。
     *
     * 这样存在很多问题：
     *   首先，每一次 web 请求都要建立一次数据库连接。建立连接是一个费时的活动，每次都得花费 0.05s～1s 的时间，而且系统还要分配内存资源。
     * 这个时间对于一次或几次数据库操作，或许感觉不出系统有多大的开销。可是对于现在的 web 应用，尤其是大型电子商务网站，
     * 同时有几百人甚至几千人在线是很正常的事。在这种情况下，频繁的进行数据库连接操作势必占用很多的系统资源，网站的响应速度必定下降，
     * 严重的甚至会造成服务器的崩溃。
     *
     */
    public void findAllUsers() throws SQLException, ClassNotFoundException {

        // 1、装载 mysql 驱动对象
        Class.forName("com.mysql.jdbc.Driver");
        // 2、通过 JDBC 建立数据库连接
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false", "mysqldb", "123456");
        // 3、创建状态
        Statement state = con.createStatement();
        // 4、查询数据库并返回结果
        ResultSet result = state.executeQuery("select * from order_detail");

        // 5、输出查询结果
        while(result.next()) {
            System.out.println("使用原生JDBC查询："+result.getString("order_id"));
        }
        // 6、断开数据库连接
        result.close();
        state.close();
        con.close();
    }


    /**
     * 使用自定义的连接池进行查询
     * 连接池的主要优点
     *   1）、减少连接的创建时间。连接池中的连接是已准备好的，可以重复使用的，获取后可以直接访问数据库，因此减少了连接创建的次数和时间。
     *   2)、更快的系统响应速度。数据库连接池在初始化过程中，往往已经创建了若干数据库连接置于池中备用。此时连接的初始化工作均已完成。
     *       对于业务请求处理而言，直接利用现有可用连接，避免了数据库连接初始化和释放过程的时间开销，从而缩减了系统整体响应时间。
     *   3）、统一的连接管理。如果不使用连接池，每次访问数据库都需要创建一个连接，这样系统的稳定性受系统的连接需求影响很大，
     *      很容易产生资源浪费和高负载异常。连接池能够使性能最大化，将资源利用控制在一定的水平之下。连接池能控制池中的链接数量，
     *      增强了系统在大量用户应用时的稳定性。
     */
    public void findAllUsersByDataSource() throws SQLException {
        System.out.println(Thread.currentThread().getName()+"\t 进行调用");
        // 1、使用连接池建立数据库连接
        MyDataSource dataSource = new MyDataSource();
        Connection conn = dataSource.getConnection();
        // 2、创建状态
        Statement state = conn.createStatement();
        // 3、查询数据库并返回结果
        ResultSet result = state.executeQuery("select * from order_detail");

        // 4、输出查询结果
        while(result.next()){
            System.out.println("使用连接池查询："+result.getString("order_id"));
        }
        // 5、断开数据库连接
        result.close();
        state.close();

        // 6、归还数据库连接给连接池
        dataSource.releaseConnection(conn);
    }
}
