package com.ian.week5.homework.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.text.SimpleDateFormat;

public class HikariCPDemo {

    public static void main(String[] args) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/javalearn");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("");
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setAutoCommit(true);
        hikariDataSource.setIdleTimeout(60000);
        hikariDataSource.setMaxLifetime(60000);
        hikariDataSource.setConnectionTestQuery("SELECT 1");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = hikariDataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM t_student WHERE id = ?");
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date createTime = resultSet.getDate("create_time");
                System.out.println("id :" + id + ", name :" + name + ", createTime :" + formatDate(createTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                hikariDataSource.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
