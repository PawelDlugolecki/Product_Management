package dlugolecki.pawel.repository.repos;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.repository.crud.CrudRepository;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Integer> {
    Optional<Country> findOneById(Integer id);
    Optional<Country> findOneByName(String name);
}
