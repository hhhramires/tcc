import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class FechamentoValorDia {

    private BigDecimal valor;
    private LocalDate data;

}
