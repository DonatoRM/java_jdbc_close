package es.donatodev.java.jdbc;

import es.donatodev.java.jdbc.modelo.Producto;
import es.donatodev.java.jdbc.repositorio.ProductoRepositorioImpl;
import es.donatodev.java.jdbc.repositorio.Repositorio;

public class EjemploJdbcDelete {
    public static void main(String[] args) {

        Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
        System.out.println("============ listar ============ ");
        repositorio.listar().forEach(System.out::println);

        System.out.println("============ obtener por id ============ ");
        
        System.out.println(repositorio.porId(1L));
        System.out.println("============ eliminar nuevo producto ============ ");
        repositorio.eliminar(3L);
        System.out.println("Producto eliminado con éxito!!!");
        repositorio.listar().forEach(System.out::println);

    }
}
