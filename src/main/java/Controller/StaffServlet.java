package Controller;

import DAO.StaffDAO;
import Model.Department;
import Model.Staff;
import Service.StaffService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StaffServlet", value = "/staffs")
public class StaffServlet extends HttpServlet {
    private StaffService staffService;


    @Override
    public void init() {
        staffService = new StaffService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action){
            case "createForm":
                createForm(request, response);
                break;
            case "updateForm":
                updateForm(request, response);
                break;
            case "delete":
                delete(request,response);
                break;
            default:
                displayListStaff(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action){
            case "create":
                create(request, response);
                break;
            case "update":
                update(request, response);
                break;
            case "search":
                displaySearchStaffList(request,response);
                break;
        }
    }

    private void displayListStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("staff/list_staff.jsp");
        request.setAttribute("staffs", staffService.findAll(request));
        requestDispatcher.forward(request, response);
    }

    private void displaySearchStaffList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("search");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("staff/list_staff.jsp");
        request.setAttribute("staffs", staffService.findByNameContaining(request));
        requestDispatcher.forward(request, response);
    }

    private void createForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = staffService.findDepartment(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("staff/create-staff.jsp");
        request.setAttribute("departments", departments);
        requestDispatcher.forward(request, response);
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (staffService.save(request)) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Create successfully!");
        }
        response.sendRedirect("http://localhost:8080/staffs");
    }

    private void updateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Staff staff = staffService.findStaffById(request);
        List<Department> department = staffService.findDepartment(request);
        request.setAttribute("staff", staff);
        request.setAttribute("department", department);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("staff/update-staff.jsp");
        requestDispatcher.forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (staffService.save(request)) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Update successfully!");
        }
        response.sendRedirect("http://localhost:8080/staffs");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        staffService.deleteById(request);
        response.sendRedirect("http://localhost:8080/staffs");
    }
}
