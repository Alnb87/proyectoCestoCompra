<%@page import="MisRutinas.BD" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%
    BD ObjBD = new BD();
    // Devuelve el numero de filas de una tabla
    int filas = ObjBD.NumeroFilas("productos");
    String NuevoCodigo;
    // Si no hay filas genera el primer codigo
    if(filas == 0){
        NuevoCodigo = "VTA0000001";
    }else{
        // Caso contrario genera el correlativo
        NuevoCodigo = "VTA"+String.format("%07d",filas+1);
    }
    out.print("Nuevo Codigo:"+NuevoCodigo);

    
    Date fechaActual = new Date();
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    String cadenaFecha = formato.format(fechaActual);
%>    

