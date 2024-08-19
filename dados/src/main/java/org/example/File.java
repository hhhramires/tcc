package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class File {

//    id (sistema, coop, ua, id_terminal)
//    transaction_date      - Data
//    beginning_balance     - Saldo inicial
//    ending_balance        - Saldo final
//    cash_in               - Depositos
//    cash_out              - Saques
//    ship_in               - Suprimentos
//    ship_out              - Recolhimento
//    _meta_processing_time - Nao usado
//    _meta_processing_date - Nao usado

    public static List<Linha> lerArquivo(String fileName) {
        List<Linha> linhas = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("dataset/input/" + fileName + ".csv"))) {
            String[] linha;
            reader.readNext();

            while ((linha = reader.readNext()) != null) {
                linhas.add(
                        Linha.builder()
                                .sistema(linha[0].split("\\.")[0])
                                .coop(linha[0].split("\\.")[1])
                                .ua(linha[0].split("\\.")[2])
                                .id_terminal(linha[0].split("\\.")[3])
                                .data(LocalDate.parse(linha[1]))
                                .saldo_inicial(new BigDecimal(linha[2]))
                                .saldo_final(new BigDecimal(linha[3]))
                                .entrada_dinheiro(new BigDecimal(linha[4]))
                                .saida_dinheiro(new BigDecimal(linha[5]))
                                .suprimento(new BigDecimal(linha[6]))
                                .recolhimento(new BigDecimal(linha[7]))
                                .build());
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return linhas;
    }

    public static void salvar(List<Linha> linhas, String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            String[] cabecalho = {"id", "transaction_date", "beginning_balance", "ending_balance", "cash_in", "cash_out", "ship_in", "ship_out, _meta_processing_time, _meta_processing_date"};
            writer.writeNext(cabecalho);

            for (Linha movimento_diario : linhas) {
                String[] linha =
                        {
                                (movimento_diario.sistema + '.' + movimento_diario.coop + '.' + movimento_diario.ua + '.' + movimento_diario.id_terminal),
                                movimento_diario.data.toString(),
                                movimento_diario.saldo_inicial.toString(),
                                movimento_diario.saldo_final.toString(),
                                movimento_diario.entrada_dinheiro.toString(),
                                movimento_diario.saida_dinheiro.toString(),
                                movimento_diario.suprimento.toString(),
                                movimento_diario.recolhimento.toString(),
                                "",
                                ""
                        };

                writer.writeNext(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarLinha(List<LinhaTodosAnos> linhas, String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            String[] cabecalho = {"dia", "v2021", "v2022", "v2023"};
            writer.writeNext(cabecalho);

            for (LinhaTodosAnos movimento_diario : linhas) {
                String[] linha =
                        {
                                movimento_diario.getDia(),
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

    public static void salvarSemanaMes(List<SemanaMes> linhas, String fileName) {
        fileName = "dataset/output/" + fileName + ".csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
//            String[] cabecalho = {"dia", "v2021", "v2022", "v2023"};
            String[] cabecalho = {"semana", "entrada"};
            writer.writeNext(cabecalho);

            for (SemanaMes semanaMes : linhas) {
                String[] linha =
                        {
                                semanaMes.getSemana().toString(),
                                semanaMes.getValor().toString()
                        };

                writer.writeNext(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}