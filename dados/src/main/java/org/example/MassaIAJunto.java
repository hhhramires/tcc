package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MassaIAJunto {
    public static void main(String[] args) {
        List<Linha> linhas = File.lerArquivo("gn");
        System.out.println("Total: " + linhas.size());

        List<DataValor> semanaMesTreino = new ArrayList<>();
        List<DataValor> semanaMesTeste = new ArrayList<>();
        mes(linhas, 1, semanaMesTreino, semanaMesTeste);
        mes(linhas, 2, semanaMesTreino, semanaMesTeste);
        mes(linhas, 3, semanaMesTreino, semanaMesTeste);
        mes(linhas, 4, semanaMesTreino, semanaMesTeste);
        mes(linhas, 5, semanaMesTreino, semanaMesTeste);
        mes(linhas, 6, semanaMesTreino, semanaMesTeste);
        mes(linhas, 7, semanaMesTreino, semanaMesTeste);
        mes(linhas, 8, semanaMesTreino, semanaMesTeste);
        mes(linhas, 9, semanaMesTreino, semanaMesTeste);
        mes(linhas, 10, semanaMesTreino, semanaMesTeste);
        mes(linhas, 11, semanaMesTreino, semanaMesTeste);
        mes(linhas, 12, semanaMesTreino, semanaMesTeste);

        salvarLinha(semanaMesTreino, "train");
        salvarLinha(semanaMesTreino, "test");
    }

    public static void mes(List<Linha> linhas, int mes, List<DataValor> semanaMesTreino, List<DataValor> semanaMesTeste) {
        String mesString = String.valueOf(mes);
        if (mes < 10) {
            mesString = "0" + mes;
        }

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
        System.out.println("Total semanaMesEntrada2022: " + semanaMesEntrada2022.size());
        List<SemanaMes> semanaMesEntrada2023 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2023);
        System.out.println("Total semanaMesEntrada2023: " + semanaMesEntrada2023.size());

        for (int i = 0; i < 6; i++) {
            if (i < semanaMesEntrada2021.size()) {
                semanaMesTreino.add(DataValor.builder()
                        .data((i + 1) + mesString)
                        .valor(semanaMesEntrada2021.get(i).getValor())
                        .build());
            }
            if (i < semanaMesEntrada2022.size()) {
//                semanaMesTreino.add(DataValor.builder()
//                        .data((i + 1) + mesString)
//                        .valor(semanaMesEntrada2022.get(i).getValor())
//                        .build());
            }
            if (i < semanaMesEntrada2023.size()) {
                semanaMesTeste.add(DataValor.builder()
                        .data((i + 1) + mesString)
                        .valor(semanaMesEntrada2023.get(i).getValor())
                        .build());
            }
        }

        // Saidas
        List<SemanaMes> semanaMesSaida2021 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2021);
        System.out.println("Total semanaMesSaida2021: " + semanaMesSaida2021.size());
        List<SemanaMes> semanaMesSaida2022 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2022);
        System.out.println("Total semanaMesSaida2022: " + semanaMesSaida2022.size());
        List<SemanaMes> semanaMesSaida2023 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2023);
        System.out.println("Total semanaMesSaida2023: " + semanaMesSaida2023.size());

        for (int i = 0; i < 6; i++) {
            if (i < semanaMesSaida2021.size()) {

            }
            if (i < semanaMesSaida2022.size()) {

            }

            if (i < semanaMesSaida2023.size()) {

            }
        }
    }

    public static void salvarLinha(List<DataValor> linhas, String fileName) {
        fileName = "C:\\Users\\henrique_ramires\\OneDrive - Sicredi\\Desktop\\Tudo TCC II\\iapronta\\" + fileName + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("week_of_month;value");
            writer.newLine();

            for (DataValor movimento_diario : linhas) {
                writer.write(movimento_diario.getData() + ";" + movimento_diario.getValor().toBigInteger());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
