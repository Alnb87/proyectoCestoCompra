<html>
	<head>
		<title>Identificacion</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
	</head>
	<body>
		<center>
			<h1>Iniciar Sesion</h1>
			<form action="verificar.jsp">
				<table border="1">
				    <tr>
				        <td>IdUsuario</td>
				        <td><input name="txtid" /></td>
				    </tr>
				    <tr>
				        <td>Password</td>
				        <td><input name="txtpass" /></td>
				    </tr>
				    <tr>
				        <td><input type="submit" /></td>
				        <td><input type="reset" /></td>
				    </tr>
				    <tr>
				        <td colspan="2" align="center">
				            <a href="registro.jsp">Usuario Nuevo (Registrarse)</a>
				        </td>
				    </tr>
				</table>
			</form>
		</center>
	</body>
</html>
