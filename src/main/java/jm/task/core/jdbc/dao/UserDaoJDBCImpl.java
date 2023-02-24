package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = Util.getPostgresqlConnection().createStatement()) {
            statement.execute("create table if not exists USERS (" +
                    "id SERIAL NOT NULL PRIMARY KEY," +
                    "Name varchar(50) not null," +
                    "LastName varchar(50)," +
                    "Age smallint)");
            /*System.out.println("Таблица пользователей создана");*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = Util.getPostgresqlConnection().createStatement()) {
            statement.execute("drop table if exists USERS");
            /*System.out.println("Таблица пользователей удалена");*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into USERS (Name, LastName, Age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = Util.getPostgresqlConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в таблицу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "delete from USERS where ID = ?";
        try (PreparedStatement statement = Util.getPostgresqlConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            /*System.out.println("Пользователь удален");*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = Util.getPostgresqlConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from USERS");
            while (resultSet.next()) {
                User nUser = new User();
                nUser.setId(resultSet.getLong("ID"));
                nUser.setName(resultSet.getString("Name"));
                nUser.setLastName(resultSet.getString("LastName"));
                nUser.setAge(resultSet.getByte("Age"));
                list.add(nUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = Util.getPostgresqlConnection().createStatement()) {
            statement.execute("truncate table USERS");
            /*System.out.println("Таблица пользователей очищена");*/
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить таблицу пользователей");
        }
    }

}