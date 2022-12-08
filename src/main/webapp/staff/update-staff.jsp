<%--
  Created by IntelliJ IDEA.
  User: IDEAPAD GAMING 3
  Date: 12/8/2022
  Time: 10:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Title</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
          crossorigin="anonymous"></script>
  <style>
    div {
      margin: auto;
      width: 500px;
    }
  </style>
</head>
<body>
<div>
  <h1>Update form</h1>
  <form action="/staffs?action=update" method="post">
    <input name="id" value="${staff.getId()}" hidden>
    <div class="mb-3 mt-3">
      <label for="name" class="form-label">Name</label>
      <input type="text" class="form-control" id="name" value="${staff.getName()}" placeholder="Enter name" name="name">
    </div>
    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="text" class="form-control" id="email" value="${staff.getEmail()}" placeholder="Enter email" name="email">
    </div>
    <div class="mb-3">
      <label for="address" class="form-label">Address</label>
      <input type="text" class="form-control" id="address" value="${staff.getAddress()}" placeholder="Enter address" name="address">
    </div>
    <div class="mb-3">
      <label for="phone" class="form-label">Phone</label>
      <input type="text" class="form-control" id="phone" value="${staff.getPhone()}" placeholder="Enter phone" name="phone">
    </div>
    <div class="mb-3">
      <label for="salary" class="form-label">Salary</label>
      <input type="text" class="form-control" id="salary" value="${staff.getSalary()}"placeholder="Enter salary" name="salary">
    </div>
    <div class="mb-3">
      <select name="department">
        <c:forEach items="${department}" var="p">
          <option selected hidden value="${staff.getDepartment().getId()}"><c:out value="${staff.getDepartment().getName()}"/></option>
          <option value="${p.getId()}">
            <c:out value="${p.getName()}"/>
          </option>
        </c:forEach>
      </select>
    </div>
    <button type="submit" class="btn btn-primary">Update</button>
    <a href="/staffs">
      <button type="button" class="btn btn-secondary">Cancel</button>
    </a>
  </form>
</div>
</body>
</html>