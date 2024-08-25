package ia.datavalor;

import ia.File;
import ia.Linha;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MassaIADataValor {
    public static void main(String[] args) {
        List<Linha> linhas = File.lerArquivo("gn");
        System.out.println("Total: " + linhas.size());

        List<DataValorSaida> semanaMesTreinoEntrada = new ArrayList<>();
        List<DataValorSaida> semanaMesTreinoSaida = new ArrayList<>();
        List<DataValorSaida> semanaMesValidacaoEntrada = new ArrayList<>();
        List<DataValorSaida> semanaMesValidacaoSaida = new ArrayList<>();
        List<DataValorSaida> semanaMesTesteEntrada = new ArrayList<>();
        List<DataValorSaida> semanaMesTesteSaida = new ArrayList<>();

        mes(linhas, 1, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 2, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 3, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 4, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 5, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 6, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 7, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 8, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 9, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 10, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 11, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);
        mes(linhas, 12, semanaMesTreinoEntrada, semanaMesTreinoSaida, semanaMesValidacaoEntrada, semanaMesValidacaoSaida, semanaMesTesteEntrada, semanaMesTesteSaida);

        salvarLinha(semanaMesTreinoEntrada, "entradas\\2021_2022");
        salvarLinha(semanaMesTreinoSaida, "saidas\\2021_2022");

        salvarLinha(semanaMesValidacaoEntrada, "entradas\\2023");
        salvarLinha(semanaMesValidacaoSaida, "saidas\\2023");

        salvarLinha(semanaMesTesteEntrada, "entradas\\2024");
        salvarLinha(semanaMesTesteSaida, "saidas\\2024");
    }

    public static void mes(List<Linha> linhas, int mes,
                           List<DataValorSaida> semanaMesTreinoEntrada,
                           List<DataValorSaida> semanaMesTreinoSaida,
                           List<DataValorSaida> semanaMesValidacaoEntrada,
                           List<DataValorSaida> semanaMesValidacaoSaida,
                           List<DataValorSaida> semanaMesTesteEntrada,
                           List<DataValorSaida> semanaMesTesteSaida
    ) {
        List<Linha> l2021 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008")) && (linha.getData().getYear() == 2021 && linha.getData().getMonthValue() == mes))
                .collect(Collectors.toList());

        List<Linha> l2022 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008")) && (linha.getData().getYear() == 2022 && linha.getData().getMonthValue() == mes))
                .collect(Collectors.toList());

        List<Linha> l2023 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008")) && (linha.getData().getYear() == 2023 && linha.getData().getMonthValue() == mes))
                .collect(Collectors.toList());

        List<Linha> l2024 = linhas
                .stream()
                .filter(linha -> (linha.getCoop().equals("0704") && linha.getUa().equals("0008")) && (linha.getData().getYear() == 2024 && linha.getData().getMonthValue() == mes))
                .collect(Collectors.toList());

        // Entradas
        for (Linha l : l2021) {
            semanaMesTreinoEntrada.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getEntradaDinheiro())
                            .build());
        }

        for (Linha l : l2022) {
            semanaMesTreinoEntrada.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getEntradaDinheiro())
                            .build());
        }

        for (Linha l : l2023) {
            semanaMesValidacaoEntrada.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getEntradaDinheiro())
                            .build());
        }

        for (Linha l : l2024) {
            semanaMesTesteEntrada.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getEntradaDinheiro())
                            .build());
        }

        // Saidas
        for (Linha l : l2021) {
            semanaMesTreinoSaida.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getSaidaDinheiro())
                            .build());
        }

        for (Linha l : l2022) {
            semanaMesTreinoSaida.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getSaidaDinheiro())
                            .build());
        }

        for (Linha l : l2023) {
            semanaMesValidacaoSaida.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getSaidaDinheiro())
                            .build());
        }

        for (Linha l : l2024) {
            semanaMesTesteSaida.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getSaidaDinheiro())
                            .build());
        }

    }

    public static void salvarLinha(List<DataValorSaida> linhas, String fileName) {
        fileName = "C:\\Users\\henrique_ramires\\OneDrive - Sicredi\\Desktop\\tcc\\ia\\dataset\\datavalor\\" + fileName + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("date,value");
            writer.newLine();

            for (DataValorSaida movimento_diario : linhas) {
                writer.write(movimento_diario.getData() + "," + movimento_diario.getValor().toBigInteger());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
