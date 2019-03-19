package dlugolecki.pawel.service;
import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserDataService {
    private static Scanner sc = new Scanner(System.in);

    public String getString(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    public int getInt(String message) {
        System.out.println(message);
        String text  = sc.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.VALIDATION, "Value is not int: " + text);
        }
        return Integer.parseInt(text);
    }

    public BigDecimal getBigDecimal(String message) {
        System.out.println(message);
        String text = sc.nextLine();

        if (!text.matches("(\\d+\\.)*\\d+")) {
            throw new MyException(ExceptionCode.VALIDATION, "Value is not a decimal: " + text);
        }

        return new BigDecimal(text);
    }
}
