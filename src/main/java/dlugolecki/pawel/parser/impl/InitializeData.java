package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.parser.Parser;
import dlugolecki.pawel.repository.crud.CrudRepository;
import dlugolecki.pawel.repository.impl.*;
import dlugolecki.pawel.repository.repos.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InitializeData {
    private static CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private static CountryRepository countryRepository = new CountryRepositoryImpl();
    private static CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private static ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private static ProductRepository productRepository = new ProductRepositoryImpl();
    private static OrderRepository orderRepository = new OrderRepositoryImpl();

    public static void initializeData(String filename) {
        try (FileReader reader = new FileReader(filename); Scanner sc = new Scanner(reader)) {
            categoryRepository.deleteAll();
            customerRepository.deleteAll();
            countryRepository.deleteAll();
            producerRepository.deleteAll();
            productRepository.deleteAll();
            orderRepository.deleteAll();

            addAllItems("categories.txt", new CategoryParser(), new CategoryRepositoryImpl());
            addAllItems("countries.txt", new CountryParser(), new CountryRepositoryImpl());
            addAllItems("customers.txt", new CustomerParser(), new CustomerRepositoryImpl());
            addAllItems("producers.txt", new ProducerParser(), new ProducerRepositoryImpl());
            addAllItems("products.txt", new ProductParser(), new ProductRepositoryImpl());
            addAllItems("orders.txt", new OrderParser(), new OrderRepositoryImpl());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new NullPointerException("INITIALIZE DATA/INITIALIZE DATA");
        } catch (IOException e) {
            throw new MyException("INITIALIZE DATA-INITIALIZE DATA", LocalDateTime.now());
        }
    }

    private static <T> List<T> getItemsFromFile(String filename, Parser<T> parser) {
        try (FileReader reader = new FileReader(filename); Scanner sc = new Scanner(reader)) {
            return Files
                    .lines(Paths.get(filename))
                    .map(line -> parser.parse(line))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException("INITIALIZE DATA/GET ITEMS FROM FILE - EXCEPTION", LocalDateTime.now());
        }
    }

    private static <T, ID> void addAllItems(String filename, Parser<T> parser, CrudRepository<T, ID> crudRepository) {
        List<T> items = getItemsFromFile(filename, parser);
        for (T t : items) {
            crudRepository.add(t);
        }
    }
}
