package DAO;

import Model.Department;
import Model.Staff;
import connection.MyConnection;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private final Connection connection;
    private final String SELECT_ALL_DEPARTMENT = "select * from phongban;";
    private final String SELECT_DEPARTMENT_BY_ID = "select * from phongban where id = ?;";

    public DepartmentDAO() {
        connection = MyConnection.getConnection();
    }

    public List<Department> findAll(HttpServletRequest request) {
        List<Department> departments = new ArrayList<>();
        try (
                PreparedStatement preparedStatement =
                     connection.prepareStatement(SELECT_ALL_DEPARTMENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                departments.add(new Department(id, name));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return departments;
    }

    public Department findDepartmentById(Long id) {
        try (
                PreparedStatement preparedStatement =
                     connection.prepareStatement(SELECT_DEPARTMENT_BY_ID)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idDb = resultSet.getLong("id");
                String name = resultSet.getString("name");
                return new Department(idDb, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
