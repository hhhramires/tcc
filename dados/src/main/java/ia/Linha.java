package ia;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Linha {

    private String sistema;
    private String coop;
    private String ua;
    private String id_terminal;
    private LocalDate data;
    private BigDecimal saldoInicial;
    private BigDecimal saldoFinal;
    private BigDecimal entradaDinheiro;
    private BigDecimal saidaDinheiro;
    private BigDecimal suprimento;
    private BigDecimal recolhimento;

}
