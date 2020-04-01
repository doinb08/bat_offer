package com.doinb.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

public class MyDataSource implements DataSource {

    // 因为 LinkedList 是用链表实现的,对于增删实现起来比较容易
    LinkedList<Connection> dataSources = new LinkedList<Connection>();

    // 初始化连接数量
    public MyDataSource() {
        // 问题：每次new MyDataSource 都会建立 10 个链接，可使用单例设计模式解决此类问题
        for(int i = 0; i < 10; i++) {
            try {
                // 1、装载 mysql 驱动对象
                Class.forName("com.mysql.jdbc.Driver");
                // 2、通过 JDBC 建立数据库连接
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sell?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false", "mysqldb", "123456");
                // 3、将连接加入连接池中
                dataSources.add(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        // 取出连接池中一个连接
        final Connection conn = dataSources.removeFirst(); // 删除第一个连接返回
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    // 将连接放回连接池
    public void releaseConnection(Connection conn) {
        dataSources.add(conn);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
