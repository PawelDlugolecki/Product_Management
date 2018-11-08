package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.model.Customer;
import dlugolecki.pawel.parser.Parser;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.repos.CountryRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

public class CustomerParser implements Parser<Customer> {
    private CountryRepository countryRepository = new CountryRepositoryImpl();

    public Customer parse(String text) {
        try {
            String[] customerArr = text.split(";");
            System.out.println(Arrays.toString(customerArr));
            Country country = countryRepository
                    .findOneByName(customerArr[3])
                    .orElseThrow(NullPointerException::new);

            if (customerArr != null) {
                return Customer
                        .builder()
                        .name(customerArr[0])
                        .surname(customerArr[1])
                        .age(Integer.parseInt(customerArr[2]))
                        .countryId(country.getId())
                        .build();
            }
            return null;
        } catch (Exception e) {
            throw new MyException("INITIALIZE DATA/CUSTOMER/PARSE - EXCEPTION", LocalDateTime.now());
        }
    }
}
