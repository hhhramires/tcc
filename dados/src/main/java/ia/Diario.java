package ia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Diario {
    public static void main(String[] args) {
        List<Linha> linhas = File.lerArquivo("gn");
        System.out.println("Total: " + linhas.size());

        List<Linha> agenciaEscolhida2021E2022E2023 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008") && linha.getData().getMonthValue() == 12) &&
                        (linha.getData().getYear() == 2021 || linha.getData().getYear() == 2022 || linha.getData().getYear() == 2023))
                .collect(Collectors.toList());
        System.out.println("Total de 2021 a 2023: " + agenciaEscolhida2021E2022E2023.size());

        // unifica entrada e saida por data
        Map<LocalDate, BigDecimal[]> entradas = new HashMap<>();
        Map<LocalDate, BigDecimal[]> saidas = new HashMap<>();

        for (Linha l : agenciaEscolhida2021E2022E2023) {
            LocalDate data = l.getData();
            Integer keyDiaMes = data.getDayOfMonth();

            BigDecimal[] valoresEntrada = entradas.getOrDefault(data, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO});
            BigDecimal[] valoresSaida = saidas.getOrDefault(data, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO});

            if (l.getData().getYear() == 2021) {
                valoresEntrada[0] = valoresEntrada[0].add(l.getEntradaDinheiro());
                valoresSaida[0] = valoresSaida[0].add(l.getSaidaDinheiro());
            } else if (l.getData().getYear() == 2022) {
                valoresEntrada[1] = valoresEntrada[1].add(l.getEntradaDinheiro());
                valoresSaida[1] = valoresSaida[1].add(l.getSaidaDinheiro());
            } else if (l.getData().getYear() == 2023) {
                valoresEntrada[2] = valoresEntrada[2].add(l.getEntradaDinheiro());
                valoresSaida[2] = valoresSaida[2].add(l.getSaidaDinheiro());
            }

            entradas.put(data, valoresEntrada);
            saidas.put(data, valoresSaida);
        }

        System.out.println("Gravando entradas");
        exportarDadosConsolidados(entradas, "Entradas");
        System.out.println("Gravando saidas");
        exportarDadosConsolidados(saidas, "Saidas");
    }

    public static void exportarDadosConsolidados(Map<LocalDate, BigDecimal[]> dados, String fileName) {
        List<LinhaTodosAnos> linhaTodosAnos = new ArrayList<>();
        for (Map.Entry<LocalDate, BigDecimal[]> entry : dados.entrySet()) {
            LocalDate keyDiaMes = entry.getKey();
            BigDecimal[] valores = entry.getValue();
            // Converte LocalDate para Date

            linhaTodosAnos.add(LinhaTodosAnos.builder()
                    .dia(String.valueOf(keyDiaMes))
                    .valor2021(valores[0])
                    .valor2022(valores[1])
                    .valor2023(valores[2])
                    .build());
        }

        File.salvarLinha(linhaTodosAnos, "dataset/output/" + fileName + ".csv");
    }

}