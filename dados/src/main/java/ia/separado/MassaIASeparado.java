package ia.separado;

import ia.CalculaSemanaMes;
import ia.File;
import ia.Linha;
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

        List<SemanaMesValor> semanaMesTreinoEntrada = new ArrayList<>();
        List<SemanaMesValor> semanaMesTreinoSaida = new ArrayList<>();
        List<SemanaMesValor> semanaMesValidacaoEntrada = new ArrayList<>();
        List<SemanaMesValor> semanaMesValidacaoSaida = new ArrayList<>();
        List<SemanaMesValor> semanaMesTesteEntrada = new ArrayList<>();
        List<SemanaMesValor> semanaMesTesteSaida = new ArrayList<>();

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
                           List<SemanaMesValor> semanaMesTreinoEntrada,
                           List<SemanaMesValor> semanaMesTreinoSaida,
                           List<SemanaMesValor> semanaMesValidacaoEntrada,
                           List<SemanaMesValor> semanaMesValidacaoSaida,
                           List<SemanaMesValor> semanaMesTesteEntrada,
                           List<SemanaMesValor> semanaMesTesteSaida
    ) {
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
            if (i < semanaMesEntrada2021.size()) {
                semanaMesTreinoEntrada.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesEntrada2021.get(i).getValor())
                                .build());
            }
            if (i < semanaMesEntrada2022.size()) {
                semanaMesTreinoEntrada.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesEntrada2022.get(i).getValor())
                                .build());
            }

            if (i < semanaMesEntrada2023.size()) {
                semanaMesValidacaoEntrada.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesEntrada2023.get(i).getValor())
                                .build());
            }

            if (i < semanaMesEntrada2024.size()) {
                semanaMesTesteEntrada.add(
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
        List<SemanaMes> semanaMesSaida2024 = CalculaSemanaMes.calculateSaidaDeDinheiroPorSemanaMes(l2024);

        for (int i = 0; i < 6; i++) {
            if (i < semanaMesSaida2021.size()) {
                semanaMesTreinoSaida.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesSaida2021.get(i).getValor())
                                .build());
            }

            if (i < semanaMesSaida2022.size()) {
                semanaMesTreinoSaida.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesSaida2022.get(i).getValor())
                                .build());
            }

            if (i < semanaMesSaida2023.size()) {
                semanaMesValidacaoSaida.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesSaida2023.get(i).getValor())
                                .build());
            }

            if (i < semanaMesSaida2024.size()) {
                semanaMesTesteSaida.add(
                        SemanaMesValor.builder()
                                .semana((i + 1))
                                .mes(mesString)
                                .valor(semanaMesSaida2024.get(i).getValor())
                                .build());
            }
        }
    }

    public static void salvarLinha(List<SemanaMesValor> linhas, String fileName) {
        fileName = "C:\\Users\\henrique_ramires\\OneDrive - Sicredi\\Desktop\\tcc\\ia\\dataset\\" + fileName + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("week_of_month,month,value");
            writer.newLine();

            for (SemanaMesValor movimento_diario : linhas) {
                writer.write(movimento_diario.getSemana() + "," + movimento_diario.getMes() + "," + movimento_diario.getValor().toBigInteger());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
