package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exception.MyException;
import dlugolecki.pawel.model.Category;
import dlugolecki.pawel.parser.Parser;
import java.time.LocalDateTime;

public class CategoryParser implements Parser<Category> {

    public Category parse(String text) {
        try {
            System.out.println(text);

            if (text != null) {
                return Category
                        .builder()
                        .name(text)
                        .build();
            }
            return null;
        } catch (Exception e) {
            throw new MyException("INITIALIZE DATA/CATEGORY/PARSE - EXCEPTION", LocalDateTime.now());
        }
    }
}
