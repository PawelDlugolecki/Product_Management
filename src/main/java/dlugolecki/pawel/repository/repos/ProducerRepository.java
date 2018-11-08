package dlugolecki.pawel.repository.repos;
import dlugolecki.pawel.model.Producer;
import dlugolecki.pawel.repository.crud.CrudRepository;
import java.util.Optional;

public interface ProducerRepository extends CrudRepository<Producer, Integer> {
    Optional<Producer> findOneByName(String name);
}
