package account.utils;

import account.exception.InvalidMonthException;

import java.time.LocalDate;

public final class DateUtil {

    public static LocalDate stringToDate(String date){
        String[] splitDate = date.split("-");
        if(Integer.parseInt(splitDate[0]) > 12 || Integer.parseInt(splitDate[0]) < 1 ){
            throw new InvalidMonthException();
        }
        return LocalDate.of(Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[0]),1);
    }
}
