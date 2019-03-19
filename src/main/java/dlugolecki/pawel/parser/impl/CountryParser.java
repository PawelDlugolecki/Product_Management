package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.parser.Parser;

public class CountryParser implements Parser<Country> {

    public Country parse(String text) {
        try {
            System.out.println(text);

            if (text != null) {
                return Country
                        .builder()
                        .name(text)
                        .build();
            }
            return null;
        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "Country parser: " + text);
        }
    }
}
