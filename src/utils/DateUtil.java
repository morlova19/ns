package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class for working with date.
 */
public class DateUtil {
    /**
     * Date format.
     */
    private static DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    /**
     * Parses string into date.
     * @param date string with date.
     * @return date.
     */
    public static Date parse(String date)
    {
        df.setLenient(false);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }

    }
    /**
     * Formats date into string.
     * @param date date.
     * @return string with date.
     */
    public static String format(Date date)
    {
        df.setLenient(false);
        return df.format(date);
    }
    /**
     * Validates the entered date.
     * @param date the entered date.
     * @return true if the date is correct, else - false.
     */
    public static boolean isCorrect(Date date)
    {
        long delta = date.getTime() - Calendar.getInstance().getTimeInMillis();
        return delta > 0;
    }
}

