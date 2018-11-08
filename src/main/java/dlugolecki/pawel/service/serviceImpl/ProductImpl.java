package dlugolecki.pawel.service.serviceImpl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Category;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.model.Producer;
import dlugolecki.pawel.model.Product;
import dlugolecki.pawel.repository.impl.CategoryRepositoryImpl;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.impl.ProducerRepositoryImpl;
import dlugolecki.pawel.repository.impl.ProductRepositoryImpl;
import dlugolecki.pawel.repository.repos.CategoryRepository;
import dlugolecki.pawel.repository.repos.CountryRepository;
import dlugolecki.pawel.repository.repos.ProducerRepository;
import dlugolecki.pawel.repository.repos.ProductRepository;
import dlugolecki.pawel.service.ServiceHelpers;
import dlugolecki.pawel.service.Syntax;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductImpl {
    private ServiceHelpers service = new ServiceHelpers();
    private Syntax syntax = new Syntax();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);

    public void addProduct() {
        try {
            Country country = null;
            Category category = null;
            Producer producer = null;

            System.out.println("Enter: PRODUCT NAME");
            String productName = scanner.nextLine();
            if (syntax.trueProductName(productName)) {
                throw new IllegalArgumentException("WRONG PRODUCT NAME");
            }

            System.out.println("Enter: PRODUCT PRICE");
            String productPrice = scanner.nextLine();
            if (syntax.truePrice(productPrice)) {
                throw new IllegalArgumentException("WRONG PRODUCT price");
            }

            service.showCategory();
            System.out.println("Enter: CATEGORY NAME");
            String categoryName = scanner.nextLine();
            if (syntax.trueCategoryName(categoryName)) {
                throw new IllegalArgumentException("WRONG CATEGORY NAME");
            }
            if (!service.doesCategoryWithNameExist(categoryName)) {
                throw new NullPointerException("CATEGORY WITH GIVEN NAME DOES NOT EXIST");
            }

            service.showProducer();
            System.out.println("Enter: PRODUCER NAME");
            String producerName = scanner.nextLine();
            List<Producer> producers = getProducerByName(producerName);
            if (producers == null || producers.size() == 0) {
                throw new NullPointerException("GIVEN PRODUCER DOES NOT EXIST");
            }
            else {
                producer = getProducer(producers);
            }

            service.showCountry();
            System.out.println("Enter: COUNTRY NAME");
            String countryName = scanner.nextLine();
            if (syntax.trueCountryName(countryName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (!service.doesCountryWithNameExist(countryName)) {
                throw new NullPointerException("COUNTRY WITH GIVEN DOES NOT EXIST");
            }
            if (service.findGivenProduct(productName, producerName)) {
                throw new IllegalArgumentException("DUPLICATE PRODUCT");
            }

            category = categoryRepository
                    .findOneByName(categoryName)
                    .get();
            producer = producerRepository
                    .findOneByName(producerName)
                    .get();
            country = countryRepository
                    .findOneByName(countryName)
                    .get();

            productRepository.add
                    (Product
                            .builder()
                            .name(productName)
                            .price(new BigDecimal(productPrice))
                            .categoryId(category.getId())
                            .producerId(producer.getId())
                            .countryId(country.getId())
                            .build());
            System.out.println("THE PRODUCT HAS BEEN ADDED CORRECTLY: " + productName + ", WITH PRICE: " + productPrice + ", WITH CATEGORY NAME: " + categoryName + ", WITH PRODUCER NAME: " + producerName + ", WITH COUNTRY NAME: " + countryName);

        } catch (Exception e) {
            throw new MyException("SERVICE: ADD PRODUCT: ", LocalDateTime.now());
        }
    }

    public void deleteOneProduct() {
        try {
            System.out.println("Enter: PRODUCT ID");
            Integer id = scanner.nextInt();

            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG PRODUCT ID");
            }
            if (!service.doesProductWithIdExist(id)) {
                throw new NullPointerException("PRODUCT WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Product> productOp = productRepository
                    .findOneById(id);

            productRepository.delete(id);
            System.out.println("PRODUCT WITH ID " + id + " HAS BEEN DELETE");

        } catch (Exception e) {
            throw new MyException("SERVICE: DELETE ONE PRODUCT: ", LocalDateTime.now());
        }
    }

    public void deleteAllProduct() {
        for (Product p : productRepository.findAll()) {
            productRepository.delete(p.getId());
        }
        System.out.println("SERVICE: ALL PRODUCT HAS BEEN DELETE");
    }

    public void updateProduct() {
        try {
            service.showProduct();
            System.out.println("Enter: PRODUCT ID");
            String id = scanner.nextLine();


            Product product = productRepository
                    .findOneById(Integer.valueOf(id))
                    .orElseThrow(NullPointerException::new);

            System.out.println("Enter: PRODUCT NEW NAME");
            String productNewName = scanner.nextLine();
            if (syntax.trueProductName(productNewName)) {
                throw new IllegalArgumentException("WRONG PRODUCT NAME");
            }

            System.out.println("Enter: PRODUCT NEW PRICE");
            String productNewPrice = scanner.nextLine();
            if (syntax.truePrice(productNewPrice)) {
                throw new IllegalArgumentException("WRONG PRODUCT price");
            }
            Country country = null;
            Category category = null;
            Producer producer = null;

            service.showCategory();
            System.out.println("Enter: PRODUCT NEW CATEGORY NAME");
            String categoryNewName = scanner.nextLine();
            if (syntax.trueCategoryName(categoryNewName)) {
                throw new IllegalArgumentException("WRONG CATEGORY NAME");
            }
            if (!service.doesCategoryWithNameExist(categoryNewName)) {
                throw new NullPointerException("CATEGORY WITH GIVEN NAME DOES NOT EXIST");
            }

            service.showProducer();
            service.showProducer();
            System.out.println("Enter: PRODUCER NAME");
            String producerNewName = scanner.nextLine();
            List<Producer> producers = getProducerByName(producerNewName);
            if (producers == null || producers.size() == 0) {
                throw new NullPointerException("GIVEN PRODUCER DOES NOT EXIST");
            } else {
                producer = getProducer(producers);
            }

            service.showCountry();
            System.out.println("Enter: PRODUCT NEW COUNTRY");
            String countryNewName = scanner.nextLine();
            if (syntax.trueCountryName(countryNewName)) {
                throw new IllegalArgumentException("WRONG COUNTRY NAME");
            }
            if (!service.doesCountryWithNameExist(countryNewName)) {
                throw new NullPointerException("COUNTRY WITH GIVEN NAME DOES NOT EXIST");
            }
            if (service.findGivenProduct(productNewName, producerNewName)) {
                throw new IllegalArgumentException("DUPLICATE PRODUCT");
            }


            category = categoryRepository
                    .findOneByName(categoryNewName).get();
            producer = producerRepository
                    .findOneByName(producerNewName).get();
            country = countryRepository
                    .findOneByName(countryNewName).get();

            product.setName(producerNewName);
            product.setPrice(new BigDecimal(productNewPrice));
            product.setCategoryId(category.getId());
            product.setProducerId(producer.getId());
            product.setCountryId(country.getId());
            productRepository.update(product);
            System.out.println("THE PRODUCT HAS BEEN UPDATED CORRECTLY: " + productNewName + ", WITH PRICE: " + productNewPrice + ", WITH CATEGORY NAME: " + categoryNewName + ", WITH PRODUCER NAME: " + producerNewName + ", WITH COUNTRY NAME: " + countryNewName);

        } catch (Exception e) {
            throw new MyException("SERVICE: UPDATE PRODUCT: ", LocalDateTime.now());
        }
    }

    public Product findOneProduct() {
        try {
            System.out.println("Enter: PRODUCT ID");
            Integer id = scanner.nextInt();
            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG PRODUCT ID");
            }
            if (!service.doesProductWithIdExist(id)) {
                throw new NullPointerException("PRODUCT WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<Product> productOp = productRepository
                    .findOneById(id);
            System.out.println(productOp);
            System.out.println("THE PRODUCT WITH GIVEN ID HAS BEEN FOUND:");

        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ONE PRODUCT: ", LocalDateTime.now());
        }
        return null;
    }

    public List<Product> findAllProduct() {
        try {
            System.out.println("ALL PRODUCTS");
            List<Product> products = productRepository.findAll();
            if (products == null || products.isEmpty()) {
                throw new NullPointerException("PRODUCT IS EMPTY");
            }
            products.forEach(s -> System.out.println(s));

        } catch (Exception e) {
            throw new MyException("SERVICE: FIND ALL PRODUCT: ", LocalDateTime.now());
        }
        return  null;
    }

    public List<Producer> getProducerByName(String producerName) {
        return producerRepository
                .findAll()
                .stream()
                .filter(x -> x.getName().equals(producerName))
                .collect(Collectors.toList());
    }
    public Producer getProducer (List<Producer> producers) {
        Producer producer;
        if (producers.size() == 1) {
            producer = producers.get(0);
        } else {
            for (int i = 0; i < producers.size(); i++) {
                System.out.println((i + 1) + ". " + producers.get(i).getName() + " " + "countryId- " + producers.get(i).getCountryId());
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Select Producer from list - choose number:");
                choice = scanner.nextInt();
                scanner.nextLine();
            } while (choice < 1 || choice > producers.size());
            producer = producers.get(choice);
        }
        return producer;
    }
}
