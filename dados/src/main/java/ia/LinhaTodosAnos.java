package ia;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LinhaTodosAnos {

    String dia;
    BigDecimal valor2021;
    BigDecimal valor2022;
    BigDecimal valor2023;

}
