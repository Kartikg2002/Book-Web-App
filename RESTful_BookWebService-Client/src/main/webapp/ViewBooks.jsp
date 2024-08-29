<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>View Books</title>
    <link rel="stylesheet" type="text/css" href="ViewBooksStyle.css">
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
        <form action="searchBook" method="get" class="search-form">
            <label>Book Name:</label>
            <input type="search" name="name" required />
            <button type="submit">Search</button>
        </form>
        <hr>
        <h3>Books</h3>
        <c:if test="${result!=null}">
            <div class="notification"> ${result} </div>
        </c:if>
        <div class="book-list">
            <c:forEach items="${books}" var="b">
                <div class="book-item">
                    <div class="book-details">
                        <p>Name: <b><c:out value="${b.name}"/></b></p>
                        <p>Price: <b><c:out value="${b.price}"/></b></p>
                        <p>Author Name: <b><c:out value="${b.aname}"/></b></p>
                        <p>Publisher Name: <b><c:out value="${b.pname}"/></b></p>
                        <form action="deleteBook" method="post">
                            <input type="hidden" name="name" value="${b.name}" required />
                            <button type="submit" class="btn-delete">Delete Book</button>
                        </form>
                        <a href="/updateBook?name=${b.name}" class="btn-update">Update Book</a>
                    </div>
                    <img alt="Book Image" src="/getBookImage?name=${b.name}" class="book-image">
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
