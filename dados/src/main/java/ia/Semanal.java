package ia;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Semanal {
    public static void main(String[] args) {

    }

    public void mes(int mes) {
        // Teste do dia da semanha
//        LocalDate dia = LocalDate.of(2024, 8, 1);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 2);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 3);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 4);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 5);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 6);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 7);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 8);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 9);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 10);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));
//        dia = LocalDate.of(2024, 8, 11);
//        System.out.println("Dia: " + dia.toString() + " - " + CalculaSemanaMes.buscarSemanaDoMes(dia));

        // Massa para saber se esta certo

//        List<Linha> linhas = new ArrayList<>();
//        linhas.add(Linha.builder()
//                .coop("0704")
//                .ua("0008")
//                .data(LocalDate.of(2024, 7, 10))
//                .entrada_dinheiro(new BigDecimal("30"))
//                .build());
//        linhas.add(Linha.builder()
//                .coop("0704")
//                .ua("0008")
//                .data(LocalDate.of(2024, 8, 1))
//                .entrada_dinheiro(new BigDecimal("5"))
//                .build());
//        linhas.add(Linha.builder()
//                .coop("0704")
//                .ua("0008")
//                .data(LocalDate.of(2024, 8, 10))
//                .entrada_dinheiro(new BigDecimal("25"))
//                .build());
//        linhas.add(Linha.builder()
//                .coop("0704")
//                .ua("0008")
//                .data(LocalDate.of(2024, 8, 10))
//                .entrada_dinheiro(new BigDecimal("10"))
//                .build());

        List<Linha> linhas = File.lerArquivo("gn");
        System.out.println("Total: " + linhas.size());

        // Filtro do ano
        List<Linha> l2021 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008")) && (linha.getData().getYear() == 2021 && linha.getData().getMonthValue() == mes))
                .collect(Collectors.toList());
        System.out.println("Total 2021: " + l2021.size());

        List<Linha> l2022 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008")) && (linha.getData().getYear() == 2022 && linha.getData().getMonthValue() == mes))
                .collect(Collectors.toList());
        System.out.println("Total 2022: " + l2022.size());

        List<Linha> l2023 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008")) && (linha.getData().getYear() == 2023 && linha.getData().getMonthValue() == mes))
                .collect(Collectors.toList());
        System.out.println("Total 2023: " + l2023.size());

        List<SemanaMes> semanaMesEntrada2021 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2021);
        System.out.println("Total semanaMesEntrada2021: " + semanaMesEntrada2021.size());
        List<SemanaMes> semanaMesEntrada2022 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2022);
        System.out.println("Total semanaMesEntrada2022: " + semanaMesEntrada2021.size());
        List<SemanaMes> semanaMesEntrada2023 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2023);
        System.out.println("Total semanaMesEntrada2023: " + semanaMesEntrada2021.size());

        Map<Integer, BigDecimal[]> entradas = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            BigDecimal[] valoresEntrada = entradas.getOrDefault(i, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO});

            if (i < semanaMesEntrada2021.size()) {
                valoresEntrada[0] = valoresEntrada[0].add(semanaMesEntrada2021.get(i).getValor());
            }

            if (i < semanaMesEntrada2022.size()) {
                valoresEntrada[1] = valoresEntrada[1].add(semanaMesEntrada2022.get(i).getValor());
            }

            if (i < semanaMesEntrada2023.size()) {
                valoresEntrada[2] = valoresEntrada[2].add(semanaMesEntrada2023.get(i).getValor());
            }
            entradas.put(i, valoresEntrada);
        }

        System.out.println("Gravando entradas");

        String mesString = String.valueOf(mes);
        if (mes < 10) {
            mesString = "0" + mes;
        }
//        exportarDadosConsolidados(entradas, "entradas_semanal_" + mes, mesString);

        // Saidas
        List<SemanaMes> semanaMesSaida2021 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2021);
        System.out.println("Total semanaMesSaida2021: " + semanaMesSaida2021.size());
        List<SemanaMes> semanaMesSaida2022 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2022);
        System.out.println("Total semanaMesSaida2022: " + semanaMesSaida2022.size());
        List<SemanaMes> semanaMesSaida2023 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2023);
        System.out.println("Total semanaMesSaida2023: " + semanaMesSaida2023.size());

        Map<Integer, BigDecimal[]> saidas = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            BigDecimal[] valoresSaida = saidas.getOrDefault(i, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO});

            if (i < semanaMesSaida2021.size()) {
                valoresSaida[0] = valoresSaida[0].add(semanaMesSaida2021.get(i).getValor());
            }

            if (i < semanaMesSaida2022.size()) {
                valoresSaida[1] = valoresSaida[1].add(semanaMesSaida2022.get(i).getValor());
            }

            if (i < semanaMesSaida2023.size()) {
                valoresSaida[2] = valoresSaida[2].add(semanaMesSaida2023.get(i).getValor());
            }
            saidas.put(i, valoresSaida);
        }

        System.out.println("Gravando saidas");

        exportarDadosConsolidados(saidas, "saidas_semanal_" + mes, mesString);
    }

    public static void exportarDadosConsolidados(Map<Integer, BigDecimal[]> dados, String fileName, String mes) {
        List<LinhaTodosAnos> linhaTodosAnos = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal[]> entry : dados.entrySet()) {
            Integer keyDiaMes = entry.getKey();
            BigDecimal[] valores = entry.getValue();
            // Converte LocalDate para Date

            linhaTodosAnos.add(LinhaTodosAnos.builder()
                    .dia(String.valueOf(keyDiaMes + 1))
                    .valor2021(valores[0])
                    .valor2022(valores[1])
                    .valor2023(valores[2])
                    .build());
        }

        salvarLinha(linhaTodosAnos, "dataset/output/" + fileName + ".csv", mes);
    }

    public static void salvarLinha(List<LinhaTodosAnos> linhas, String fileName, String mes) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            String[] cabecalho = {"semana-mes", "v2021", "v2022", "v2023"};
            writer.writeNext(cabecalho);

            for (LinhaTodosAnos movimento_diario : linhas) {
                String[] linha =
                        {
                                movimento_diario.getDia() + "-" + mes,
                                movimento_diario.getValor2021().toString(),
                                movimento_diario.getValor2022().toString(),
                                movimento_diario.getValor2023().toString()
                        };

                writer.writeNext(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
