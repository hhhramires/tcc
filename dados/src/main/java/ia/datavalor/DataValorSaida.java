package ia.datavalor;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class DataValorSaida {

    private LocalDate data;
    private BigDecimal valor;

}
