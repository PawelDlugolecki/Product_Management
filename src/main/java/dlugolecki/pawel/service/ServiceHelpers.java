package dlugolecki.pawel.service;

import dlugolecki.pawel.model.*;
import dlugolecki.pawel.repository.impl.*;
import dlugolecki.pawel.repository.repos.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceHelpers {
    private static OrderRepository orderTabRepository = new OrderRepositoryImpl();
    private static CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private static ProductRepository productRepository = new ProductRepositoryImpl();
    private static ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private static CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private static CountryRepository countryRepository = new CountryRepositoryImpl();

    public boolean doesCategoryWithNameExist(String categoryName) {
        Optional<Category> categoryOp = categoryRepository.findOneByName(categoryName);
        return categoryOp.isPresent();
    }

    public static boolean doesCategoryWithIdExist(Integer id) {
        Optional<Category> categoryOp = categoryRepository.findOneById(id);
        return categoryOp.isPresent();
    }

    public boolean doesCountryWithNameExist(String countryName) {
        Optional<Country> countryOp = countryRepository.findOneByName(countryName);
        return countryOp.isPresent();
    }

    public boolean doesCountryWithIdExist(Integer id) {
        Optional<Country> countryOp = countryRepository.findOneById(id);
        return countryOp.isPresent();
    }

    public void showCategory() {
        categoryRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getName))
                .forEach(s -> System.out.println(s.getId() + " " + s.getName()));
    }

    public void showCountry() {
        countryRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Country::getName))
                .forEach(s -> System.out.println(s.getId() + ". " + s.getName()));
    }

    public void showCustomer() {
        customerRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getSurname)
                        .thenComparing(Customer::getName))
                .forEach(s -> System.out.println(s.getId() + ". " + s.getName() + " " + s.getSurname() + " " + s.getAge()));
    }

    public void showProducer() {
        producerRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Producer::getName))
                .forEach(s -> System.out.println("(id:) " + s.getId() + ". " + s.getName() + " " + s.getBudget() + " - (countryId)" + s.getCountryId()));
    }

    public void showProduct() {
        productRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getName))
                .forEach(s -> System.out.println
                        (s.getName() + " - " + s.getPrice() + " - (categoryId)" + s.getCategoryId() + " - (producerId)" + s.getProducerId() + " - (countryId)" + s.getCountryId()));
    }

    public boolean findGivenCustomer(String customerName, String customerSurname, String countryName) {
        Country country = countryRepository.findOneByName(countryName).orElse(null);
        if (country == null) {
            return false;
        }
        List<Customer> customers = customerRepository
                .findAll()
                .stream()
                .filter(c -> c.getName().equals(customerName) &&
                        c.getSurname().equals(customerSurname) &&
                        c.getCountryId().equals(country.getId()))
                .collect(Collectors.toList());
        return customers != null && !customers.isEmpty();
    }

    public boolean findGivenProducer(String producerName, String countryName) {
        Country country = countryRepository.findOneByName(countryName).orElse(null);
        if (country == null) {
            return false;
        }
        List<Producer> producers = producerRepository
                .findAll()
                .stream()
                .filter(p -> p.getName().equals(producerName) &&
                        p.getCountryId().equals(country.getId()))
                .collect(Collectors.toList());
        return producers != null && !producers.isEmpty();
    }

    public boolean findGivenProduct(String productName, String producerName) {
        {
            Producer producer = producerRepository.findOneByName(producerName).orElse(null);
            if (producer == null) {
                return false;
            }
            List<Product> products =
                    productRepository
                            .findAll()
                            .stream()
                            .filter(p -> p.getName().equalsIgnoreCase(productName) &&
                                    p.getProducerId().equals(producer.getId()))
                            .collect(Collectors.toList());
            return products != null && !products.isEmpty();
        }
    }

    public boolean doesCustomerWithIdExist(Integer id) {
        Optional<Customer> customerOp = customerRepository.findOneById(id);
        return customerOp.isPresent();
    }

    public boolean doesProducerWithIdExist(Integer id) {
        Optional<Producer> producerOp = producerRepository.findOneById(id);
        return producerOp.isPresent();
    }

    public boolean doesProducerWithNameExist(String producerName) {
        Optional<Producer> producerOp = producerRepository.findOneByName(producerName);
        return producerOp.isPresent();
    }

    public boolean doesProductWithIdExist(Integer id) {
        Optional<Product> productOp = productRepository.findOneById(id);
        return productOp.isPresent();
    }

    public boolean doesOrderWithIdExist(Integer id) {
        Optional<OrderTab> orderTabOp = orderTabRepository.findOneById(id);
        return orderTabOp.isPresent();
    }

    public void availableCategory() {
        System.out.println("- - - - -");
        System.out.println(("Available category: "));
        showCategory();
        System.out.println("- - - - -");
    }

    public void availableCountry() {
        System.out.println("- - - - -");
        System.out.println(("Available country: "));
        showCountry();
        System.out.println("- - - - -");
    }

    public void availableCustomer() {
        System.out.println("- - - - -");
        System.out.println(("Available customer: "));
        showCustomer();
        System.out.println("- - - - -");
    }

    public void availableProducer() {
        System.out.println("- - - - -");
        System.out.println(("Available producer: "));
        showProducer();
        System.out.println("- - - - -");
    }

    public void availableProduct() {
        System.out.println("- - - - -");
        System.out.println(("Available product: "));
        showProduct();
        System.out.println("- - - - -");
    }

    /*public void availableOrder() {
        System.out.println("- - - - -");
        System.out.println(("Available order: "));
        showOrder();
        System.out.println("- - - - -");
    }*/
}
