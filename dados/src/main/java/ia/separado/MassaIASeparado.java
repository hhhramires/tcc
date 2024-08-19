package ia.separado;

import ia.Linha;
import ia.CalculaSemanaMes;
import ia.File;
import ia.SemanaMes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MassaIASeparado {
    public static void main(String[] args) {
        List<Linha> linhas = File.lerArquivo("gn");
        System.out.println("Total: " + linhas.size());

        List<SemanaMesValor> semanaMesTreino = new ArrayList<>();
        List<SemanaMesValor> semanaMesTeste = new ArrayList<>();
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

        salvarLinha(semanaMesTreino, "train_2022_2023");
        salvarLinha(semanaMesTeste, "test_2024");

//        salvarLinha(semanaMesTreino, "train_2021_2022");
//        salvarLinha(semanaMesTeste, "test_2023");
    }

    public static void mes(List<Linha> linhas, int mes, List<SemanaMesValor> semanaMesTreino, List<SemanaMesValor> semanaMesTeste) {
        String mesString = String.valueOf(mes);
        if (mes < 10) {
            mesString = "0" + mes;
        }

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

        List<SemanaMes> semanaMesEntrada2021 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2021);
        List<SemanaMes> semanaMesEntrada2022 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2022);
        List<SemanaMes> semanaMesEntrada2023 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2023);
        List<SemanaMes> semanaMesEntrada2024 = CalculaSemanaMes.calculateEntradaDeDinheiroPorSemanaMes(l2024);


        for (int i = 0; i < 6; i++) {
//            if (i < semanaMesEntrada2021.size()) {
//                semanaMesTreino.add(
//                        SemanaMesValor.builder()
//                                .semana((i + 1))
//                                .mes(mesString)
//                                .valor(semanaMesEntrada2021.get(i).getValor())
//                                .build());
//            }
            if (i < semanaMesEntrada2022.size()) {
                semanaMesTreino.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesEntrada2022.get(i).getValor())
                                .build());
            }
            if (i < semanaMesEntrada2023.size()) {
                semanaMesTreino.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesEntrada2023.get(i).getValor())
                                .build());
            }
            if (i < semanaMesEntrada2024.size()) {
                semanaMesTeste.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesEntrada2024.get(i).getValor())
                                .build());
            }
        }

        // Saidas
        List<SemanaMes> semanaMesSaida2021 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2021);
        List<SemanaMes> semanaMesSaida2022 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2022);
        List<SemanaMes> semanaMesSaida2023 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2023);

        for (int i = 0; i < 6; i++) {
            if (i < semanaMesSaida2021.size()) {

            }
            if (i < semanaMesSaida2022.size()) {

            }

            if (i < semanaMesSaida2023.size()) {

            }
        }
    }

    public static void salvarLinha(List<SemanaMesValor> linhas, String fileName) {
        fileName = "C:\\Users\\henrique_ramires\\OneDrive - Sicredi\\Desktop\\tcc\\ia\\dataset\\separado\\" + fileName + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("week_of_month;month;value");
            writer.newLine();

            for (SemanaMesValor movimento_diario : linhas) {
                writer.write(movimento_diario.getSemana() + ";" + movimento_diario.getMes() + ";" + movimento_diario.getValor().toBigInteger());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
