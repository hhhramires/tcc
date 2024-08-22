import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        List<Fechamento> fechamentos = File.lerArquivo("gn");

        List<Fechamento> centralEAgenciaSelecionadaList = filtrarPorCentralEAgencia(fechamentos);
        // Entradas
        List<FechamentoValorDia> fechamentoValorDiaList = agregarEntradasPorData(centralEAgenciaSelecionadaList);
        List<FechamentoValorSemanaMes> fechamentoValorSemanaMesList = agregarEntradasPelaSemana(fechamentoValorDiaList);
    }

    private static List<Fechamento> filtrarPorCentralEAgencia(List<Fechamento> fechamentos) {
        return fechamentos
                .stream()
                .filter(fechamento -> "NORTE".equals(fechamento.getCentral()) && "0418".equals(fechamento.getAgencia()))
                .collect(Collectors.toList());
    }

    private static List<FechamentoValorDia> agregarEntradasPorData(List<Fechamento> fechamentos) {
        // Usando um HashMap para somar os valores por data
        Map<LocalDate, BigDecimal> mapaSomado = new HashMap<>();

        for (Fechamento fechamento : fechamentos) {
            mapaSomado.merge(fechamento.getData(), fechamento.getEntradaDinheiro(), BigDecimal::add);
        }

        // Transformando o mapa em uma lista de fechamentos
        return mapaSomado.entrySet().stream()
                .map(entry -> new FechamentoValorDia(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

    private static List<FechamentoValorSemanaMes> agregarEntradasPelaSemana(List<FechamentoValorDia> fechamentoValorDiaList) {
        // Agrupa os valores pelo numero da semana no mês
        Map<Integer, Map<Integer, BigDecimal>> groupedExpenses =
                fechamentoValorDiaList
                        .stream()
                        .collect(Collectors.groupingBy(
                                linha -> buscarNumeroDaSemanaNoMes(linha.getData()), // número da semana no mês
                                Collectors.groupingBy(
                                        linha -> linha.getData().getMonthValue(), // mês
                                        Collectors.reducing(BigDecimal.ZERO, FechamentoValorDia::getValor, BigDecimal::add) // soma dos valores
                                )
                        ));

        // Mapeia o agrupamento para uma lista de SemanaMes
        return groupedExpenses
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(innerEntry -> new FechamentoValorSemanaMes(entry.getKey(), innerEntry.getKey(), innerEntry.getValue())))
                .collect(Collectors.toList());
    }

    private static int buscarNumeroDaSemanaNoMes(LocalDate data) {
        // Foi definido que o inicio da semana seria em um domingo e o final da semana um sábado
        WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);

        return data.get(weekFields.weekOfMonth());
    }

}
