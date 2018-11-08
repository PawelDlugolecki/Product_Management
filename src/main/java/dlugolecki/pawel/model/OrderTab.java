package dlugolecki.pawel.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTab {
    private Integer id;
    private Integer quantity;
    private BigDecimal discount;
    private Integer customerId;
    private Integer productId;
}
