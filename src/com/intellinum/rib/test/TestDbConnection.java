package com.intellinum.rib.test;

import com.intellinum.rib.db.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Otniel on 4/17/2015.
 */
public class TestDbConnection {
    public static void main(String[] args) throws SQLException {
        ConnectionPool pool     = new ConnectionPool();
        Connection connection   = pool.getConnection();
        ResultSet resultSet;

        try{
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM test");

            while(resultSet.next()){
                System.out.println("Value :"+resultSet.getInt("num"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
