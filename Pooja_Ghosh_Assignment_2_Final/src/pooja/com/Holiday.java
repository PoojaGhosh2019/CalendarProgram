package pooja.com;

import java.util.ArrayList;

public class Holiday {
    private int date;
    private String holidayName;

    public Holiday(int date, String holidayName) {
        this.date = date;
        this.holidayName = holidayName;
    }

//getter of holiday
    public static ArrayList<Holiday> getHoliday(String month) {
       ArrayList<Holiday> holidays = new ArrayList<>();
        if (month.equals("Jan")) {
            holidays.add(new Holiday(1, "New Year’s Day"));
            holidays.add(new Holiday(21, "Birthday of Martin Luther King, Jr."));
        }
        if (month.equals("Feb")) {
            holidays.add(new Holiday(18, "Washington’s Birthday"));
        }
        if (month.equals("May")) {
            holidays.add(new Holiday(27, "Memorial Day"));
        }
        if (month.equals("July")) {
            holidays.add(new Holiday(4, "Independence Day"));
        }
        if (month.equals("Sept")) {
            holidays.add(new Holiday(2, "Labor Day"));
        }
        if (month.equals("Oct") ) {
            holidays.add(new Holiday(14, "Columbus Day"));
        }
        if (month.equals("Nov")) {
            holidays.add(new Holiday(11, "Veterans Day"));
            holidays.add(new Holiday(28, "Thanksgiving Day"));
        }
        if (month.equals("Dec")) {
            holidays.add(new Holiday(25, "Christmas Day"));
        }
        return holidays;
    }


    public int getDate() {
        return date;
    }

    public String getHolidayName() {
        return holidayName;
    }
}


