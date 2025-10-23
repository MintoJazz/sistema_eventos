package persistencias;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Formatadores {
    public static String data2String(Date data) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(data);
    }

    public static String Timestamp2StringData(Timestamp timestamp) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(timestamp);
    }
}