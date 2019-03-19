package dlugolecki.pawel.service;

import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.model.*;
import dlugolecki.pawel.repository.impl.*;
import dlugolecki.pawel.repository.repos.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ServiceApp {

    private ServiceHelpers service = new ServiceHelpers();
    private UserDataService dataService = new UserDataService();
    private ServiceHelpers serviceHelpers = new ServiceHelpers();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private OrderRepository orderRepository = new OrderRepositoryImpl();


    public void addCategory(String categoryName) {

        if (service.doesCategoryWithNameExist(categoryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Category with given name already exist: " + categoryName);
        }
        Optional<Category> category = categoryRepository.findOneByName(categoryName);
        categoryRepository.add
                (Category
                        .builder()
                        .name(categoryName)
                        .build());
        System.out.println("THE CATEGORY HAS BEEN ADDED CORRECTLY: " + categoryName);
    }

    public void updateCategory(String categoryName, String categoryNewName) {

        if (!service.doesCategoryWithNameExist(categoryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Category with given name does not exist: " + categoryName);
        }

        if (service.doesCategoryWithNameExist(categoryNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Category with given name already exist: " + categoryNewName);
        }
        Category category = categoryRepository
                .findOneByName(categoryName)
                .orElseThrow(NullPointerException::new);

        category.setName(categoryNewName);
        categoryRepository.update(category);
        System.out.println("THE CATEGORY HAS BEEN UPDATED CORRECTLY: " + "FROM: " + categoryName + " TO: " + categoryNewName);
    }

    public Category findOneCategory(Integer id) {

        if (!service.doesCategoryWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Category with given id does not exist: " + id);
        }

        Optional<Category> categoryOp = categoryRepository.findOneById(id);
        System.out.println(categoryOp);
        System.out.println("THE CATEGORY WITH GIVEN ID HAS BEEN FOUND:");

        return null;
    }

    public void deleteOneCategory(Integer id) {
        if (!service.doesCategoryWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Category with given id does not exist: " + id);
        }
        Optional<Category> categoryOp = categoryRepository
                .findOneById(id);
        categoryRepository.delete(id);
        System.out.println("CATEGORY WITH ID " + id + " HAS BEEN DELETE");

    }

    public void deleteAllCategory() {
        for (Category c : categoryRepository.findAll()) {
            categoryRepository.delete(c.getId());
        }
        System.out.println("SERVICE: ALL CATEGORY HAS BEEN DELETE");
    }

    public List<Category> findAllCategory() {

        System.out.println("ALL CATEGORIES");
        List<Category> categories = categoryRepository.findAll();

        if (categories == null || categories.isEmpty()) {
            throw new MyException(ExceptionCode.SERVICE, "Category is empty");
        }
        categories.forEach(s -> System.out.println(s));

        return null;
    }

    public void addCountry(String countryName) {

        if (service.doesCountryWithNameExist(countryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Country with given name already exist: " + countryName);
        }
        Optional<Country> country = countryRepository.findOneByName(countryName);
        countryRepository.add
                (Country
                        .builder()
                        .name(countryName)
                        .build());
        System.out.println("THE COUNTRY HAS BEEN ADDED CORRECTLY: " + countryName);
    }

    public void updateCountry(String countryName, String countryNewName) {

        if (!service.doesCountryWithNameExist(countryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Country with given name already exist: " + countryName);
        }

        if (service.doesCountryWithNameExist(countryNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Country with given name already exist: " + countryNewName);
        }
        Country country = countryRepository
                .findOneByName(countryName)
                .orElseThrow(NullPointerException::new);

        country.setName(countryNewName);
        countryRepository.update(country);
        System.out.println("THE COUNTRY HAS BEEN UPDATED CORRECTLY: " + "FROM: " + countryName + " TO: " + countryNewName);
    }

    public Country findOneCountry(Integer id) {

        if (!service.doesCountryWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Country with given id does not exist: " + id);
        }

        Optional<Country> countryOp = countryRepository.findOneById(id);
        System.out.println("THE COUNTRY WITH GIVEN ID HAS BEEN FOUND:");
        System.out.println(countryOp);

        return null;
    }

    public void deleteOneCountry(Integer id) {
        if (!service.doesCountryWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Country with given id does not exist: " + id);
        }
        Optional<Country> countryOp = countryRepository
                .findOneById(id);
        countryRepository.delete(id);
        System.out.println("COUNTRY WITH ID " + id + " HAS BEEN DELETE");

    }

    public void deleteAllCountry() {
        for (Country c : countryRepository.findAll()) {
            countryRepository.delete(c.getId());
        }
        System.out.println("SERVICE: ALL CATEGORY HAS BEEN DELETE");
    }

    public List<Country> findAllCountry() {

        System.out.println("ALL COUNTRIES");
        List<Country> countries = countryRepository.findAll();

        if (countries == null || countries.isEmpty()) {
            throw new MyException(ExceptionCode.SERVICE, "Country is empty");
        }
        countries.forEach(s -> System.out.println(s));

        return null;
    }

    public void addCustomer() {

        String customerName = dataService.getString("Enter Customer name");
        String customerSurname = dataService.getString("Enter Customer surname");
        int customerAge = dataService.getInt("Enter Customer age");

        serviceHelpers.availableCountry();
        String customerCountryName = dataService.getString("Enter country name");

        if (!serviceHelpers.doesCountryWithNameExist(customerCountryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given country does not exist: " + customerCountryName);
        }
        if (serviceHelpers.findGivenCustomer(customerName, customerSurname, customerCountryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given customer already exist");
        }

        Country country = countryRepository
                .findOneByName(customerCountryName)
                .get();

        customerRepository.add(
                Customer
                        .builder()
                        .name(customerName)
                        .surname(customerSurname)
                        .age(Integer.valueOf(customerAge))
                        .countryId(country.getId())
                        .build());

        System.out.println("THE CUSTOMER HAS BEEN ADDED CORRECTLY: ");
        System.out.println(customerName + " " + customerSurname + " " + customerAge + " " + customerCountryName);
    }

    public void updateCustomer() {

        serviceHelpers.availableCustomer();
        Integer id = dataService.getInt("Enter Customer id");
        if (!serviceHelpers.doesCustomerWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Customer with given id does not exist");
        }

        Customer customer = customerRepository
                .findOneById(id)
                .orElseThrow(NullPointerException::new);

        String customerNewName = dataService.getString("Enter new customer name");
        String customerNewSurname = dataService.getString("Enter new customer surname");
        int customerNewAge = dataService.getInt("Enter new customer age");

        serviceHelpers.availableCountry();
        String countryNewName = dataService.getString("Enter new customer country name");

        if (!serviceHelpers.doesCountryWithNameExist(countryNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given country does not exist: " + countryNewName);
        }
        if (serviceHelpers.findGivenCustomer(customerNewName, customerNewSurname, countryNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given customer already exist: " + customerNewName + " " + customerNewSurname + " " + countryNewName);
        }

        Country country = countryRepository
                .findOneByName(countryNewName)
                .get();

        customer.setName(customerNewName);
        customer.setSurname(customerNewSurname);
        customer.setAge(customerNewAge);
        customer.setCountryId(country.getId());
        customerRepository.update(customer);
        System.out.println("THE CUSTOMER HAS BEEN UPDATED TO: " + customerNewName + " " + customerNewSurname + " WITH COUNTRY: " + countryNewName);

    }

    public void deleteOneCustomer() {
        serviceHelpers.availableCustomer();
        Integer id = dataService.getInt("Enter customer id");

        if (!serviceHelpers.doesCustomerWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Customer with given id does not exist");
        }
        Optional<Customer> customerOp = customerRepository
                .findOneById(id);

        customerRepository.delete(id);
        System.out.println("CUSTOMER WITH ID " + id + " HAS BEEN DELETE");

    }

    public void deleteAllCustomer() {

        for (Customer c : customerRepository.findAll()) {
            customerRepository.delete(c.getId());
        }
        System.out.println("SERVICE: ALL CUSTOMER HAS BEEN DELETE");
    }

    public Customer findOneCustomer() {
        serviceHelpers.availableCustomer();
        Integer id = dataService.getInt("Enter customer id");

        if (!serviceHelpers.doesCustomerWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Customer with given id already exist: " + id);
        }
        Optional<Customer> customerOp = customerRepository
                .findOneById(id);

        System.out.println("THE CUSTOMER WITH GIVEN ID HAS BEEN FOUND:");
        System.out.println(customerOp);

        return null;
    }

    public List<Customer> findAllCustomer() {

        System.out.println("ALL CUSTOMERS");
        List<Customer> customers = customerRepository.findAll();

        if (customers == null || customers.isEmpty()) {
            throw new MyException(ExceptionCode.SERVICE, "Customer is empty");
        }
        customers.forEach(s -> System.out.println(s));

        return null;
    }

    public void addProducer() {

        String producerName = dataService.getString("Enter producer name");
        BigDecimal budget = dataService.getBigDecimal("Enter budget");

        service.availableCountry();
        String countryName = dataService.getString("Enter country name");

        if (!service.doesCountryWithNameExist(countryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given country does not exist: " + countryName);
        }
        if (service.findGivenProducer(producerName, countryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given producer already exist: " + producerName + ", " + countryName);
        }
        Country country = countryRepository
                .findOneByName(countryName).get();

        producerRepository.add(
                Producer
                        .builder()
                        .name(producerName)
                        .budget(budget)
                        .countryId(country.getId())
                        .build()
        );
        System.out.println("THE PRODUCER HAS BEEN ADDED CORRECTLY");
        System.out.println(producerName + " " + budget + " " + countryName);
    }

    public void updateProducer() {

        service.availableProducer();
        Integer id = dataService.getInt("Enter producer id");

        Producer producer = producerRepository
                .findOneById(Integer.valueOf(id))
                .orElseThrow(NullPointerException::new);

        String producerNewName = dataService.getString("Enter producer name");
        BigDecimal producerNewBudget = dataService.getBigDecimal("Enter budget");

        service.availableCountry();
        String countryNewName = dataService.getString("Enter country name");
        if (!service.doesCountryWithNameExist(countryNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given country does not exist: " + countryNewName);
        }
        if (service.doesProducerWithNameExist(producerNewName) && !producerNewName.equals(producer.getName())) {
            throw new MyException(ExceptionCode.SERVICE, "Given producer already exist");
        }
        producer.setName(producer.getName());
        if (producerNewName.equals(producer.getName()) && !producerNewBudget.equals(producer.getBudget())) {
            producer.setName(producerNewName);
        }
        Country country = countryRepository
                .findOneByName(countryNewName).get();

        producer.setName(producerNewName);
        producer.setBudget(producerNewBudget);
        producer.setCountryId(country.getId());

        producerRepository.update(producer);
        System.out.println("THE PRODUCER HAS BEEN UPDATED TO: " + producerNewName + " " + producerNewBudget + " " + countryNewName);

    }

    public void deleteOneProducer() {

        Integer id = dataService.getInt("Enter producer id");
        if (!service.doesProducerWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Given producer id does not exist: " + id);
        }

        Optional<Producer> producerOp = producerRepository
                .findOneById(id);

        producerRepository.delete(id);
        System.out.println("PRODUCER WITH ID " + id + " HAS BEEN DELETE");

    }

    public void deleteAllProducer() {
        for (Producer p : producerRepository.findAll()) {
            producerRepository.delete(p.getId());
        }
        System.out.println("SERVICE: ALL PRODUCER HAS BEEN DELETE");
    }

    public Producer findOneProducer() {

        Integer id = dataService.getInt("Enter producer id");
        if (!service.doesProducerWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Producer with given id does not exist: " + id);
        }
        Optional<Producer> producerOp = producerRepository
                .findOneById(id);

        System.out.println("THE PRODUCER WITH GIVEN ID HAS BEEN FOUND:");
        System.out.println(producerOp);
        return null;
    }

    public List<Producer> findAllProducer() {
        System.out.println("ALL PRODUCERS");
        List<Producer> producers = producerRepository.findAll();
        if (producers == null || producers.isEmpty()) {
            throw new MyException(ExceptionCode.SERVICE, "Producer is empty");
        }
        producers.forEach(s -> System.out.println(s));

        return null;
    }

    public void addProduct() {

        Country country = null;
        Category category = null;
        Producer producer = null;

        String productName = dataService.getString("Enter product name");
        BigDecimal productPrice = dataService.getBigDecimal("Enter product price");

        service.availableCategory();
        String categoryName = dataService.getString("Enter category name");
        if (!service.doesCategoryWithNameExist(categoryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given category does not exist");
        }

        service.availableProducer();
        String producerName = dataService.getString("Enter producer name");
        List<Producer> producers = getProducerByName(producerName);
        if (producers == null || producers.size() == 0) {
            throw new MyException(ExceptionCode.SERVICE, "Given producer doest not exist");
        } else {
            producer = getProducer(producers);
        }

        service.availableCountry();
        String countryName = dataService.getString("Enter country name");
        if (!service.doesCountryWithNameExist(countryName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given country does not exist");
        }
        if (service.findGivenProduct(productName, producerName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given product already exist");
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
                        .price((productPrice))
                        .categoryId(category.getId())
                        .producerId(producer.getId())
                        .countryId(country.getId())
                        .build());
        System.out.println("THE PRODUCT HAS BEEN ADDED CORRECTLY: " + productName + ", WITH PRICE: " + productPrice + ", WITH CATEGORY NAME: " + categoryName + ", WITH PRODUCER NAME: " + producerName + ", WITH COUNTRY NAME: " + countryName);
    }

    public void updateProduct() {

        service.availableProduct();
        Integer id = dataService.getInt("Enter product id");

        Product product = productRepository
                .findOneById(Integer.valueOf(id))
                .orElseThrow(NullPointerException::new);

        String productNewName = dataService.getString("Enter product new name");
        BigDecimal productNewPrice = dataService.getBigDecimal("Enter new product price");

        Country country = null;
        Category category = null;
        Producer producer = null;

        service.availableCategory();
        String categoryNewName = dataService.getString("Enter category new name");

        if (!service.doesCategoryWithNameExist(categoryNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given category does not exist");
        }

        service.availableProducer();
        String producerNewName = dataService.getString("Enter producer new name");
        List<Producer> producers = getProducerByName(producerNewName);
        if (producers == null || producers.size() == 0) {
            throw new MyException(ExceptionCode.SERVICE, "Given produce does not exist");
        } else {
            producer = getProducer(producers);
        }

        service.availableCountry();
        String countryNewName = dataService.getString("Enter country new name");

        if (!service.doesCountryWithNameExist(countryNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given country does not exist");
        }
        if (service.findGivenProduct(productNewName, producerNewName)) {
            throw new MyException(ExceptionCode.SERVICE, "Given product already exist");
        }

        category = categoryRepository
                .findOneByName(categoryNewName).get();
        producer = producerRepository
                .findOneByName(producerNewName).get();
        country = countryRepository
                .findOneByName(countryNewName).get();

        product.setName(producerNewName);
        product.setPrice(productNewPrice);
        product.setCategoryId(category.getId());
        product.setProducerId(producer.getId());
        product.setCountryId(country.getId());

        productRepository.update(product);
        System.out.println(
                "THE PRODUCT HAS BEEN UPDATED CORRECTLY: " + productNewName +
                        ", WITH PRICE: " + productNewPrice +
                        ", WITH CATEGORY NAME: " + categoryNewName +
                        ", WITH PRODUCER NAME: " + producerNewName +
                        ", WITH COUNTRY NAME: " + countryNewName);
    }

    public void deleteOneProduct() {

        service.availableProduct();
        Integer id = dataService.getInt("Enter prodct id");

        if (!service.doesProductWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Product with given id does not exist");
        }
        Optional<Product> productOp = productRepository
                .findOneById(id);

        productRepository.delete(id);
        System.out.println("PRODUCT WITH ID " + id + " HAS BEEN DELETE");
    }

    public void deleteAllProduct() {

        for (Product p : productRepository.findAll()) {
            productRepository.delete(p.getId());
        }
        System.out.println("SERVICE: ALL PRODUCT HAS BEEN DELETE");
    }

    public Product findOneProduct() {

        Integer id = dataService.getInt("Enter product id");
        if (!service.doesProductWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Product with given id does not exist");
        }
        Optional<Product> productOp = productRepository
                .findOneById(id);
        System.out.println("THE PRODUCT WITH GIVEN ID HAS BEEN FOUND:");
        System.out.println(productOp);
        return null;
    }

    public List<Product> findAllProduct() {
        System.out.println("ALL PRODUCTS");
        List<Product> products = productRepository.findAll();
        if (products == null || products.isEmpty()) {
            throw new MyException(ExceptionCode.SERVICE, "Product id empty");
        }
        products.forEach(s -> System.out.println(s));
        return null;
    }

    public List<Producer> getProducerByName(String producerName) {
        return producerRepository
                .findAll()
                .stream()
                .filter(x -> x.getName().equals(producerName))
                .collect(Collectors.toList());
    }

    public Producer getProducer(List<Producer> producers) {
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

    public void addOrder() {

        Customer customer = null;
        Product product = null;

        service.availableCustomer();
        String customerName = dataService.getString("Enter customer name");
        String customerSurname = dataService.getString("Enter customer surname");

        List<Customer> customers = getCustomerByNameAndSurname(customerName, customerSurname);
        if (customers == null || customers.size() == 0) {
            throw new MyException(ExceptionCode.SERVICE, "Given customer does not exist");
        } else {
            customer = getCustomer(customers);
        }

        service.availableProduct();
        String productName = dataService.getString("Enter product name");

        List<Product> products = getProductByName(productName);
        if (products == null || products.size() == 0) {
            throw new MyException(ExceptionCode.SERVICE, "Given product doest not exist");
        } else {
            product = getProduct(products);
        }

        BigDecimal discount = dataService.getBigDecimal("Enter discount");
        Integer quantity = dataService.getInt("Enter quantity");

        customer = customerRepository
                .findOneCustomerByNameAndSurname(customerName, customerSurname)
                .get();
        product = productRepository
                .findOneByName(productName)
                .get();

        orderRepository.add(
                OrderTab
                        .builder()
                        .customerId(customer.getId())
                        .productId(product.getId())
                        .discount(discount)
                        .quantity(quantity)
                        .build());
        System.out.println("THE ORDER HAS BEEN ADDED CORRECTLY: " + customerName + " " + customerSurname + " WITH PRODUCT: " + productName + " WITH DISCOUNT " + discount + " WITH QUANTITY " + quantity);

    }

    public void deleteOneOrder() {

        Integer id = dataService.getInt("Enter order id");

        if (!service.doesOrderWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Order with given id does not exist");
        }
        Optional<OrderTab> orderTabOp = orderRepository
                .findOneById(id);
        System.out.println("ORDER WITH ID " + id + " HAS BEEN DELETE");
        orderRepository.delete(id);

    }

    public void deleteAllOrder() {

        for (OrderTab o : orderRepository.findAll()) {
            orderRepository.delete(o.getId());
        }
        System.out.println("SERVICE: ALL PRODUCT HAS BEEN DELETE");
    }


    public void updateOrder() {

        orderRepository.findAll();
        Integer id = dataService.getInt("Enter order id");

        OrderTab orderTab = orderRepository
                .findOneById(Integer.valueOf(id))
                .orElseThrow(NullPointerException::new);

        Customer customer = null;
        Product product = null;

        service.availableCustomer();
        String customerNewName = dataService.getString("Enter customer name");
        String customerNewSurname = dataService.getString("Enter customer surname");

        List<Customer> customers = getCustomerByNameAndSurname(customerNewName, customerNewSurname);
        if (customers == null || customers.size() == 0) {
            throw new MyException(ExceptionCode.SERVICE, "Given customer does not exist");
        } else {
            customer = getCustomer(customers);
        }

        service.availableProduct();
        String productNewName = dataService.getString("Enter new product name");

        List<Product> products = getProductByName(productNewName);
        if (products == null || products.size() == 0) {
            throw new NullPointerException("GIVEN PRODUCT DOES NOT EXIST");
        } else {
            product = getProduct(products);
        }

        BigDecimal newDiscount = dataService.getBigDecimal("Enter new discount");
        Integer newQuantity = dataService.getInt("Enter new quantity");

        customer = customerRepository
                .findOneCustomerByNameAndSurname(customerNewName, customerNewSurname)
                .get();
        product = productRepository
                .findOneByName(productNewName)
                .get();

        orderTab.setCustomerId(customer.getId());
        orderTab.setProductId(product.getId());
        orderTab.setDiscount(newDiscount);
        orderTab.setQuantity(newQuantity);

        orderRepository.update(orderTab);
        System.out.println("THE ORDER HAS BEEN UPDATED CORRECTLY: " +
                customerNewName + " " + customerNewSurname +
                " WITH PRODUCT: " + productNewName +
                " WITH DISCOUNT " + newDiscount +
                " WITH QUANTITY " + newQuantity);
    }

    public OrderTab findOneOrder() {

        System.out.println("Enter: ORDER ID");
        Integer id = dataService.getInt("Enter order id");
        if (!service.doesOrderWithIdExist(id)) {
            throw new MyException(ExceptionCode.SERVICE, "Order with given id does not exist");
        }
        Optional<OrderTab> orderTabOp = orderRepository
                .findOneById(id);

        System.out.println("THE ORDER WITH GIVEN ID HAS BEEN FOUND:");
        System.out.println(orderTabOp);

        return null;
    }

    public List<OrderTab> findAllOrder() {

        System.out.println("ALL ORDER");
        List<OrderTab> orders = orderRepository.findAll();
        if (orders == null || orders.isEmpty()) {
            throw new MyException(ExceptionCode.SERVICE, "Order is empty");
        }
        orders.forEach(s -> System.out.println(s));
        return null;
    }

    public List<Customer> getCustomerByNameAndSurname(String customerName, String customerSurname) {
        return customerRepository
                .findAll()
                .stream()
                .filter(x -> x.getName().equals(customerName) &&
                        x.getSurname().equals(customerSurname))
                .collect(Collectors.toList());
    }

    public Customer getCustomer(List<Customer> customers) {
        Customer customer;
        if (customers.size() == 1) {
            customer = customers.get(0);
        } else {
            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i) + ". " +
                        customers.get(i).getName() +
                        "." + customers.get(i).getSurname() +
                        ". " + customers.get(i).getAge() +
                        ". " + "countryId- " + customers.get(i).getCountryId());
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Select Customer from list - choose number:");
                choice = scanner.nextInt();
                scanner.nextLine();
            } while (choice < 0 || choice > customers.size());
            customer = customers.get(choice);
        }
        return customer;
    }

    public List<Product> getProductByName(String productName) {
        return productRepository
                .findAll()
                .stream()
                .filter(x -> x.getName().equals(productName))
                .collect(Collectors.toList());
    }

    public Product getProduct(List<Product> products) {
        Product product;
        if (products.size() == 1) {
            product = products.get(0);
        } else {
            for (int i = 0; i < products.size() + 0; i++) {
                System.out.println((i) + ". " + products.get(i).getName() + ". " + products.get(i).getProducerId());
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Select product from list - choose number:");
                choice = scanner.nextInt();
                scanner.nextLine();
            } while (choice < 0 || choice > products.size());
            product = products.get(choice);
        }
        return product;
    }
}
