package ia;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static LocalDate obterProximoDiaUtil(LocalDate data) {
        ZoneId zoneBrasil = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime dataHoraBrasil = ZonedDateTime.of(data.getYear(), data.getMonthValue(), data.getDayOfMonth(), 0, 0, 0, 0, zoneBrasil);
        LocalDate proximoDiaUtil = dataHoraBrasil.toLocalDate();

        while (isFeriadoNacional(proximoDiaUtil) || proximoDiaUtil.getDayOfWeek() == DayOfWeek.SATURDAY || proximoDiaUtil.getDayOfWeek() == DayOfWeek.SUNDAY) {
            proximoDiaUtil = proximoDiaUtil.plusDays(1);
        }

        return proximoDiaUtil;
    }

    private static boolean isFeriadoNacional(LocalDate data) {
        List<LocalDate> feriados = getFeriados();

        for (LocalDate feriado : feriados) {
            if (feriado.getMonthValue() == data.getMonthValue() && feriado.getDayOfMonth() == data.getDayOfMonth()) {
                return true;
            }
        }

        return false;
    }

    private static List<LocalDate> getFeriados() {
        List<LocalDate> feriados = new ArrayList<>();

        feriados.add(LocalDate.of(2021, 1, 1)); // Ano Novo
        feriados.add(LocalDate.of(2021, 2, 15)); // Carnaval
        feriados.add(LocalDate.of(2021, 2, 16)); // Carnaval
        feriados.add(LocalDate.of(2021, 4, 2)); // Paixão de Cristo
        feriados.add(LocalDate.of(2021, 4, 21)); // Tiradentes
        feriados.add(LocalDate.of(2021, 5, 1)); // Dia do Trabalho
        feriados.add(LocalDate.of(2021, 6, 3)); //Corpus Christi
        feriados.add(LocalDate.of(2021, 9, 7)); // Independência do Brasil
        feriados.add(LocalDate.of(2021, 10, 12)); // Nossa Senhora Aparecida
        feriados.add(LocalDate.of(2021, 11, 2)); // Finados
        feriados.add(LocalDate.of(2021, 11, 15)); // Proclamação da República
        feriados.add(LocalDate.of(2021, 12, 25)); // Natal

        feriados.add(LocalDate.of(2022, 1, 1)); // Ano Novo
        feriados.add(LocalDate.of(2022, 2, 28)); // Carnaval
        feriados.add(LocalDate.of(2022, 2, 1)); // Carnaval
        feriados.add(LocalDate.of(2022, 4, 15)); // Paixão de Cristo
        feriados.add(LocalDate.of(2022, 4, 21)); // Tiradentes
        feriados.add(LocalDate.of(2022, 5, 1)); // Dia do Trabalho
        feriados.add(LocalDate.of(2022, 6, 16)); //Corpus Christi
        feriados.add(LocalDate.of(2022, 9, 7)); // Independência do Brasil
        feriados.add(LocalDate.of(2022, 10, 12)); // Nossa Senhora Aparecida
        feriados.add(LocalDate.of(2022, 11, 2)); // Finados
        feriados.add(LocalDate.of(2022, 11, 15)); // Proclamação da República
        feriados.add(LocalDate.of(2022, 12, 25)); // Natal

        feriados.add(LocalDate.of(2023, 1, 1)); // Ano Novo
        feriados.add(LocalDate.of(2023, 2, 20)); // Carnaval
        feriados.add(LocalDate.of(2023, 2, 21)); // Carnaval
        feriados.add(LocalDate.of(2023, 4, 7)); // Paixão de Cristo
        feriados.add(LocalDate.of(2023, 4, 21)); // Tiradentes
        feriados.add(LocalDate.of(2023, 5, 1)); // Dia do Trabalho
        feriados.add(LocalDate.of(2023, 6, 8)); //Corpus Christi
        feriados.add(LocalDate.of(2023, 9, 7)); // Independência do Brasil
        feriados.add(LocalDate.of(2023, 10, 12)); // Nossa Senhora Aparecida
        feriados.add(LocalDate.of(2023, 11, 2)); // Finados
        feriados.add(LocalDate.of(2023, 11, 15)); // Proclamação da República
        feriados.add(LocalDate.of(2023, 12, 25)); // Natal

        return feriados;
    }

}
