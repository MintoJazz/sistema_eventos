package persistencias;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Formatadores {
    public static String data2String(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // public static String Timestamp2StringData(Timestamp timestamp) {
    //     SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    //     return formatador.format(timestamp);
    // }
}