package sample.database;

import sample.database.database;

import java.sql.*;

public class database_handler extends database {
    Connection dbconnection;

    public Connection getDbconnection() throws ClassNotFoundException, SQLException {

            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            Class.forName("com.mysql.jdbc.Driver");
            dbconnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            return dbconnection;
    }

}
