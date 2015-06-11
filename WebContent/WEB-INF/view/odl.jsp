<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@include file="../../head.jsp"%>


	<div>
		<table class="table">
			<thead>
				<tr>
					<th>topology-id</th>
					<th>node-id</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach var="node" items="${nodeList }">
					<tr>
						<th><c:out value="${topology_id}"></c:out></th>
						<td>${node}</td>
					</tr>
				</c:forEach>


			</tbody>
		</table>
	</div>
</body>
</html>