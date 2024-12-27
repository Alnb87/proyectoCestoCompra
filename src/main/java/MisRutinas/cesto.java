package MisRutinas;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import MisRutinas.BD;
import MisRutinas.Productos;
import MisRutinas.carrito;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class cesto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM\" crossorigin=\"anonymous\">");
        try {
            // Enlaces del carrito
            String enlace1 = "<a href=index.jsp>Seguir Comprando</a>";
            String enlace2 = "<a href=pagar.jsp>Pagar Compra</a>";
            String enlace3 = "<a href=cancelar.jsp>Cancelar Compra</a>";            
            // Variable de la clase BD
            BD ObjBD = new BD();
            // Recuperando los valores del formulario
            String IdPro = (String)request.getParameter("txtid");
            int Cant =  Integer.parseInt(request.getParameter("txtcant"));
            // Agregar los valores del formulario a un objeto de tipo carrito
            carrito ObjC = new carrito(IdPro, Cant);
            // Variable para acceder a la sesion del proyecto web
            HttpSession MiSesion = request.getSession();
            // Declarar un ArrayList de tipo carrito
            ArrayList<carrito> Lista = null;
            // Recuperando los elementos almacenados en la sesion
            Lista = (ArrayList<carrito>)MiSesion.getAttribute("cesto");
            // Verificar si logro recuperar valores de la sesion
            if(Lista == null){
                Lista = new ArrayList<carrito>();
                Lista.add(ObjC);
            }else{
                // Si ya existen elementos en la lista
                boolean encontrado = false;
                for(int i = 0; i < Lista.size();i++){
                    carrito Obj = new carrito();
                    Obj = Lista.get(i);
                    if(Obj.getIdProducto().equalsIgnoreCase(IdPro)){
                        encontrado = true;
                        Obj.setCantidad(Obj.getCantidad()+Cant);
                        Lista.set(i, Obj);
                        break;
                    }
                }    
                // Si no encontro el producto añadirlo al cesto
                if(!encontrado && Cant!=0){
                    Lista.add(ObjC);
                }
            }
            // Actualizar el valor de la sesion
            if(Cant!=0)MiSesion.setAttribute("cesto", Lista);
            // Construir la factura
            String tabla = "<table class='table'>";
                tabla += "<tr bgcolor=Yellow>";
                    tabla += "<th>Item</th>";
                    tabla += "<th>IdProducto</th>";
                    tabla += "<th>Descripcion</th>";
                    tabla += "<th>Imagen</th>";
                    tabla += "<th>Precio</th>";
                    tabla += "<th>Cantidad</th>";
                    tabla += "<th>Sub-Total</th>";
                    tabla += "<th>Opciones</th>";
                tabla += "</tr>";
                double Total = 0; int i;
                // Recorrer todos los productos de Lista
                for(i = 0; i < Lista.size(); i++){
                    Productos Obj = new Productos();
                    // Recuperar la informacion de cada producto del cesto
                    Obj = ObjBD.InfoProducto(Lista.get(i).getIdProducto());
                    String enlace = "suprimir.jsp?id="+Obj.getIdProducto();
                    double Precio = Obj.getPrecioUnidad();
                    int Cantidad = Lista.get(i).getCantidad();
                    double SubTotal = Precio * Cantidad;
                    Total += SubTotal;
                    tabla += "<tr>";
                        tabla += "<td>"+(i+1)+"</td>";
                        tabla += "<td>"+Obj.getIdProducto()+"</td>";
                        tabla += "<td>"+Obj.getDescripcion()+"</td>";
                        tabla += "<td><img src=imagenes/"+Obj.getImagen()+
                                " width=50 heigth=50></td>";
                        tabla += "<td>"+Precio+"</td>";
                        tabla += "<td>"+Cantidad+"</td>";
                        tabla += "<td>"+SubTotal+"</td>";
                        tabla += "<td><a href="+enlace+">Suprimir</a></td>";
                    tabla +="</tr>";
                }
                tabla += "<tr bgcolor=Yellow><th colspan=6>TOTAL GENERAL</th><th>"+Total+"</th><th></th></tr>";
                tabla += "<tr><td colspan=8 align=center>[ "+enlace1+" ][ "+enlace2+" ][ "+enlace3+" ]</td></tr>";
            tabla += "</table>";
            out.print(tabla);
            // Guardar valores en sesion
            MiSesion.setAttribute("numarticulos", i);
            MiSesion.setAttribute("total", Total);
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
