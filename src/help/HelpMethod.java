package help;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HelpMethod {

    private int day, month, year;
    private List<Integer> MonthLength;

    public HelpMethod() {
        LocalDate date = LocalDate.now();
        day = date.getDayOfMonth();
        month = date.getMonthValue();
        year = date.getYear();

        MonthLength = new ArrayList<>(List.of(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31));
        if(checkYearIsLeep())
            MonthLength.set(1, 29); 
    }

    public int getTotalDays(int month) {        // 1 base indexing
        return MonthLength.get(month-1);
    }

    public boolean checkYearIsLeep() {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
            return true;
        return false;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
