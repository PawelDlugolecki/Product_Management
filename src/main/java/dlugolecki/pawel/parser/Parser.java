package dlugolecki.pawel.parser;

public interface Parser<T> {
    T parse(String text);
}
