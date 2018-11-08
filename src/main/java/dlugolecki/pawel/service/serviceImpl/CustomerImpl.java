package dlugolecki.pawel.service.serviceImpl;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.model.Customer;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.impl.CustomerRepositoryImpl;
import dlugolecki.pawel.repository.repos.CountryRepository;
import dlugolecki.pawel.repository.repos.CustomerRepository;
import dlugolecki.pawel.service.ServiceHelpers;
import dlugolecki.pawel.service.Syntax;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CustomerImpl {
    private Connection connection = DbConnection.getInstance().getConnection();
    private ServiceHelpers service = new ServiceHelpers();
    private Syntax syntax = new Syntax();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);

    public void addCustomer() {
        try {
            System.out.println("Enter: CUSTOMER NAME");
            String customerName = scanner.nextLine();
            if (syntax.trueCustomerNameAndSurname(customerName)) {
                throw new IllegalArgumentException("WRONG CUSTOMER NAME");
            }

            System.out.println("Enter: CUSTOMER SURNAME");
            String customerSurname = scanner.nextLine();
            if (syntax.trueCustomerNameAndSurname(customerSurname)) {
                throw new IllegalArgumentException("WRONG CUSTOMER SURNAME");
            }

            System.out.println("Enter: CUSTOMER AGE");
            String age = scanner.nextLine();
            if (syntax.trueAge(age)) {
                throw new IllegalArgumentException("WRONG CUSTOMER AGE: AGE MUST BE MORE THAN 18");
            }
            System.out.println("Enter: COUNTRY NAME WITH LIST");
            service.showCountry();
            String countryName = scanner.nextLine();
            if (syntax.trueCountryName(countryName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (!service.doesCountryWithNameExist(countryName)) {
                throw new NullPointerException("COUNTRY WITH GIVEN NAME DOES NOT EXIST");
            }
            if (service.findGivenCustomer(customerName, customerSurname, countryName)) {
                throw new IllegalArgumentException("DUPLICATE CUSTOMER");
            }

            Country country = countryRepository
                    .findOneByName(countryName).get();
            customerRepository.add(
                    Customer
                            .builder()
                            .name(customerName)
                            .surname(customerSurname)
                            .age(Integer.valueOf(age))
                            .countryId(country.getId())
                            .build());

            System.out.println("THE CUSTOMER HAS BEEN ADDED CORRECTLY: ");
        } catch (Exception e) {
            throw new MyException("SERVICE: ADD CUSTOMER: ", LocalDateTime.now());
        }
    }

    public void deleteOneCustomer() {
        try {
            System.out.println("Enter: CUSTOMER ID");
            Integer id = scanner.nextInt();

            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG CUSTOMER ID");
            }
            if (!service.doesCustomerWithIdExist(id)) {
                throw new NullPointerException("CUSTOMER WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Customer> customerOp = customerRepository
                    .findOneById(id);

            customerRepository.delete(id);
            System.out.println("CUSTOMER WITH ID " + id + " HAS BEEN DELETE");
        } catch (Exception e) {
            throw new MyException("SERVICE: DELETE ONE CUSTOMER: ", LocalDateTime.now());
        }
    }

    public void deleteAllCustomer() {
        for (Customer c : customerRepository.findAll()) {
            customerRepository.delete(c.getId());
        }
        System.out.println("SERVICE: ALL CUSTOMER HAS BEEN DELETE");
    }

    public void updateCustomer() {
        try {
            service.showCustomer();
            System.out.println("Enter: CUSTOMER ID");
            String id = scanner.nextLine();
            Customer customer = customerRepository
                    .findOneById(Integer.valueOf(id))
                    .orElseThrow(NullPointerException::new);

            System.out.println("Enter: CUSTOMER NEW NAME");
            String customerNewName = scanner.nextLine();
            if (syntax.trueCustomerNameAndSurname(customerNewName)) {
                throw new IllegalArgumentException("WRONG CUSTOMER NAME");
            }

            System.out.println("Enter: CUSTOMER NEW SURNAME");
            String customerNewSurname = scanner.nextLine();
            if (syntax.trueCustomerNameAndSurname(customerNewSurname)) {
                throw new IllegalArgumentException("WRONG CUSTOMER SURNAME");
            }

            System.out.println("Enter: CUSTOMER NEW AGE");
            String customerNewAge = scanner.nextLine();
            if (syntax.trueAge(customerNewAge)) {
                throw new IllegalArgumentException("WRONG CUSTOMER AGE");
            }

            service.showCountry();
            System.out.println("Enter: CUSTOMER NEW COUNTRY");
            String countryNewName = scanner.nextLine();
            if (syntax.trueCountryName(countryNewName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (!service.doesCountryWithNameExist(countryNewName)) {
                throw new NullPointerException("COUNTRY WITH GIVEN NAME DOES NOT EXIST");
            }
            if (service.findGivenCustomer(customerNewName, customerNewSurname, countryNewName)) {
                throw new IllegalArgumentException("DUPLICATE CUSTOMER");
            }

            Country country = countryRepository
                    .findOneByName(countryNewName).get();

            customer.setName(customerNewName);
            customer.setSurname(customerNewSurname);
            customer.setAge(Integer.valueOf(customerNewAge));
            customer.setCountryId(country.getId());
            customerRepository.update(customer);
            System.out.println("THE CUSTOMER HAS BEEN UPDATED TO: " + customerNewName + " " + customerNewSurname + " with country: " + countryNewName);

        } catch (Exception e) {
            throw new MyException("SERVICE: UPDATE CUSTOMER: ", LocalDateTime.now());
        }
    }
    public Customer findOneCustomer() {
        try {
            System.out.println("Enter: CUSTOMER ID");
            Integer id = scanner.nextInt();

            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG CUSTOMER ID");
            }
            if (!service.doesCustomerWithIdExist(id)) {
                throw new NullPointerException("CUSTOMER WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Customer> customerOp = customerRepository
                    .findOneById(id);
            System.out.println(customerOp);
            System.out.println("THE CUSTOMER WITH GIVEN ID HAS BEEN FOUND:");

        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ONE CUSTOMER: ", LocalDateTime.now());
        }
        return null;
    }

    public List<Customer> findAllCustomer() {
        try {
            System.out.println("ALL CUSTOMERS");
            List<Customer> customers = customerRepository.findAll();

            if (customers == null || customers.isEmpty()) {
                throw new NullPointerException("CUSTOMER IS EMPTY");
            }
            customers.forEach(s -> System.out.println(s));
        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ALL CUSTOMER: ", LocalDateTime.now());
        }
        return  null;
    }
}
