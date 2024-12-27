package MisRutinas;
import java.sql.*;
import MisRutinas.*;
import java.util.ArrayList;

public class BD {
    // Campos o atributos
    private String Driver = "com.mysql.jdbc.Driver";
    private String URL = "jdbc:mysql://localhost:3306/tienda2023";
    private String Usuario = "root";
    private String Password = "mysql";
    Connection Cn;  // Establecer la Conexion con MySQL
    Statement Cmd;  // Ejecutar un comando SQL en MySQL
    CallableStatement Stm; // Ejecutar Store Procedure
    ResultSet Rs;   // Almacenar el resultado de una consulta
    ArrayList<Categorias> Lista;
    ArrayList<Productos> ListaP;

    // Constructor de la Clase    
    public BD() {
        try {
            Class.forName(this.Driver);
            this.Cn = DriverManager.getConnection(this.URL,
                    this.Usuario, this.Password);
        } catch (Exception e) {
            System.out.println("*** ERROR:"+e.getMessage());
        }
    }
    
    // Métodos
    public ArrayList<Categorias> ListarCategorias()
    { this.Lista = new ArrayList<Categorias>();
        try {
            this.Cmd = this.Cn.createStatement();
            this.Rs = this.Cmd.executeQuery("SELECT * FROM Categorias");
            while(this.Rs.next()){
                Categorias ObjC = new Categorias(
                        this.Rs.getString("IdCategoria"),
                        this.Rs.getString("Descripcion"),
                        this.Rs.getString("Imagen"),
                        this.Rs.getString("Estado").charAt(0)
                        );
                Lista.add(ObjC);
            }
        } catch (Exception e) {
            System.out.println("***ERROR:"+e.getMessage());
        }        
      return this.Lista;  
    }
    
    public ArrayList<Productos> ListarProductos(String IdCat)
    { this.ListaP = new ArrayList<Productos>();
        String SQL = "SELECT * FROM Productos WHERE IdCategoria='"+IdCat+"'";
        try {
            this.Cmd = this.Cn.createStatement();
            this.Rs = this.Cmd.executeQuery(SQL);
            while(this.Rs.next()){
                Productos ObjP = new Productos(
                        this.Rs.getString("IdProducto"),
                        this.Rs.getString("IdCategoria"),
                        this.Rs.getString("Descripcion"),
                        this.Rs.getDouble("PrecioUnidad"),
                        this.Rs.getInt("Stock"),
                        this.Rs.getString("Imagen"),
                        this.Rs.getString("Estado").charAt(0)
                        );
                this.ListaP.add(ObjP);
            }
        } catch (Exception e) {
            System.out.println("***ERROR:"+e.getMessage());
        }
      return this.ListaP;  
    }
    
    // Método para devolver la informacion de un producto
    public Productos InfoProducto(String IdPro)
    { Productos ObjP = null;
      String SQL = "SELECT * FROM productos WHERE IdProducto='"+IdPro+"'";
        try {
            this.Cmd = this.Cn.createStatement();
            this.Rs = this.Cmd.executeQuery(SQL);
            if(this.Rs.next()){
                ObjP = new Productos(
                        this.Rs.getString("IdProducto"),
                        this.Rs.getString("IdCategoria"),
                        this.Rs.getString("Descripcion"),
                        this.Rs.getDouble("PrecioUnidad"),
                        this.Rs.getInt("Stock"),
                        this.Rs.getString("Imagen"),
                        this.Rs.getString("Estado").charAt(0)
                        );
            }
        } catch (Exception e) {
            System.out.println("***ERROR:"+e.getMessage());
        }
      return ObjP;  
    }
    
    // Validar IdUsuario y Password
    public boolean VerificaUsuario(String IdCliente,String Password)
    { boolean estado = false;
        String SQL = "SELECT * FROM Clientes WHERE IdCliente='"+
                    IdCliente+"' AND Password='"+Password+"'";
        try {
            this.Cmd = this.Cn.createStatement();
            this.Rs = this.Cmd.executeQuery(SQL);
            if(this.Rs.next()){
                estado = true;
            }
        } catch (Exception e) {
            System.out.println("***ERROR:"+e.getMessage());
        }
      return estado;
    }
    
    // Devolver Informacion de Cliente
    public Clientes InfoCliente(String IdCliente)
    { Clientes ObjP = null;      
        try {
            // Establece el nombre del SP a invocar
            this.Stm = this.Cn.prepareCall("CALL InfoCliente(?)");
            // Asigna el valor del unico parametro del SP
            this.Stm.setString(1, IdCliente);
            // Ejecuta el SP y almacena los resultados
            this.Rs = this.Stm.executeQuery();
            // Si recupero filas guardalo en un objeto de tipo Clientes
            if(this.Rs.next()){
                ObjP = new Clientes(
                            this.Rs.getString("IdCliente"),
                            this.Rs.getString("Apellidos"),
                            this.Rs.getString("Nombres"),
                            this.Rs.getString("Direccion"),
                            this.Rs.getString("FechaNacimiento"),
                            this.Rs.getString("Sexo").charAt(0),
                            this.Rs.getString("Correo"),
                            this.Rs.getString("Password"),
                            this.Rs.getString("Estado").charAt(0)
                        );
            }
        } catch (Exception e) {
            System.out.println("***ERROR:"+e.getMessage());
        }
      return ObjP;  
    }
    
    // Método para insertar filas en la tabla ventas
    public void InsertarVenta(Ventas ObjV)
    {
        try {
            this.Stm = this.Cn.prepareCall("CALL InsertaVenta(?,?,?,?,?)");
            this.Stm.setString(1, ObjV.getIdVenta());
            this.Stm.setString(2, ObjV.getIdCliente());
            this.Stm.setString(3, ObjV.getFechaVenta());
            this.Stm.setDouble(4, ObjV.getMontoTotal());
            this.Stm.setString(5, ObjV.getEstado()+"");
            this.Stm.executeUpdate(); // INSERT, DELETE o UPDATE
        } catch (Exception e) {
            System.out.println("***ERROR:"+e.getMessage());        
        }
    }
    
    // Método para insertar filas en la tabla detalle
    public void InsertarDetalle(Detalle ObjD)
    {
        try {
            this.Stm = this.Cn.prepareCall("CALL InsertaDetalle(?,?,?,?,?)");
            this.Stm.setString(1, ObjD.getIdVenta());
            this.Stm.setString(2, ObjD.getIdProducto());
            this.Stm.setInt(3, ObjD.getCantidad());
            this.Stm.setDouble(4, ObjD.getPrecioUnidad());
            this.Stm.setString(5, ObjD.getEstado()+"");
            this.Stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("***ERROR:"+e.getMessage());  
        }
    }
    
    // Método para devolver el numero de filas de un tabla
    public int NumeroFilas(String NombreTabla)
    { int filas = 0;
      String SQL = "SELECT Count(*) FROM "+NombreTabla;
        try {
            this.Cmd = this.Cn.createStatement();
            this.Rs = this.Cmd.executeQuery(SQL);
            if(this.Rs.next()){
                filas = Rs.getInt(1);
            }
        } catch (Exception e) {
           System.out.println("***ERROR:"+e.getMessage());   
        }
      return filas;  
    }
}
