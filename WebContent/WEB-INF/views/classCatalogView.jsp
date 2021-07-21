<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>

<html>
 <head>
    <meta charset="UTF-8">
    <title>Class Catalog</title>
 </head>
 <body>
 
    <jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu2.jsp"></jsp:include>
 
    <h3>Class List</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="7" cellspacing="1" >
       <tr>
          <th>Class ID</th>
          <th>Class Type</th>
          <th>Class Number</th>
          <th>Class Title</th>
          <th>Days</th>
          <th>Start Time</th>
          <th>End Time</th>
       </tr>
       <c:forEach items="${courseList}" var="course" >
          <tr>
             <td>${course.classID}</td>
             <td>${course.classType}</td>
             <td>${course.classNumber}</td>
             <td>${course.classTitle}</td>
             <td>${course.classDays}</td>
             <td>${course.classStartTime}</td>
             <td>${course.classEndTime}</td>

          </tr>
       </c:forEach>
    </table>
  
    <jsp:include page="_footer.jsp"></jsp:include>
 
 </body>
</html>