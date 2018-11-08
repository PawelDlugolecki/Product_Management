package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.model.Producer;
import dlugolecki.pawel.parser.Parser;
import dlugolecki.pawel.repository.impl.CountryRepositoryImpl;
import dlugolecki.pawel.repository.repos.CountryRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ProducerParser implements Parser<Producer> {
    private CountryRepository countryRepository = new CountryRepositoryImpl();

    public Producer parse(String text) {
        try {
            String[] producerArr = text.split(";");
            System.out.println(Arrays.toString(producerArr));
            Country country = countryRepository.findOneByName(producerArr[2])
                    .get();

            if (producerArr != null) {
                return Producer.builder()
                        .name(producerArr[0])
                        .budget(new BigDecimal(producerArr[1]))
                        .countryId(country.getId())
                        .build();
            }
            return null;
        } catch (Exception e) {
            throw new MyException("INITIALIZE DATA/PRODUCER/PARSE - EXCEPTION", LocalDateTime.now());
        }
    }
}
