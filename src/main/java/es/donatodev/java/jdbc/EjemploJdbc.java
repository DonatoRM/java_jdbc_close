package es.donatodev.java.jdbc;

import java.util.Date;

import es.donatodev.java.jdbc.modelo.Categoria;
import es.donatodev.java.jdbc.modelo.Producto;
import es.donatodev.java.jdbc.repositorio.ProductoRepositorioImpl;
import es.donatodev.java.jdbc.repositorio.Repositorio;

public class EjemploJdbc {
    public static void main(String[] args) {

        Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
        System.out.println("============ listar ============ ");
        repositorio.listar().forEach(System.out::println);

        System.out.println("============ obtener por id ============ ");
        
        System.out.println(repositorio.porId(1L));
        System.out.println("============ insertar nuevo producto ============ ");
        Producto producto=new Producto();
        producto.setNombre("Notebook Asus ROG");
        producto.setPrecio(2550);
        producto.setFechaRegistro(new Date());
        Categoria categoria=new Categoria();
        categoria.setId(3L);
        producto.setCategoria(categoria);
        repositorio.guardar(producto);
        System.out.println("Producto guardado con éxito!!!");
        repositorio.listar().forEach(System.out::println);

    }
}
