
package es.donatodev.java.jdbc.repositorio;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre entidades.
 * Proporciona los métodos básicos para interactuar con la persistencia de datos.
 *
 * @param <T> Tipo de la entidad gestionada por el repositorio.
 */
public interface Repositorio<T> {

    /**
     * Obtiene una lista con todas las entidades almacenadas.
     *
     * @return una {@code List} que contiene todas las entidades de tipo {@code T}.
     */
    List<T> listar();

    /**
     * Busca una entidad por su identificador único.
     *
     * @param id el identificador único de la entidad.
     * @return la entidad encontrada de tipo {@code T}, o {@code null} si no existe.
     */
    T porId(Long id);

    /**
     * Guarda una nueva entidad o actualiza una existente en el repositorio.
     * Si la entidad ya existe (por ejemplo, tiene un ID válido), se actualiza; si no, se crea.
     *
     * @param t la entidad de tipo {@code T} a guardar o actualizar.
     */
    void guardar(T t);

    /**
     * Elimina una entidad del repositorio según su identificador único.
     *
     * @param id el identificador único de la entidad a eliminar.
     */
    void eliminar(Long id);
}
