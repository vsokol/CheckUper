package milovanov.stc31.innopolis.checkuper.dao;

import java.util.List;

public interface CRUD<T> {
    List<T> getAll();

    void create(T obj);

    void update(T obj);

    T getById(Long id);

    void remove(T obj);
}
