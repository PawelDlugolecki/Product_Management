package dlugolecki.pawel.repository.repos;
import dlugolecki.pawel.model.Customer;
import dlugolecki.pawel.repository.crud.CrudRepository;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findOneCustomerByNameAndSurname(String customerName, String customerSurname);
    Optional<Customer> findOneGivenCustomer(String name, String surname, int countryId);
}
