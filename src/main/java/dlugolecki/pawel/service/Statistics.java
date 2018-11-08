package dlugolecki.pawel.service;
import dlugolecki.pawel.model.*;
import dlugolecki.pawel.repository.impl.*;
import dlugolecki.pawel.repository.repos.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics {
    private OrderRepository orderTabRepository = new OrderRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();

    public Category mostPopularCategory() {
        return orderTabRepository
                .findAll()
                .stream()
                .map(o -> {
                    Product product
                            = productRepository
                            .findOneById(o.getProductId())
                            .orElseThrow(() -> new NullPointerException("NO PRODUCT WITH THIS ID"));
                    return categoryRepository
                            .findOneById(product.getCategoryId())
                            .orElseThrow(() -> new NullPointerException("NO CATEGORY WITH THIS ID"));
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NullPointerException("MOST POPULAR CATEGORY ERROR "));
    }

    public Map<Category, List<OrderTab>> sortedCategoriesWithOrders() {
        return orderTabRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(o -> categoryRepository
                        .findOneById(productRepository
                                .findOneById(o.getProductId())
                                .orElseThrow(NullPointerException::new)
                                .getCategoryId())
                        .orElseThrow(NullPointerException::new))
                );
    }

    public Country mostPopularCountry() {
        return orderTabRepository
                .findAll()
                .stream()
                .map(o -> {
                    Product product = productRepository
                            .findOneById(o.getProductId())
                            .orElseThrow(() -> new NullPointerException("NO PRODUCT WITH ID"));
                    return countryRepository
                            .findOneById(product.getCountryId())
                            .orElseThrow(() -> new NullPointerException("NO COUNTRY WITTH ID"));
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NullPointerException("MOST POPULAR COUNTRY"));
    }

    public Customer mostPopularCustomer() {
        return orderTabRepository
                .findAll()
                .stream()
                .map(o -> customerRepository
                        .findOneById(o.getCustomerId())
                        .orElseThrow(() -> new NullPointerException("NO CUSTOMER WITH ID")))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NullPointerException("MOST POPULAR CUSTOMER"));
    }

    public Producer mostPopularProducer() {
        return orderTabRepository
                .findAll()
                .stream()
                .map(o -> {
                    Product product = productRepository
                            .findOneById(o.getProductId())
                            .orElseThrow(() -> new NullPointerException("NO PRODUCT WITH ID"));
                    return producerRepository
                            .findOneById(product.getProducerId())
                            .orElseThrow(() -> new NullPointerException("NO PRODUCER WITH ID"));
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NullPointerException("MOST POPULAR PRODUCER"));
    }

    public Product mostPopularProduct() {
        return orderTabRepository
                .findAll()
                .stream()
                .map(o -> productRepository
                        .findOneById(o.getProductId())
                        .orElseThrow(() -> new NullPointerException("NO PRODUCER WITH ID")))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NullPointerException("MOST POPULAR PRODUCT"));
    }

    public List<Producer> sortProducersByAmountSpentOnProducts() {
        return orderTabRepository
                .findAll()
                .stream()
                .map(o -> {
                    Product product = productRepository.findOneById(o.getProductId()).orElseThrow(() -> new NullPointerException("PRODUCT ERROR " + o.getProductId()));
                    Producer producer = producerRepository.findOneById(product.getProducerId()).orElseThrow(() -> new NullPointerException("PRODUCER ERROR " + product.getProducerId()));
                    return producer;
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Map<Producer, BigDecimal> producersWithAveragePrice() {
        return orderTabRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(o -> producerRepository.findOneById(productRepository.findOneById(o.getProductId()).orElseThrow(NullPointerException::new).getProducerId()).orElseThrow(NullPointerException::new)))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> {
                    BigDecimal sum = BigDecimal.ZERO;
                    for (OrderTab o : e.getValue()) {
                        Product product = productRepository.findOneById(o.getProductId()).orElseThrow(NullPointerException::new);
                        sum = sum.add(product
                                .getPrice()
                                .multiply(BigDecimal.valueOf(o.getQuantity()))
                                .multiply(BigDecimal.ONE.subtract(o.getDiscount().divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR))));
                    }
                    return sum.divide(BigDecimal.valueOf(e.getValue().size()), 2, RoundingMode.FLOOR);
                }));
    }
}

