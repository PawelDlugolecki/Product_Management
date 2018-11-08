package dlugolecki.pawel.repository.repos;
import dlugolecki.pawel.model.Product;
import dlugolecki.pawel.repository.crud.CrudRepository;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findOneByName (String name);
    Optional<Product> findGivenProduct(String name, int producerId);
}
