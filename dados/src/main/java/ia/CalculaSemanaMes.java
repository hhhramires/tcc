package ia;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculaSemanaMes {

    public static List<SemanaMes> calculateEntradaDeDinheiroPorSemanaMes(List<Linha> linhas) {
        // Agrupa as despesas por semana e mês
        Map<Integer, Map<Integer, BigDecimal>> groupedExpenses =
                linhas.stream()
                        .collect(Collectors.groupingBy(
                                linha -> buscarSemanaDoMes(linha.getData()), // número da semana do mês
                                Collectors.groupingBy(
                                        linha -> linha.getData().getMonthValue(), // mês
                                        Collectors.reducing(BigDecimal.ZERO, Linha::getEntradaDinheiro, BigDecimal::add) // soma dos valores
                                )
                        ));

        // Mapeia o agrupamento para uma lista de SemanaMes
        return groupedExpenses.entrySet().stream()
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(innerEntry -> new SemanaMes(entry.getKey(), innerEntry.getKey(), innerEntry.getValue())))
                .collect(Collectors.toList());
    }

    public static List<SemanaMes> calculateSaidaDeDinheiroPorSemanaMes(List<Linha> linhas) {
        // Agrupa as despesas por semana e mês
        Map<Integer, Map<Integer, BigDecimal>> groupedExpenses =
                linhas.stream()
                        .collect(Collectors.groupingBy(
                                linha -> buscarSemanaDoMes(linha.getData()), // número da semana do mês
                                Collectors.groupingBy(
                                        linha -> linha.getData().getMonthValue(), // mês
                                        Collectors.reducing(BigDecimal.ZERO, Linha::getSaidaDinheiro, BigDecimal::add) // soma dos valores
                                )
                        ));

        // Mapeia o agrupamento para uma lista de SemanaMes
        return groupedExpenses.entrySet().stream()
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(innerEntry -> new SemanaMes(entry.getKey(), innerEntry.getKey(), innerEntry.getValue())))
                .collect(Collectors.toList());
    }

    public static int buscarSemanaDoMes(LocalDate localDate) {
//        Monday — Segunda-feira.
//        Tuesday — Terça-feira.
//        Wednesday — Quarta-feira.
//        Thursday — Quinta-feira.
//        Friday — Sexta-feira.
//        Saturday — Sábado.
//        Sunday — Domingo.

        WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);
        int diaDoMes = localDate.get(weekFields.weekOfMonth());

        return diaDoMes;
    }

}
