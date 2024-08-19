package ia;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EntradaSaida {

    private BigDecimal entrada;
    private BigDecimal saida;

}
