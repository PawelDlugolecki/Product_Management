package dlugolecki.pawel.service.serviceImpl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.repos.CountryRepository;
import dlugolecki.pawel.service.ServiceHelpers;
import dlugolecki.pawel.service.Syntax;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CountryImpl {
    private ServiceHelpers service = new ServiceHelpers();
    private Syntax syntax = new Syntax();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);

    public void addCountry() {
        try {
            System.out.println("Enter: COUNTRY NAME");
            String countryName = scanner.nextLine();
            if (syntax.trueCountryName(countryName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (service.doesCountryWithNameExist(countryName)) {
                throw new IllegalArgumentException("DUPLICATE COUNTRY");
            }
            Optional<Country> country = countryRepository
                    .findOneByName(countryName);
            countryRepository.add
                    (Country
                            .builder()
                            .name(countryName)
                            .build());
            System.out.println("THE COUNTRY HAS BEEN ADDED CORRECTLY: " + countryName);

        } catch (Exception e) {
            throw new MyException("SERVICE: ADD COUNTRY: ", LocalDateTime.now());
        }
    }

    public void deleteOneCountry() {
        try {
            System.out.println("Enter: COUNTRY ID");
            Integer id = scanner.nextInt();

            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG COUNTRY ID");
            }
            if (!service.doesCountryWithIdExist(id)) {
                throw new NullPointerException("COUNTRY WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Country> countryOp = countryRepository
                    .findOneById(id);
            countryRepository.delete(id);
            System.out.println("COUNTRY WITH ID " + id + " HAS BEEN DELETE");

        } catch (Exception e) {
            throw new MyException("SERVICE: DELETE ONE COUNTRY: ", LocalDateTime.now());
        }
    }

    public void deleteAllCountry() {
        for (Country c : countryRepository.findAll()) {
            countryRepository.delete(c.getId());
        }
        System.out.println("SERVICE: ALL COUNTRY HAS BEEN DELETE");
    }

    public void updateCountry() {
        try {
            System.out.println("Enter: COUNTRY NAME");
            String countryName = scanner.nextLine();
            if (syntax.trueCountryName(countryName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (!service.doesCountryWithNameExist(countryName)) {
                throw new IllegalArgumentException("COUNTRY WITH GIVEN NAME DOES NOT EXIST");
            }

            System.out.println("Enter: COUNTRY NEW NAME");
            String countryNewName = scanner.nextLine();
            if (syntax.trueCountryName(countryNewName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NEW NAME");
            }
            if (service.doesCountryWithNameExist(countryNewName)) {
                throw new IllegalArgumentException("DUPLICATE COUNTRY");
            }
            Country country = countryRepository
                    .findOneByName(countryName)
                    .orElseThrow(NullPointerException::new);
            country.setName(countryNewName);
            countryRepository.update(country);
            System.out.println("THE COUNTRY HAS BEEN UPDATDED CORRECTLY: " + "FROM: " + countryName + " TO: "+ countryNewName);

        } catch (Exception e) {
            throw new MyException("SERVICE: UPDATE COUNTRY: ", LocalDateTime.now());
        }
    }

    public Country findOneCountry() {
        try {
            System.out.println("Enter: COUNTRY ID");
            Integer id = scanner.nextInt();
            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG CATEGORY ID");
            }
            if (!service.doesCountryWithIdExist(id)) {
                throw new NullPointerException("COUNTRY WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Country> countryOp = countryRepository
                    .findOneById(id);
            System.out.println(countryOp);
            System.out.println("THE COUNTRY WITH GIVEN ID HAS BEEN FOUND:");

        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ONE COUNTRY: ", LocalDateTime.now());
        }
        return null;
    }

    public List<Country> findAllCountry() {
        try {
            System.out.println("ALL COUNTRIES");
            List<Country> countries = countryRepository.findAll();
            if (countries == null || countries.isEmpty()) {
                throw new NullPointerException("COUNTRY IS EMPTY");
            }
            countries.forEach(s -> System.out.println(s));

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: FIND ALL COUNTRY: ", LocalDateTime.now());
        }
        return  null;
    }
}
