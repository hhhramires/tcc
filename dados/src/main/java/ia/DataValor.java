package ia;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DataValor {

    private String data;
    private BigDecimal valor;

}
