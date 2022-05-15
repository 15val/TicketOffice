package model.dao;


import java.sql.SQLException;
import java.util.List;


public interface GenericDao<T> extends AutoCloseable {
    void create (T entity) throws SQLException;
    T findById(int id);
    List<T> findAll();
    void update(T entity) throws SQLException;
    void delete(int id) throws SQLException;
    void close();

}
