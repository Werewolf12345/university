<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Students list</title>
</head>
<body>
	<h2 style="text-align: center;">
		<strong>Professors list</strong>
	</h2>

	<table style="margin-left: auto; margin-right: auto;" border=1>
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${professors}" var="current">
				<tr>
					<td style="text-align: left;"><c:out value="${current.firstName}" /></td>
					<td style="text-align: left;"><c:out value="${current.lastName}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>