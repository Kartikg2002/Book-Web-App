<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Add Book</title>
    <link rel="stylesheet" type="text/css" href="indexStyle.css">
</head>
<body>
    <div class="container">
        <h1>${appName}</h1>
        <nav>
            <a href="/">Home</a> 
            <a href="/viewBooks">View Books</a>
        </nav>
        <hr>
        <h3>Add Book</h3>
        <c:if test="${result != null}">
            <div class="notification">${result}</div>
        </c:if>
        <form action="addBook" method="post" enctype="multipart/form-data">
            <label>Book Name:</label>
            <input type="text" name="name" required />
            
            <label>Book Price:</label>
            <input type="number" name="price" required />
            
            <label>Book Author Name:</label>
            <input type="text" name="aname" required />
            
            <label>Book Publisher Name:</label>
            <input type="text" name="pname" required />
            
            <label>Book Photo:</label>
            <input type="file" accept="image/*" name="image" />
            
            <input type="submit" value="Add Book" />
        </form>
    </div>
</body>
</html>
