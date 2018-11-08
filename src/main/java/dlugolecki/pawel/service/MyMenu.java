package dlugolecki.pawel.service;
import dlugolecki.pawel.parser.impl.InitializeData;
import dlugolecki.pawel.service.serviceImpl.*;
import java.util.Scanner;

public class MyMenu {
    private Scanner scanner = new Scanner(System.in);
    private ProductImpl productImpl = new ProductImpl();
    private CategoryImpl categoryImpl = new CategoryImpl();
    private CountryImpl countryImpl = new CountryImpl();
    private CustomerImpl customerImpl = new CustomerImpl();
    private ProducerImpl producerImpl = new ProducerImpl();
    private OrderImpl orderImpl = new OrderImpl();
    private Statistics statistics = new Statistics();


    public boolean mainMenu() {
        return chooseOptionFromMainMenu();
    }

    private boolean chooseOptionFromMainMenu() {
        System.out.println("CHOOSE TABLE:");
        System.out.println("1. CATEGORY");
        System.out.println("2. COUNTRY");
        System.out.println("3. CUSTOMER");
        System.out.println("4. PRODUCER");
        System.out.println("5. PRODUCT");
        System.out.println("6. ORDER");
        System.out.println("7. STATISTICS");
        System.out.println("8. INITIALIZE DATA");
        System.out.println("9. EXIT");
        int option = scanner.nextInt();
        boolean isFinished = true;
        switch (option) {
            case 1:
                categoryOperations(mainOperations());
                break;
            case 2:
                countryOperations(mainOperations());
                break;
            case 3:
                custumerOperations(mainOperations());
                break;
            case 4:
                producerOperations(mainOperations());
                break;
            case 5:
                productOperations(mainOperations());
                break;
            case 6:
                orderTabOperations(mainOperations());
                break;
            case 7:
                statistics();
                break;
            case 8:
                InitializeData.initializeData("data.txt");
            case 9:
                System.out.println("END OF APPLICATION");
                isFinished = false;
                break;
            default:
        }
        return isFinished;
    }

    private int mainOperations() {
        System.out.println("CHOOSE OPERATION:");
        System.out.println("1. ADD");
        System.out.println("2. UPDATE");
        System.out.println("3. DELETE ONE");
        System.out.println("4. DELETE ALL");
        System.out.println("5. FIND ONE");
        System.out.println("6. FIND ALL");
        return scanner.nextInt();
    }

    private void categoryOperations(int option) {
        switch (option) {
            case 1:
                categoryImpl.addCategory();
                break;
            case 2:
                categoryImpl.findAllCategory();
                categoryImpl.updateCategory();
                break;
            case 3:
                categoryImpl.findAllCategory();
                categoryImpl.deleteOneCategory();
                break;
            case 4:
                categoryImpl.deleteAllCategory();
            case 5:
                categoryImpl.findOneCategory();
                break;
            case 6:
                categoryImpl.findAllCategory();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void countryOperations(int option) {
        switch (option) {
            case 1:
                countryImpl.addCountry();
                break;
            case 2:
                countryImpl.updateCountry();
                break;
            case 3:
                countryImpl.findAllCountry();
                countryImpl.deleteOneCountry();
                break;
            case 4:
                countryImpl.deleteAllCountry();
            case 5:
                countryImpl.findAllCountry();
                countryImpl.findOneCountry();
                break;
            case 6:
                countryImpl.findAllCountry();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void custumerOperations(int option) {
        switch (option) {
            case 1:
                customerImpl.addCustomer();
                break;
            case 2:
                customerImpl.updateCustomer();
                break;
            case 3:
                customerImpl.findAllCustomer();
                customerImpl.deleteOneCustomer();
                break;
            case 4:
                customerImpl.deleteAllCustomer();
            case 5:
                customerImpl.findOneCustomer();
                break;
            case 6:
                customerImpl.findAllCustomer();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }
    private void orderTabOperations(int option) {
        switch (option) {
            case 1:
                orderImpl.addOrder();
                break;
            case 2:
                orderImpl.updateOrder();
                break;
            case 3:
                orderImpl.findAllOrder();
                orderImpl.deleteOneOrder();
                break;
            case 4:
                orderImpl.deleteAllOrder();
            case 5:
                orderImpl.findOneOrder();
                break;
            case 6:
                orderImpl.findAllOrder();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void producerOperations(int option) {
        switch (option) {
            case 1:
                producerImpl.addProducer();
                break;
            case 2:
                producerImpl.updateProducer();
                break;
            case 3:
                producerImpl.findAllProducer();
                producerImpl.deleteOneProducer();
                break;
            case 4:
                producerImpl.deleteAllProducer();
            case 5:
                producerImpl.findAllProducer();
                producerImpl.findOneProducer();
                break;
            case 6:
                producerImpl.findAllProducer();
                break;
        }
    }

    private void productOperations(int option) {
        switch (option) {
            case 1:
                productImpl.addProduct();
                break;
            case 2:
                productImpl.updateProduct();
                break;
            case 3:
                productImpl.findAllProduct();
                productImpl.deleteOneProduct();
                break;
            case 4:
                productImpl.deleteAllProduct();
            case 5:
                productImpl.findAllProduct();
                productImpl.findOneProduct();
                break;
            case 6:
                productImpl.findAllProduct();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void statistics() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose statistics:");
        System.out.println("1. SHOW THE MOST POPULAR CATEGORY");
        System.out.println("2. SHOW THW MOST POPULAR COUNTRY");
        System.out.println("3. SHOW THE MOST POPULAR CUSTOMER");
        System.out.println("4. SHOW THE MOST POPULAR PRODUCER");
        System.out.println("5. SHOW THE MOST POPULAR PRODUCT");
        System.out.println("6. SORT PRODUCERS BY AMOUNT SPENT ON PRODUCTS");
        System.out.println("7. SORT PRODUCERS WITH AVERAGE PRICE");
        System.out.println("8. SORT CATEGORIES WITH ORDERS");
        System.out.println("9. FIND THE MOST EXPENSIVE PRODUCT");
        switch (Integer.parseInt(sc.nextLine())) {
            case 1:
                System.out.println(statistics.mostPopularCategory());
                break;
            case 2:
                System.out.println(statistics.mostPopularCountry());
                break;
            case 3:
                System.out.println(statistics.mostPopularCustomer());
                break;
            case 4:
                System.out.println(statistics.mostPopularProducer());
                break;
            case 5:
                System.out.println(statistics.mostPopularProduct());
                break;
            case 6:
                System.out.println(statistics.sortProducersByAmountSpentOnProducts());
                break;
            case 7:
                System.out.println(statistics.producersWithAveragePrice());
                break;
            case 8:
                System.out.println(statistics.sortedCategoriesWithOrders());
                break;
        }
    }
}
