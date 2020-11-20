package com.ian.week5.homework.jdbc;

import java.sql.*;
import java.text.SimpleDateFormat;

public class JDBCDemo {

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javalearn", "root", "");
            String sql = "INSERT INTO t_student (id, name, create_time, update_time) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <3; i++) {
                preparedStatement.setInt(1, i );
                preparedStatement.setString(2, "student" + i);
                preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
                preparedStatement.setDate(4, new Date(new java.util.Date().getTime()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


}
