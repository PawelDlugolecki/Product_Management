package dlugolecki.pawel.service.serviceImpl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.model.Producer;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.impl.ProducerRepositoryImpl;
import dlugolecki.pawel.repository.repos.CountryRepository;
import dlugolecki.pawel.repository.repos.ProducerRepository;
import dlugolecki.pawel.service.ServiceHelpers;
import dlugolecki.pawel.service.Syntax;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProducerImpl {
    private ServiceHelpers service = new ServiceHelpers();
    private Syntax syntax = new Syntax();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);

    public void addProducer() {
        try {
            System.out.println("Enter: PRODUCER NAME");
            String producerName = scanner.nextLine();
            if (syntax.trueProducerName(producerName)) {
                throw new IllegalArgumentException("WRONG PRODUCER NAME");
            }

            System.out.println("Enter: PRODUCER BUDGET");
            String producerBudget = scanner.nextLine();
            if (syntax.trueBudget(producerBudget)) {
                throw new IllegalArgumentException("WRONG PRODUCER BUDGET");
            }

            service.showCountry();
            System.out.println("Enter: COUNTRY NAME ");
            String countryName = scanner.nextLine();
            if (syntax.trueCountryName(countryName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (!service.doesCountryWithNameExist(countryName)) {
                throw new NullPointerException("COUNTRY WITH GIVEN NAME DOES NOT EXIST");
            }
            if (service.findGivenProducer(producerName, countryName)) {
                throw new IllegalArgumentException("DUPLICATE PRODUCER");
            }
            Country country = countryRepository
                    .findOneByName(countryName).get();
            producerRepository.add(
                    Producer
                            .builder()
                            .name(producerName)
                            .budget(new BigDecimal(producerBudget))
                            .countryId(country.getId())
                            .build()
            );
            System.out.println("THE PRODUCER HAS BEEN ADDED CORRECTLY");

        } catch (Exception e) {
            throw new MyException("SERVICE: ADD PRODUCER: ", LocalDateTime.now());
        }
    }

    public void deleteOneProducer() {
        try {
            System.out.println("Enter: PRODUCER ID");
            Integer id = scanner.nextInt();

            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG PRODUCER ID");
            }
            if (!service.doesProducerWithIdExist(id)) {
                throw new NullPointerException("PRODUCER WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Producer> producerOp = producerRepository
                    .findOneById(id);

            producerRepository.delete(id);
            System.out.println("PRODUCER WITH ID " + id + " HAS BEEN DELETE");

        } catch (Exception e) {
            throw new MyException("SERVICE: DELETE ONE PRODUCER: ", LocalDateTime.now());
        }
    }

    public void deleteAllProducer() {
        for (Producer p : producerRepository.findAll()) {
            producerRepository.delete(p.getId());
        }
        System.out.println("SERVICE: ALL PRODUCER HAS BEEN DELETE");
    }

    public void updateProducer() {
        try {
            service.showProducer();
            System.out.println("Enter: PRODUCER ID");
            String id = scanner.nextLine();

            Producer producer = producerRepository
                    .findOneById(Integer.valueOf(id))
                    .orElseThrow(NullPointerException::new);

            System.out.println("Enter: PRODUCER NEW NAME");
            String producerNewName = scanner.nextLine();
            if (syntax.trueProducerName(producerNewName)) {
                throw new IllegalArgumentException("WRONG PRODUCER NAME");
            }

            System.out.println("Enter: PRODUCER NEW BUDGET");
            String producerNewBudget = scanner.nextLine();
            if (syntax.trueBudget(producerNewBudget)) {
                throw new IllegalArgumentException("WRONG PRODUCER BUDGET");
            }

            service.showCountry();
            System.out.println("Enter: PRODUCER NEW COUNTRY");
            String countryNewName = scanner.nextLine();
            if (syntax.trueCountryName(countryNewName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (!service.doesCountryWithNameExist(countryNewName)) {
                throw new NullPointerException("COUNTRY WITH GIVEN NAME DOES NOT EXIST");
            }
            if (service.doesProducerWithNameExist(producerNewName) && !producerNewName.equals(producer.getName())) {
                throw new IllegalArgumentException("DUPLICATE PRODUCER");
            }
            producer.setName(producer.getName());
            if (producerNewName.equals(producer.getName()) && !producerNewBudget.equals(producer.getBudget())) {
                producer.setName(producerNewName);
            }
            Country country = countryRepository
                    .findOneByName(countryNewName).get();

            producer.setName(producerNewName);
            producer.setBudget(new BigDecimal(producerNewBudget));
            producer.setCountryId(country.getId());
            producerRepository.update(producer);
            System.out.println("THE PRODUCER HAS BEEN UPDATED TO: " + producerNewName + " " + producerNewBudget + " with country: " + countryNewName);

        } catch (Exception e) {
            throw new MyException("SERVICE: UPDATE PRODUCER: ", LocalDateTime.now());
        }
    }

    public Producer findOneProducer() {
        try {
            System.out.println("Enter: PRODUCER ID");
            Integer id = scanner.nextInt();

            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG PRODUCER ID");
            }
            if (!service.doesProducerWithIdExist(id)) {
                throw new NullPointerException("PRODUCER WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Producer> producerOp = producerRepository
                    .findOneById(id);
            System.out.println(producerOp);
            System.out.println("THE PRODUCER WITH GIVEN ID HAS BEEN FOUND:");

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: FIND ONE PRODUCER: ", LocalDateTime.now());
        }
        return null;
    }

    public List<Producer> findAllProducer() {
        try {
            System.out.println("ALL PRODUCERS");
            List<Producer> producers = producerRepository.findAll();
            if (producers == null || producers.isEmpty()) {
                throw new NullPointerException("PRODUCER IS EMPTY");
            }
            producers.forEach(s -> System.out.println(s));

        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ALL PRODUCER: ", LocalDateTime.now());
        }
        return  null;
    }
}
