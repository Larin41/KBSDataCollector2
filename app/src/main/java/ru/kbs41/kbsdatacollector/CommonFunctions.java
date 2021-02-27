package ru.kbs41.kbsdatacollector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonFunctions {

    public static String getDateRussianFormat(Date sourceDate) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", new Locale("ru")).format(sourceDate);
    }

}
