import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        List<Fechamento> fechamentos = File.lerArquivo("gn");

        List<Fechamento> centralEAgenciaSelecionadaList = filtrarPorCentralEAgencia(fechamentos);

        List<Fechamento> centralEAgenciaSelecionadaList2021_01 = filtrarAnoMes(centralEAgenciaSelecionadaList, 2021, 1);
        centralEAgenciaSelecionadaList2021_01.sort(Comparator.comparing(Fechamento::getData));
        List<Fechamento> centralEAgenciaSelecionadaList2022_01 = filtrarAnoMes(centralEAgenciaSelecionadaList, 2022, 1);
        centralEAgenciaSelecionadaList2022_01.sort(Comparator.comparing(Fechamento::getData));
        List<Fechamento> centralEAgenciaSelecionadaList2023_01 = filtrarAnoMes(centralEAgenciaSelecionadaList, 2023, 1);
        centralEAgenciaSelecionadaList2023_01.sort(Comparator.comparing(Fechamento::getData));


        // Entradas
        List<FechamentoValorDia> fechamentoValorDiaList = agregarEntradasPorData(centralEAgenciaSelecionadaList2021_01);
        List<FechamentoValorSemanaMes> fechamentoValorSemanaMesList = agregarEntradasPelaSemana(fechamentoValorDiaList);
    }

    private static void agregarPorDataAtributos2021E2022E2023(){
        Map<LocalDate, BigDecimal[]> entradas = new HashMap<>();

    }

    private static List<Fechamento> filtrarAnoMes(List<Fechamento> fechamentos, int ano, int mes) {
        return fechamentos
                .stream()
                .filter(linha -> linha.getData().getYear() == ano && linha.getData().getMonthValue() == mes)
                .collect(Collectors.toList());
    }

    private static List<Fechamento> filtrarPorCentralEAgencia(List<Fechamento> fechamentos) {
        return fechamentos
                .stream()
                .filter(fechamento -> "0704".equals(fechamento.getCentral()) && "0008".equals(fechamento.getAgencia()))
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
