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
		<strong>Group ${group} students list</strong>
	</h2>

	<table style="margin-left: auto; margin-right: auto;" border=1>
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Zip Code</th>
				<th colspan=3>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${students}" var="current">
				<tr>
					<td style="text-align: left;"><c:out
							value="${current.firstName}" /></td>
					<td style="text-align: left;"><c:out
							value="${current.lastName}" /></td>
					<td style="text-align: left;"><c:out
							value="${current.address.zipCode}" /></td>
					<td><a
						href="index.html?action=updatestudent&firstname=<c:out value="${current.firstName}"/>
						&lastname=<c:out value="${current.lastName}"/>
						&zip=<c:out value="${current.address.zipCode}"/>
						&group=<c:out value="${group}"/>">Update</a></td>
					<td><a
						href="studentslist.html?action=delete&firstname=<c:out value="${current.firstName}"/>
						&lastname=<c:out value="${current.lastName}"/>
						&zip=<c:out value="${current.address.zipCode}"/>
						&group=<c:out value="${group}"/>">Delete</a></td>
					<td><a
						href="index.html?action=schedule&firstname=<c:out value="${current.firstName}"/>
						&lastname=<c:out value="${current.lastName}"/>
						&zip=<c:out value="${current.address.zipCode}"/>">Schedule</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p style="text-align: center;">
		<a
			href="index.html?action=insert&member=student&group=<c:out value="${group}"/>">Add
			Student</a>
	</p>
</body>
</html>