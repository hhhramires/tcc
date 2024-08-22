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

    public static List<Fechamento> lerArquivo(String fileName) {
        List<Fechamento> fechamentos = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("dataset/input/" + fileName + ".csv"))) {
            String[] linha;
            reader.readNext();

            while ((linha = reader.readNext()) != null) {
                fechamentos.add(
                        Fechamento.builder()
                                .central(linha[0].split("\\.")[1])
                                .agencia(linha[0].split("\\.")[2])
                                .terminal(linha[0].split("\\.")[3])
                                .data(LocalDate.parse(linha[1]))
                                .saldoInicial(new BigDecimal(linha[2]))
                                .saldoFinal(new BigDecimal(linha[3]))
                                .entradaDinheiro(new BigDecimal(linha[4]))
                                .saidaDinheiro(new BigDecimal(linha[5]))
                                .build());
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return fechamentos;
    }

    public static void salvar(List<Fechamento> fechamentos, String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            String[] cabecalho = {"id", "transaction_date", "beginning_balance", "ending_balance", "cash_in", "cash_out", "ship_in", "ship_out, _meta_processing_time, _meta_processing_date"};
            writer.writeNext(cabecalho);

            for (Fechamento movimento_diario : fechamentos) {
                String[] linha =
                        {
                                (movimento_diario.getCentral() + '.' + movimento_diario.getAgencia() + '.' + movimento_diario.getTerminal()),
                                movimento_diario.getData().toString(),
                                movimento_diario.getSaldoInicial().toString(),
                                movimento_diario.getSaldoFinal().toString(),
                                movimento_diario.getEntradaDinheiro().toString(),
                                movimento_diario.getSaidaDinheiro().toString(),
                                "",
                                ""
                        };

                writer.writeNext(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}