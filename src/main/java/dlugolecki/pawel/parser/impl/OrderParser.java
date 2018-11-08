package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.*;
import dlugolecki.pawel.parser.Parser;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.impl.CustomerRepositoryImpl;
import dlugolecki.pawel.repository.impl.ProducerRepositoryImpl;
import dlugolecki.pawel.repository.impl.ProductRepositoryImpl;
import dlugolecki.pawel.repository.repos.CountryRepository;
import dlugolecki.pawel.repository.repos.CustomerRepository;
import dlugolecki.pawel.repository.repos.ProducerRepository;
import dlugolecki.pawel.repository.repos.ProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class OrderParser implements Parser<OrderTab> {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();

    public OrderTab parse(String text) {
        try {
            String[] orderTabArr = text.split(";");
            System.out.println(Arrays.toString(orderTabArr));

            Country country = countryRepository
                    .findOneByName(orderTabArr[2])
                    .orElseThrow(NullPointerException::new);
            Producer producer = producerRepository
                    .findOneByName(orderTabArr[4])
                    .orElseThrow(NullPointerException::new);
            Customer customer = customerRepository
                    .findOneGivenCustomer(orderTabArr[0], orderTabArr[1], country.getId())
                    .get();
            Product product = productRepository
                    .findGivenProduct(orderTabArr[3], producer.getId())
                    .get();

            if (orderTabArr != null) {
                return OrderTab
                        .builder()
                        .customerId(customer.getId())
                        .productId(product.getId())
                        .discount(new BigDecimal(orderTabArr[5]))
                        .quantity(Integer.parseInt(orderTabArr[6]))
                        .build();
            }
            return null;
        } catch (Exception e) {
            throw new MyException("INITIALIZE DATA/ORDER/PARSE - EXCEPTION", LocalDateTime.now());
        }
    }
}
