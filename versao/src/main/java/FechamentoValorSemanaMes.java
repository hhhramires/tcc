import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FechamentoValorSemanaMes {

    private Integer semana;
    private Integer mes;
    private BigDecimal valor;

}
