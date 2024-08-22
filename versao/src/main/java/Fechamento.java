import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Fechamento {

    private String central;
    private String agencia;
    private String terminal;
    private LocalDate data;
    private BigDecimal saldoInicial;
    private BigDecimal saldoFinal;
    private BigDecimal entradaDinheiro;
    private BigDecimal saidaDinheiro;

}
