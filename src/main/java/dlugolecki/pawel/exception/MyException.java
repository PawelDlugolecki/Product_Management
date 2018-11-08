package dlugolecki.pawel.exception;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MyException extends RuntimeException {
    private String message;
    private LocalDateTime date;

    @Override
    public String getMessage() {
        return "[MY_EXCEPTION]: " + message + " " + date;
    }
}
