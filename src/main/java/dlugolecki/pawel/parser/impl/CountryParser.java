package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.parser.Parser;
import java.time.LocalDateTime;

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
        } catch (Exception e) {
            throw new MyException("INITIALIZE DATA/COUNTRY/PARSE - EXCEPTION", LocalDateTime.now());
        }
    }
}
