<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Page</title>
</head>
<body>
<p style="color: red;" >
<%
  out.println("The resouce <h3>"+request.getServletPath()+"</h3> is not existed");
%>

</p>
</body>
</html>