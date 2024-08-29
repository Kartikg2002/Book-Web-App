<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Update Book</title>
    <link rel="stylesheet" type="text/css" href="UpdateBookStyle.css">
</head>
<body>
    <div class="container">
        <h1> ${appName} </h1>
        <hr>
        <nav>
            <a href="/">Home</a> 
            <a href="/viewBooks">View Books</a>
        </nav>
        <hr>
        <h3>Update Book</h3>
        <c:if test="${result != null}">
            <div class="notification"> ${result} </div>
        </c:if>
        <form action="doUpdateBook" method="post" enctype="multipart/form-data">
            <label>Book Name:</label>
            <input type="text" name="name" value="${book.name}" required/>
            <label>Book Price:</label>
            <input type="number" name="price" value="${book.price}" required/>
            <label>Book Author Name:</label>
            <input type="text" name="aname" value="${book.aname}" required/>
            <label>Book Publisher Name:</label>
            <input type="text" name="pname" value="${book.pname}" required/>
            <label>Book Photo:</label>
            <input type="file" accept="image/*" name="image" />
            <img alt="Book Image" src="/getBookImage?name=${book.name}" height="200px" class="book-image">
            <input type="hidden" name="oldName" value="${book.name}" required />
            <input type="submit" value="UPDATE Book"/>
        </form>
    </div>
</body>
</html>
