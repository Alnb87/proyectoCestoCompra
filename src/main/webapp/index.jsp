<%@page session="true" %>
<%@page import="MisRutinas.Categorias" %>
<%@page import="MisRutinas.BD" %>
<%@page import="java.util.ArrayList" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<%
    BD ObjBD = new BD();
    ArrayList<Categorias> Lista = new ArrayList<Categorias>();
    Lista = ObjBD.ListarCategorias();
    HttpSession MiSesion = request.getSession();
    // Recuperando valores de sesion
    int numero = MiSesion.getAttribute("numarticulos")==null ? 0 : 
                (Integer)(MiSesion.getAttribute("numarticulos")); 
    double total = MiSesion.getAttribute("total")==null ? 0 : 
                (Double)MiSesion.getAttribute("total");    
%>
<center>
<h1>Tienda Virtual de Compras</h1>
<table class="table">
    <tr bgcolor="Yellow">
        <td colspan="2" align="center">
            <b>Usted ha comprado hasta el momento <%=numero%> articulos
            <br>Su total a pagar es S/. <%=total%> SOLES</b>
        </td>
    </tr>
<tr>
<%  int columnas = 0;
    for(Categorias ObjC:Lista){ 
    String imagen = "<img src=imagenes/"+
                    ObjC.getImagen()+" width=150 height=150>";
    String enlace = "<a href=verproductos.jsp?id="+
                    ObjC.getIdCategoria()+">Ver Productos</a>";
    if(columnas % 2 == 0){ out.print("</tr><tr>"); }
%>
    <td align="center">
        <%=ObjC.getDescripcion()%>
        <br><%=imagen%>
        <br><%=enlace%>
    </td>
<% columnas++; } %>
</table>
</center>