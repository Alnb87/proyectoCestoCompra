<%@page import="MisRutinas.Productos" %>
<%@page import="MisRutinas.BD" %>
<%@page import="java.util.ArrayList" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<%
    // Recuperar el codigo del producto
    String IdPro = request.getParameter("id");
    // Proceso de Consulta
    BD ObjBD = new BD();
    Productos ObjP = new Productos();
    ObjP = ObjBD.InfoProducto(IdPro);
%>
<center>
    <h1>Detalle de Producto</h1>
    <form action="cesto">
    <table class="table">
        <tr>
            <td>IdProducto</td>
            <td><input name="txtid" value=<%=ObjP.getIdProducto()%> readonly></td>
        </tr>
        <tr>
            <td>Descripcion</td>
            <td><%=ObjP.getDescripcion()%></td>
        </tr>
        <tr>
            <td>Precio Unidad</td>
            <td><%=ObjP.getPrecioUnidad()%></td>            
        </tr>
        <tr>
            <td>Imagen</td>
            <td><img src=imagenes/<%=ObjP.getImagen()%> width="200" height="200"></td>
        </tr>
        <tr>
            <td>Cantidad a Comprar</td>
            <td><input name="txtcant" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Agregar al Cesto"/></td>
            <td><input type="reset" /></td>
        </tr>
    </table>
    </form>
    <a href="javascript:history.back()">Seleccionar Otro Producto</a>
</center>