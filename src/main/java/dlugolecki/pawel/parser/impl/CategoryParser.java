package dlugolecki.pawel.parser.impl;
import dlugolecki.pawel.exceptions.ExceptionCode;
import dlugolecki.pawel.exceptions.MyException;
import dlugolecki.pawel.model.Category;
import dlugolecki.pawel.parser.Parser;

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
        } catch (MyException e) {
            throw new MyException(ExceptionCode.VALIDATION, "Category parser: " + text);
        }
    }
}
