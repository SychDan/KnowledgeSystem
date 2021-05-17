package knowledge.service;

import java.util.List;

public interface CRUDService<T> {

    List<T> listAll();

    T getById(Integer id);

    void saveOrUpdate(T domainObject);

    void delete(Integer id);

    void delete(T domainObject);
}