package ia.separado;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SemanaMesValor {

    private Integer semana;
    private String mes;
    private BigDecimal valor;

}
