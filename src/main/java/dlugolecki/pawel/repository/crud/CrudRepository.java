package dlugolecki.pawel.repository.crud;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    void add(T t);
    void update(T t);
    void delete(ID id);
    void deleteAll();
    Optional<T> findOneById(ID id);
    List<T> findAll();
}
