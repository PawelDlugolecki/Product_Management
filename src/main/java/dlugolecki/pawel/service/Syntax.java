package dlugolecki.pawel.service;
import java.math.BigDecimal;

public class Syntax {
    private static final String NAME_REGEX = "[A-Z ]+";
    private static final String BUDGET_AND_PRICE = "[(\\d+.)?\\d+]";
    private static final Integer AGE_REGEX = Integer.valueOf(0);
    private static final BigDecimal DISCOUNT_REGEX = BigDecimal.valueOf(0);

    public boolean trueCategoryName (String categoryName) {
        if (!categoryName.matches("^[A-Z ]+")) {
            return true;
        }
        return false;
    }

    public boolean trueCountryName (String countryName) {
        if (!countryName.matches("^[A-Z ]+")) {
            return true;
        }
        return false;
    }

    public boolean trueCustomerNameAndSurname (String customer) {
        if (!customer.matches("^[A-Z ]+")) {
            return true;
        }
        return false;
    }

    public boolean trueProducerName (String producerName) {
        if (!producerName.matches(NAME_REGEX)){
            return true;
        }
        return false;
    }

    public boolean trueProductName (String productName) {
        if (!productName.matches(NAME_REGEX)){
            return true;
        }
        return false;
    }

    public boolean trueAge (String age) {
        if (age == null || Integer.parseInt(age) > 18) {
            return  false;
        }
        return true;
    }

    public boolean trueBudget (String budget) {
        if (budget == null || budget.isEmpty() || !budget.matches(BUDGET_AND_PRICE)) {
            return false;
        }
        return true;
    }

    public boolean truePrice (String price) {
        if (price == null || price.isEmpty() || !price.matches(BUDGET_AND_PRICE)) {
            return false;
        }
        return true;
    }

    public boolean trueId (String id) {
        if (id == null || !id.matches(NAME_REGEX)) {
            return true;
        }
        return false;
    }

    public boolean trueDiscount (BigDecimal discount) {
        if (discount.compareTo(DISCOUNT_REGEX) > 0 && discount.compareTo(DISCOUNT_REGEX) < 1) {
            return true;
        }
        return false;
    }

    public boolean trueQuantity (Integer quantity) {
        if (quantity > 0 && quantity < 100) {
            return false;
        }
        return true;
    }
}
