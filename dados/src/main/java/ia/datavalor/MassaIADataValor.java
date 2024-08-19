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

        List<DataValorSaida> semanaMesTreino = new ArrayList<>();
        List<DataValorSaida> semanaMesTeste = new ArrayList<>();
        System.out.println("1");
        mes(linhas, 1, semanaMesTreino, semanaMesTeste);
        System.out.println("2");
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

        salvarLinha(semanaMesTreino, "train_data_valor_2021_2022");
        salvarLinha(semanaMesTeste, "test_data_valor_2023");

//        salvarLinha(semanaMesTreino, "train_2022_2023");
//        salvarLinha(semanaMesTeste, "test_2024");
    }

    public static void mes(List<Linha> linhas, int mes, List<DataValorSaida> semanaMesTreino, List<DataValorSaida> semanaMesTeste) {
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

        System.out.println("for 21");
        for (Linha l : l2021) {
            semanaMesTreino.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getEntrada_dinheiro())
                            .build());
        }

        System.out.println("for 22");
        for (Linha l : l2022) {
            semanaMesTreino.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getEntrada_dinheiro())
                            .build());
        }

        for (Linha l : l2023) {
            semanaMesTeste.add(
                    DataValorSaida
                            .builder()
                            .data(l.getData())
                            .valor(l.getEntrada_dinheiro())
                            .build());
        }

    }

    public static void salvarLinha(List<DataValorSaida> linhas, String fileName) {
        fileName = "C:\\Users\\henrique_ramires\\OneDrive - Sicredi\\Desktop\\tcc\\ia\\dataset\\real\\" + fileName + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("date;value");
            writer.newLine();

            for (DataValorSaida movimento_diario : linhas) {
                writer.write(movimento_diario.getData() + ";" + movimento_diario.getValor().toBigInteger());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
