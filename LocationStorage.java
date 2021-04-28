package com.grafando.home.data;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Properties;

public class LocationStorage {

    Connection conn = null;
    PreparedStatement ps = null;

    String username;
    String password;

    String serverName;
    String dbms;
    String SqlInsertQuery = "INSERT INTO Homes(Username, World, Axis_X, Axis_Y, Axis_Z) VALUES(?,?,?,?,?);";
    String SqlUpdateQuery = "UPDATE Homes SET World = ?, Axis_x = ?, Axis_Y = ?. Axis_Z = ? WHERE Username = ?";
    String SqlSelectQuery = "SELECT World, Axis_X, Axis_Y, Axis_Z FROM Homes WHERE Username = ?";
    String SqlCheckStatus = "SELECT NumberOfHomes FROM Homes WHERE Username = ?";
    String SqlSelectSpecificPlayerHome = "SELECT World, Axis_X, Axis_Y, Axis_Z FROM Homes WHERE Username = ? AND Homename = ?";
    ResultSet rs;
    int rowsAffected;


    public LocationStorage() {
        password = "Nbiwe!12";
        username = "zGrafando";
        serverName = "jdbc:mysql://localhost:3306/MinecraftServer";
        dbms = "mysql";
    }


    public Connection getConnection() throws SQLException {
        try {
            Properties connectionParms = new Properties();
            connectionParms.put("user", this.username);
            connectionParms.put("password", this.password);
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (this.dbms.equals("mysql")) {
                conn = DriverManager.getConnection(serverName, connectionParms);
            }
            System.out.println("Connected to database");
            conn.setAutoCommit(false);
            return conn;
        }catch(SQLException e){
            e.printStackTrace();
            return conn;
        }
    }

    public void storeLocation(Player player, String homeName){
        try{
            conn = getConnection();
            ps= conn.prepareStatement("select Count(*) FROM Home WHERE Username = ?");
            ps.setString(1, player.getName());
            rs = ps.executeQuery();

            if(rs.next()){
                ps = conn.prepareStatement(SqlInsertQuery);
                ps.setString(1, player.getName());
                ps.setString(2, String.valueOf(player.getLocation().getWorld()));
                ps.setInt(3, (int) player.getLocation().getX());
                ps.setInt(4, (int) player.getLocation().getY());
                ps.setInt(5, (int) player.getLocation().getZ());
                rowsAffected = ps.executeUpdate();
            }
            else{
                ps = conn.prepareStatement(SqlUpdateQuery);
                ps.setString(1, String.valueOf(player.getLocation().getWorld()));
                ps.setInt(2, (int) player.getLocation().getX());
                ps.setInt(3, (int) player.getLocation().getY());
                ps.setInt(4, (int) player.getLocation().getZ());
                ps.setString(5, player.getName());
                rowsAffected = ps.executeUpdate();
            }
            if(rowsAffected > 0){
                conn.commit();
            }else{
                conn.rollback();
            }
            conn.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet retrieveLocation(Player player){
        try{
            conn = getConnection();
            ps = conn.prepareStatement(SqlSelectQuery);
            ps.setString(1, player.getName());
            rs = ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public boolean checkMultipleHomePermission(Player player){
        Boolean multipleHomeStatus = false;
        try{
            conn = getConnection();
            ps = conn.prepareStatement(SqlCheckStatus);
            ps.setString(1, player.getName());
            rs=ps.executeQuery();
            if (rs.next()){
                int Result = rs.getInt("NumberOfHomes");
                if (Result > 1){
                    multipleHomeStatus = true;
                }else{
                    multipleHomeStatus = false;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return multipleHomeStatus;
    }

    public boolean checkIfHomeSet(Player player) throws SQLException{
        Boolean homeIsSet = false;
        conn = getConnection();
        try{
            ps = conn.prepareStatement(SqlSelectQuery);
            ps.setString(1, player.getName());
            rs=ps.executeQuery();
            if (rs.next()){
                homeIsSet = true;
            }else{
                homeIsSet = false;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return homeIsSet;
    }

    public ResultSet findSpecificPlayerHome(String Homename, Player player){
        try{
            conn = getConnection();
            ps = conn.prepareStatement(SqlSelectSpecificPlayerHome);
            ps.setString(1, player.getName());
            ps.setString(2, Homename);
            rs = ps.executeQuery();
            if (!rs.next()){
                rs = retrieveLocation(player);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

}
