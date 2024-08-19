package ia;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Linha {

    String sistema;
    String coop;
    String ua;
    String id_terminal;
    LocalDate data;
    BigDecimal saldo_inicial;
    BigDecimal saldo_final;
    BigDecimal entrada_dinheiro;
    BigDecimal saida_dinheiro;
    BigDecimal suprimento;
    BigDecimal recolhimento;

}
