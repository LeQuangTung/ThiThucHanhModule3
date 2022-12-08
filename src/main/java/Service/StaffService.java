package Service;

import DAO.StaffDAO;
import DAO.DepartmentDAO;
import Model.Staff;
import Model.Department;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class StaffService {
    private static ArrayList<Staff> staffs;
    private static ArrayList<Department> departments;

    private StaffDAO staffDAO;
    private DepartmentDAO departmentDAO;

    public StaffService() {
        staffDAO = new StaffDAO();
        departmentDAO = new DepartmentDAO();
    }
    public List<Staff> findAll(HttpServletRequest request) {
        return staffDAO.findAll(request);
    }

    public List<Department> findDepartment(HttpServletRequest request) {
        return departmentDAO.findAll(request);
    }

    public Staff findStaffById(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        return staffDAO.findById(id);
    }

    public boolean save(HttpServletRequest request) {
        String staffId = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Double salary = Double.parseDouble(request.getParameter("salary"));
        Long departmentId = Long.parseLong(request.getParameter("department"));
        if (staffId == null) {
            return staffDAO.createStaff(new Staff(name, email, address, phone,
                    salary, departmentDAO.findDepartmentById(departmentId)));
        } else {
            return staffDAO.updateStaff(new Staff(Long.parseLong(staffId),name, email, address, phone,
                    salary, departmentDAO.findDepartmentById(departmentId)));
        }

    }

    public boolean deleteById(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        return  staffDAO.deleteStaff(id);
    }

    public List<Staff> findByNameContaining(HttpServletRequest request) {
        String name = request.getParameter("search");
        return staffDAO.findAllByNameContaining(name);
    }

}



