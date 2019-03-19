package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.model.Category;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.model.Producer;
import dlugolecki.pawel.model.Product;
import dlugolecki.pawel.parser.Parser;
import dlugolecki.pawel.repository.impl.CategoryRepositoryImpl;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.impl.ProducerRepositoryImpl;
import dlugolecki.pawel.repository.repos.CategoryRepository;
import dlugolecki.pawel.repository.repos.CountryRepository;
import dlugolecki.pawel.repository.repos.ProducerRepository;
import java.math.BigDecimal;
import java.util.Arrays;

public class ProductParser implements Parser<Product> {
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();

    public Product parse(String text) {
        try {
            String[] productArr = text.split(";");
            System.out.println(Arrays.toString(productArr));
            Producer producer = producerRepository
                    .findOneByName(productArr[3])
                    .get();
            Category category = categoryRepository
                    .findOneByName(productArr[2])
                    .get();
            Country country = countryRepository
                    .findOneByName(productArr[4])
                    .get();

            if (productArr != null) {
                return Product.builder()
                        .name(productArr[0])
                        .price(new BigDecimal(productArr[1]))
                        .categoryId(category.getId())
                        .producerId(producer.getId())
                        .countryId(country.getId())
                        .build();
            }
            return null;
        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "Product parser: " + text);
        }
    }
}
