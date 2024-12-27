<%@page import="MisRutinas.Productos" %>
<%@page import="MisRutinas.BD" %>
<%@page import="java.util.ArrayList" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<%
    // Recuperar el IdCategoria
    String IdCat = request.getParameter("id");
    BD ObjBD = new BD();
    ArrayList<Productos> Lista = new ArrayList<Productos>();
    Lista = ObjBD.ListarProductos(IdCat);
%>
<center>
    <h1>Tienda Virtual de Compras</h1>
    <table class="table">
        <tr>
        <% int columnas = 0;
            for(Productos ObjP:Lista){ 
            String imagen="<img src=imagenes/"+ObjP.getImagen()+">";
            String enlace="<a href=verdetalle.jsp?id="+
                    ObjP.getIdProducto()+">Ver Detalle</a>";
            if(columnas % 2 == 0){ out.print("</tr><tr>"); }
        %>
            <td align="center"><%=ObjP.getDescripcion()%>
                <br><%=imagen%>
                <br><%=enlace%>
            </td>
        <% columnas++; } %>
    </table>
    <a href="index.jsp">Seleccionar otra categoria</a>
</center>
