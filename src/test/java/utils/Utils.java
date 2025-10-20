package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public String generarNormbre(String nombre) {

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        String nombreFinal = nombre + "_" + ahora.format(formato);

        return nombreFinal;
    }
}
