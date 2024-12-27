<%@page session="true" %>
<%@page import="MisRutinas.*" %>
<%@page import="java.util.ArrayList" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<%
    // Recuperar valores de la sesion actual
    HttpSession MiSesion = request.getSession();
    String IdCliente = (String)MiSesion.getAttribute("IdCliente");
    String FechaVenta = (String)MiSesion.getAttribute("Fecha");
    double MontoTotal = (Double)MiSesion.getAttribute("Total");
    ArrayList<carrito> Lista = new ArrayList<carrito>();
    Lista = (ArrayList<carrito>)MiSesion.getAttribute("cesto");
    
    BD ObjBD = new BD();
    // Devuelve el numero de filas de una tabla
    int filas = ObjBD.NumeroFilas("ventas");
    String IdVenta;
    // Si no hay filas genera el primer codigo
    if(filas == 0){
        IdVenta = "VTA0000001";
    }else{
        // Caso contrario genera el correlativo
        IdVenta = "VTA"+String.format("%07d",filas+1);
    }
    
    // Grabar fila en la tabla ventas
    Ventas ObjV = new Ventas(IdVenta, IdCliente, FechaVenta, MontoTotal, '1');
    ObjBD.InsertarVenta(ObjV);
    
    // Grabar filas en la tabla detalle
    for(int i = 0; i < Lista.size(); i++){
        String IdPro = Lista.get(i).getIdProducto();
        Productos ObjP = new Productos();
        ObjP = ObjBD.InfoProducto(IdPro);
        Detalle ObjD = new Detalle(IdVenta,IdPro,
                Lista.get(i).getCantidad(),ObjP.getPrecioUnidad(),'1');
        ObjBD.InsertarDetalle(ObjD);
        
    }
    //------------------------------------------------
    // Modificacion del Stock en la tabla Productos
    //------------------------------------------------
    
    // Limpiar el cesto de la sesion
    MiSesion.setAttribute("numarticulos", 0);
    MiSesion.setAttribute("total", 0.0);
    MiSesion.removeAttribute("cesto");
%>    
<center>
<h1>Se ha grabado su Compra Ok</h1>
<a href="index.jsp">Hacer Click para retornar a la Pagina Principal</a>
</center>