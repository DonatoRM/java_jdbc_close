package es.donatodev.java.jdbc.repositorio;

import static es.donatodev.java.jdbc.repositorio.SqlQueries.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.donatodev.java.jdbc.modelo.Categoria;
import es.donatodev.java.jdbc.modelo.Producto;
import es.donatodev.java.jdbc.util.ConexionBaseDatos;

import static es.donatodev.java.jdbc.repositorio.Messages.*;

public class ProductoRepositorioImpl implements Repositorio<Producto> {
    private static final Logger LOGGER=Logger.getLogger(ProductoRepositorioImpl.class.getName());
    // Constantes SQL movidas a SqlQueries.java
    // Mensajes movidos a Messages.java

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
    try (Connection conn=getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SQL_LISTAR)) {

            while (rs.next()) {
                Producto producto = crearProducto(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,MSG_LIST_WRONG);
            e.printStackTrace();
        }
        LOGGER.log(Level.INFO,MSG_LIST_OK);
        return productos;
    }

    
    @Override
    public Producto porId(Long id) {
        Producto producto=null;
    try(Connection conn=getConnection();
        PreparedStatement stmt=conn.prepareStatement(SQL_POR_ID)) {
        stmt.setLong(1,id);
            try(ResultSet rs=stmt.executeQuery()){
                if(rs.next()) {
                    producto=crearProducto(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,MSG_FOR_ID_WRONG);
            e.printStackTrace();
        }
        return producto;
    }
    
    @Override
    public void guardar(Producto producto) {
        String sql;
        if(producto.getId()!=null && producto.getId()>0) {
            sql=SQL_ACTUALIZAR;
        } else {
            sql=SQL_GUARDAR;
        }
        try(Connection conn=getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql)) {
            stmt.setString(1,producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3,producto.getCategoria().getId());

            if(producto.getId()!=null && producto.getId()>0) {
                stmt.setLong(4, producto.getId());
            } else {
                stmt.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            if(sql.equals(SQL_GUARDAR)) {
                LOGGER.log(Level.SEVERE,MSG_INSERT_FOR_ID_WRONG);
            } else {
                LOGGER.log(Level.SEVERE,MSG_UPDATE_FOR_ID_WRONG);
            }
            e.printStackTrace();
        }
    }
    
    @Override
    public void eliminar(Long id) {
    try(Connection conn=getConnection();
        PreparedStatement stmt=conn.prepareStatement(SQL_ELIMINAR)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            LOGGER.log(Level.SEVERE,MSG_DELETE_FOR_ID_OK);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,MSG_DELETE_FOR_ID_WRONG);
            e.printStackTrace();
        }
    }

    private Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        Categoria categoria=new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }

}
