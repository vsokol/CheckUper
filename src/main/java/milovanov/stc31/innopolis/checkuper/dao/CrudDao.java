package milovanov.stc31.innopolis.checkuper.dao;

import java.util.List;

public interface CrudDao<T> {
    List<T> getAll();
    void add(T obj);
    void update(T obj);
    void getById(T id);
    void delete(T  obj);
}
