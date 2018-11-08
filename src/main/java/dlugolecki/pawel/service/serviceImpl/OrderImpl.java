package dlugolecki.pawel.service.serviceImpl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Customer;
import dlugolecki.pawel.model.OrderTab;
import dlugolecki.pawel.model.Product;
import dlugolecki.pawel.repository.impl.CustomerRepositoryImpl;
import dlugolecki.pawel.repository.impl.OrderRepositoryImpl;
import dlugolecki.pawel.repository.impl.ProductRepositoryImpl;
import dlugolecki.pawel.repository.repos.CustomerRepository;
import dlugolecki.pawel.repository.repos.OrderRepository;
import dlugolecki.pawel.repository.repos.ProductRepository;
import dlugolecki.pawel.service.ServiceHelpers;
import dlugolecki.pawel.service.Syntax;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderImpl {
    private ServiceHelpers service = new ServiceHelpers();
    private Syntax syntax = new Syntax();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private OrderRepository orderRepository = new OrderRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);

    public void addOrder() {
        try{
            Customer customer = null;
            Product product = null;

            service.showCustomer();
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
            List<Customer> customers = getCustomerByNameAndSurname(customerName, customerSurname);
            if (customers == null || customers.size() == 0) {
                throw new NullPointerException("GIVEN CUSTOMER DOES NOT EXIST");
            }
            else {
                customer = getCustomer(customers);
            }

            service.showProduct();
            System.out.println("Enter: PRODUCT NAME");
            String productName = scanner.nextLine();
            if (syntax.trueProductName(productName)) {
                throw new IllegalArgumentException("WRONG PRODUCT NAME");
            }
            List<Product> products = getProductByName(productName);
            if (products == null || products.size() == 0) {
                throw new NullPointerException("GIVEN PRODUCt DOES NOT EXIST");
            }
            else {
                product = getProduct(products);
            }

            System.out.println("Enter: DISCOUNT");
            String discount = scanner.nextLine();
            if (syntax.trueDiscount(new BigDecimal(discount))) {
                throw new IllegalArgumentException("WRONG DISCOUNT. THE DISCOUNT MUST BE IN THE RANGE FROM 0 TO 1");
            }

            System.out.println("Enter: QUANTITY");
            Integer quantity = scanner.nextInt();
            if (syntax.trueQuantity(quantity)) {
                throw new IllegalArgumentException("WRONG QUANTITY. THE QUANTITY MUST BE IN THE RANGE FROM 0 TO 100");
            }

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
                            .discount(new BigDecimal(discount))
                            .quantity(quantity)
                            .build());
            System.out.println("THE ORDER HAS BEEN ADDED CORRECTLY: " + customerName + " " + customerSurname + " WITH PRODUCT: " + productName + " WITH DISCOUNT " + discount + " WITH QUANTITY " + quantity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: ADD ORDER: ", LocalDateTime.now());
        }
    }

    public void deleteOneOrder() {
        try {
            System.out.println("Enter: ORDER ID");
            Integer id = scanner.nextInt();
            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG ORDER ID");
            }
            if (!service.doesOrderWithIdExist(id)) {
                throw new NullPointerException("ORDER WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<OrderTab> orderTabOp = orderRepository
                    .findOneById(id);
            orderRepository.delete(id);
            System.out.println("ORDER WITH ID " + id + " HAS BEEN DELETE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: DELETE ONE ORDER: ", LocalDateTime.now());
        }
    }

    public void deleteAllOrder() {
        for (OrderTab o : orderRepository.findAll()) {
            orderRepository.delete(o.getId());
        }
        System.out.println("SERVICE: ALL PRODUCT HAS BEEN DELETE");
    }


    public void updateOrder() {
        try{
            orderRepository.findAll();
            System.out.println("Enter: ORDER ID");
            String id = scanner.nextLine();

            OrderTab orderTab = orderRepository
                    .findOneById(Integer.valueOf(id))
                    .orElseThrow(NullPointerException::new);

            Customer customer = null;
            Product product = null;

            service.showCustomer();
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
            List<Customer> customers = getCustomerByNameAndSurname(customerNewName, customerNewSurname);
            if (customers == null || customers.size() == 0) {
                throw new NullPointerException("GIVEN CUSTOMER DOES NOT EXIST");
            }
            else {
                customer = getCustomer(customers);
            }

            service.showProduct();
            System.out.println("Enter: PRODUCT NEW NAME");
            String productNewName = scanner.nextLine();
            if (syntax.trueProductName(productNewName)) {
                throw new IllegalArgumentException("WRONG PRODUCT NAME");
            }
            List<Product> products = getProductByName(productNewName);
            if (products == null || products.size() == 0) {
                throw new NullPointerException("GIVEN PRODUCT DOES NOT EXIST");
            }
            else {
                product = getProduct(products);
            }

            System.out.println("Enter: NEW DISCOUNT");
            String newDiscount = scanner.nextLine();
            if (syntax.trueDiscount(new BigDecimal(newDiscount))) {
                throw new IllegalArgumentException("WRONG DISCOUNT. THE DISCOUNT MUST BE IN THE RANGE FROM 0 TO 1");
            }

            System.out.println("Enter: NEW QUANTITY");
            Integer newQuantity = scanner.nextInt();
            if (syntax.trueQuantity(newQuantity)) {
                throw new IllegalArgumentException("WRONG QUANTITY. THE QUANTITY MUST BE IN THE RANGE FROM 0 TO 100");
            }

            customer = customerRepository
                    .findOneCustomerByNameAndSurname(customerNewName, customerNewSurname)
                    .get();
            product = productRepository
                    .findOneByName(productNewName)
                    .get();

            orderTab.setCustomerId(customer.getId());
            orderTab.setProductId(product.getId());
            orderTab.setDiscount(new BigDecimal(newDiscount));
            orderTab.setQuantity(newQuantity);
            orderRepository.update(orderTab);
            System.out.println("THE ORDER HAS BEEN UPDATED CORRECTLY: " + customerNewName + " " + customerNewSurname + " WITH PRODUCT: " + productNewName + " WITH DISCOUNT " + newDiscount + " WITH QUANTITY " + newQuantity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: UPDATE ORDER: ", LocalDateTime.now());
        }
    }

    public OrderTab findOneOrder() {
        try {
            System.out.println("Enter: ORDER ID");
            Integer id = scanner.nextInt();
            if (!syntax.trueId(String.valueOf(id))) {
                throw new IllegalArgumentException("WRONG ORDER ID");
            }
            if (!service.doesOrderWithIdExist(id)) {
                throw new NullPointerException("ORDER WITH GIVEN ID DOES NOT EXIST");
            }
            Optional<OrderTab> orderTabOp = orderRepository
                    .findOneById(id);
            System.out.println(orderTabOp);
            System.out.println("THE ORDER WITH GIVEN ID HAS BEEN FOUND:");

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: FIND ONE ORDER: ", LocalDateTime.now());
        }
        return null;
    }

    public List<OrderTab> findAllOrder() {
        try {
            System.out.println("ALL ORDER");
            List<OrderTab> orders = orderRepository.findAll();
            if (orders == null || orders.isEmpty()) {
                throw new NullPointerException("ORDER IS EMPTY");
            }
            orders.forEach(s -> System.out.println(s)); } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE: FIND ALL ORDER: ", LocalDateTime.now());
        }
        return  null;
    }

    public List<Customer> getCustomerByNameAndSurname(String customerName, String customerSurname) {
        return customerRepository
                .findAll()
                .stream()
                .filter(x -> x.getName().equals(customerName) &&
                        x.getSurname().equals(customerSurname))
                .collect(Collectors.toList());
    }
    public Customer getCustomer (List<Customer> customers) {
        Customer customer;
        if (customers.size() == 1) {
            customer = customers.get(0);
        } else {
            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i) + ". " + customers.get(i).getName() + "." + customers.get(i).getSurname() + ". " + customers.get(i).getAge() + ". " + "countryId- " + customers.get(i).getCountryId());
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Select Customer from list - choose number:");
                choice = scanner.nextInt();
                scanner.nextLine();
            } while (choice < 1 || choice > customers.size());
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
    public Product getProduct (List<Product> products) {
        Product product;
        if (products.size() == 1) {
            product = products.get(0);
        } else {
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i).getName() + ". " + products.get(i).getProducerId());
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Select Product from list - choose number:");
                choice = scanner.nextInt();
                scanner.nextLine();
            } while (choice < 1 || choice > products.size());
            product = products.get(choice);
        }
        return product;
    }
}

