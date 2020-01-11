<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<div id="qr">
    <p>
        Scan this Barcode using Google Authenticator app on your phone 
        to use it later in login
    </p>
    <img th:src="${param.qr[0]}"/>
</div>
<a href="/login" class="btn btn-primary">Go to login page</a>
<body>

</body>
</html>