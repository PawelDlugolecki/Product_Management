package dlugolecki.pawel.service;

import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.parser.impl.InitializeData;

import java.util.Scanner;

public class MyMenu {
    private Scanner scanner = new Scanner(System.in);
    private ServiceApp serviceApp = new ServiceApp();
    private UserDataService dataService = new UserDataService();


    public void mainMenu() {
        chooseOptionFromMainMenu();
    }

    private void chooseOptionFromMainMenu() {
        try {
            System.out.println("CHOOSE TABLE:");
            System.out.println("1. Category");
            System.out.println("2. Country");
            System.out.println("3. Customer");
            System.out.println("4. Producer");
            System.out.println("5. Product");
            System.out.println("6. Order");
            System.out.println("7. Statistics");
            System.out.println("8. Initialize Data");
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
                    customerOperations(mainOperations());
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
                    break;
                case 8:
                    InitializeData.initializeData("data.txt");
                case 9:
                    System.out.println("END OF APPLICATION");
                    isFinished = false;
                    /*break;*/
                default:
            }
            isFinished = true;

        } catch (MyException e) {
            System.out.println("\n****************************** EXCEPTIONS *************************************");
            System.out.println(e.getExceptionInfo().getMessage());
            System.out.println(e.getExceptionInfo().getExceptionCode());
            System.out.println(e.getExceptionInfo().getTimeOfException());
            System.out.println("*******************************************************************************\n");
        }
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
                serviceApp.addCategory(
                        dataService.getString("Enter category name"));
                break;
            case 2:
                serviceApp.findAllCategory();
                serviceApp.updateCategory(
                        dataService.getString("Enter category name"),
                        dataService.getString("Enter new category name"));
                break;
            case 3:
                serviceApp.findAllCategory();
                serviceApp.deleteOneCategory(
                        dataService.getInt("Enter category id")
                );
                break;
            case 4:
                serviceApp.deleteAllCategory();
            case 5:
                serviceApp.findOneCategory(
                        dataService.getInt("Enter category id")
                );
                break;
            case 6:
                serviceApp.findAllCategory();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void countryOperations(int option) {
        switch (option) {
            case 1:
                serviceApp.addCountry(
                        dataService.getString("Enter country name"));
                break;
            case 2:
                serviceApp.updateCountry(
                        dataService.getString("Enter country name"),
                        dataService.getString("Enter country new name"));
                break;
            case 3:
                serviceApp.findAllCountry();
                serviceApp.deleteOneCountry(
                        dataService.getInt("Enter country id")
                );
                break;
            case 4:
                serviceApp.deleteAllCountry();
            case 5:
                serviceApp.findAllCountry();
                serviceApp.findOneCountry(
                        dataService.getInt("Enter country id"));
                break;
            case 6:
                serviceApp.findAllCountry();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void customerOperations(int option) {
        switch (option) {
            case 1:
                serviceApp.addCustomer();
                break;
            case 2:
                serviceApp.updateCustomer();
                break;
            case 3:
                serviceApp.deleteOneCustomer();
                break;
            case 4:
                serviceApp.deleteAllCustomer();
            case 5:
                serviceApp.findOneCustomer();
                break;
            case 6:
                serviceApp.findAllCustomer();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void orderTabOperations(int option) {
        switch (option) {
            case 1:
                serviceApp.addOrder();
                break;
            case 2:
                serviceApp.updateOrder();
                break;
            case 3:
                serviceApp.findAllOrder();
                serviceApp.deleteOneOrder();
                break;
            case 4:
                serviceApp.deleteAllOrder();
            case 5:
                serviceApp.findOneOrder();
                break;
            case 6:
                serviceApp.findAllOrder();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }

    private void producerOperations(int option) {
        switch (option) {
            case 1:
                serviceApp.addProducer();
                break;
            case 2:
                serviceApp.updateProducer();
                break;
            case 3:
                serviceApp.findAllProducer();
                serviceApp.deleteOneProducer();
                break;
            case 4:
                serviceApp.deleteAllProducer();
            case 5:
                serviceApp.findAllProducer();
                serviceApp.findOneProducer();
                break;
            case 6:
                serviceApp.findAllProducer();
                break;
        }
    }

    private void productOperations(int option) {
        switch (option) {
            case 1:
                serviceApp.addProduct();
                break;
            case 2:
                serviceApp.updateProduct();
                break;
            case 3:
                serviceApp.findAllProduct();
                serviceApp.deleteOneProduct();
                break;
            case 4:
                serviceApp.deleteAllProduct();
            case 5:
                serviceApp.findAllProduct();
                serviceApp.findOneProduct();
                break;
            case 6:
                serviceApp.findAllProduct();
                break;
            default:
                System.out.println("There is not operation with number: " + option);
        }
    }
}

