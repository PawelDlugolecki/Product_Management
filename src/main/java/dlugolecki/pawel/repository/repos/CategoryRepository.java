package dlugolecki.pawel.repository.repos;
import dlugolecki.pawel.model.Category;
import dlugolecki.pawel.repository.crud.CrudRepository;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Optional<Category> findOneById(Integer id);
    Optional<Category> findOneByName(String name);
}