package utils;

import commands.RequestData;
import exceptions.NoDataException;
import exceptions.NoSuchStatementException;
import marine.SpaceMarine;
import request.MarineData;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayDeque;

public class DBManager {
    private final Connection connection;
    private final Statement statement;

    public DBManager() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "1234";
        connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement();
    }

    public void add(RequestData requestData) throws SQLException, NoDataException {
        PreparedStatement stm = connection.prepareStatement("insert into marines " +
                "(name, x, y, creation_date, health, heart_count, loyal, weapon_type, chapter_name, parent_legion, owner) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        setDataToStatement(requestData.getMarineData(), stm);
        stm.setString(11, requestData.getLogin());
        stm.executeUpdate();
    }

    public void update(RequestData requestData, Integer id) throws SQLException, NoDataException, NoSuchStatementException {
        PreparedStatement stm = connection.prepareStatement("update marines set " +
                "name = ?," +
                "x = ?," +
                "y = ?," +
                "creation_date = ?," +
                "health = ?," +
                "heart_count = ?," +
                "loyal = ?," +
                "weapon_type = ?," +
                "chapter_name = ?," +
                "parent_login = ? " +
                "where id = ? and owner = ?");
        setDataToStatement(requestData.getMarineData(), stm);
        stm.setInt(11, id);
        stm.setString(12, requestData.getLogin());
        if (stm.executeUpdate() < 1) {
            throw new NoSuchStatementException();
        }
    }

    public void remove(RequestData requestData, Integer id) throws SQLException, NoSuchStatementException {
        PreparedStatement stm = connection.prepareStatement("delete from marines where id = ? and owner = ?");
        stm.setInt(1, id);
        stm.setString(2, requestData.getLogin());
        if (stm.executeUpdate() < 1) {
            throw new NoSuchStatementException();
        }
    }

    public void load(ArrayDeque<SpaceMarine> marines) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from marines");
        while (rs.next()) {
            MarineBuilder marineBuilder = new MarineBuilder();
            marineBuilder.setId(rs.getInt("id"));
            marineBuilder.setName(rs.getString("name"));
            marineBuilder.setX(rs.getDouble("x"));
            marineBuilder.setY(rs.getDouble("y"));
            marineBuilder.setCreationDate(rs.getTimestamp("creation_date"));
            marineBuilder.setHealth(rs.getInt("health"));
            marineBuilder.setHeartCount(rs.getInt("heart_count"));
            marineBuilder.setLoyal(rs.getBoolean("loyal"));
            marineBuilder.setWeaponType(rs.getString("weapon_type"));
            marineBuilder.setChapterName(rs.getString("chapter_name"));
            marineBuilder.setParentLegion(rs.getString("parent_legion"));
            marines.add(marineBuilder.buildMarine());
        }
    }

    public Integer getId() throws SQLException {
        return statement.executeQuery("select currval('id')").getInt(1);
    }

    private void setDataToStatement(MarineData marineData, PreparedStatement stm) throws
            SQLException, NoDataException {
        if (marineData != null) {
            stm.setString(1, marineData.getName());
            stm.setDouble(2, marineData.getX());
            stm.setDouble(3, marineData.getY());
            stm.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stm.setInt(5, marineData.getHealth());
            stm.setInt(6, marineData.getHeartCount());
            stm.setBoolean(7, marineData.getLoyal());
            stm.setString(8, marineData.getWeaponType());
            stm.setString(9, marineData.getChapterName());
            stm.setString(10, marineData.getParentLegion());
        } else throw new NoDataException();
    }
}
