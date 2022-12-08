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
import java.util.Date;
import java.util.List;

public class StaffDAO {
    private final Connection connection;
    private final DepartmentDAO departmentDAO;
    private final String SELECT_ALL_STAFF = "select * from nhanvien;";
    private final String SELECT_ALL_STAFF_BY_NAME = "select * from nhanvien where name like ?;";
    private final String INSERT_STAFF = "insert into nhanvien(name, email, address, phone, salary , id_department)" +
            "value (?,?,?,?,?,?);";
    private final String UPDATE_STAFF = "update nhanvien set name = ?, email = ?, address = ?, phone = ?, salary = ?, id_department = ? where id = ?;";
    private final String DELETE_STAFF = "delete from nhanvien where id = ?;";
    private final String FIND_BY_ID = "select * from nhanvien where id = ?;";

    public StaffDAO() {
        departmentDAO = new DepartmentDAO();
        connection = MyConnection.getConnection();
    }

    public List<Staff> findAll(HttpServletRequest request) {
        List<Staff> staffs = new ArrayList<>();
        try (
                PreparedStatement preparedStatement =
                        connection.prepareStatement(SELECT_ALL_STAFF)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                Double salary = resultSet.getDouble("salary");
                Long idDepartment =resultSet.getLong("id_department");
                Department department = departmentDAO.findDepartmentById(idDepartment);
                staffs.add(new Staff(id, name, email, address, phone, salary, department));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffs;
    }

    public List<Staff> findAllByNameContaining(String search) {
        List<Staff> staffList = new ArrayList<>();
        try (
                PreparedStatement preparedStatement =
                        connection.prepareStatement(SELECT_ALL_STAFF_BY_NAME)) {
            preparedStatement.setString(1, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                Double salary = resultSet.getDouble("salary");
                Long idDepartment = resultSet.getLong("id_department");
                Department department = departmentDAO.findDepartmentById(idDepartment);
                staffList.add(new Staff(id, name, email, address, phone, salary, department));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public boolean createStaff(Staff staff) {
        try (
                PreparedStatement preparedStatement =
                        connection.prepareStatement(INSERT_STAFF)) {
            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getEmail());
            preparedStatement.setString(3, staff.getAddress());
            preparedStatement.setString(4, staff.getPhone());
            preparedStatement.setDouble(5, staff.getSalary());
            preparedStatement.setLong(6, staff.getDepartment().getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStaff(Staff staff) {
        try (
                PreparedStatement preparedStatement =
                        connection.prepareStatement(UPDATE_STAFF)) {
            preparedStatement.setString(1, staff.getName());
            preparedStatement.setString(2, staff.getEmail());
            preparedStatement.setString(3, staff.getAddress());
            preparedStatement.setString(4, staff.getPhone());
            preparedStatement.setDouble(5, staff.getSalary());
            preparedStatement.setLong(6, staff.getDepartment().getId());
            preparedStatement.setLong(7,staff.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStaff(Long id) {
        try (PreparedStatement preparedStatement =
                        connection.prepareStatement(DELETE_STAFF)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Staff findById(Long id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                Double salary = resultSet.getDouble("salary");
                Long idDepartment = resultSet.getLong("id_department");
                Department department = departmentDAO.findDepartmentById(idDepartment);
                return new Staff(id, name, email, address, phone, salary, department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
