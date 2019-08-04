package pooja.com;

import java.util.ArrayList;

public class Month {
    private int days;
    private int year;
    private String monthName;
    private ArrayList<Holiday> holidays;


    public Month(int year, String monthName) {
        this.year = year;
        this.monthName = monthName;

    }


    public int getDays() {
        return days;
    }


    public String getMonthName() {
        return monthName;
    }

    public void setEvent (int day, String eventText) {
        Holiday temp = new Holiday(day, eventText);
        holidays.add(temp);
    }

    public void deleteEvent(int day, String eventText){
        for(int i = 0 ; i < holidays.size(); i++){
            if (holidays.get(i).getDate() == day && holidays.get(i).getHolidayName().equals(eventText)) {
                holidays.remove(i);
            }
        }
    }

    //Method to create holiday objects and setting the month days

public void create(){
    if(this.monthName.equals("Jan")||this.monthName.equals("Mar")||this.monthName.equals("May")||this.monthName.equals("July")
            ||this.monthName.equals("Aug")||this.monthName.equals("Oct") ||this.monthName.equals("Dec")){
        this.days = 31;
        holidays = Holiday.getHoliday(this.monthName);

    }else if(this.monthName.equals("Apr")||this.monthName.equals("Jun")||this.monthName.equals("Sep")||this.monthName.equals("Nov")){
        this.days = 30;
        holidays =Holiday.getHoliday(this.monthName);
    }else if(((this.year%4)!=0) && (this.monthName.equals("Feb"))){
        this.days= 28;
        holidays =Holiday.getHoliday(this.monthName);
    }else if(((this.year%4) == 0)&& (this.monthName.equals("Feb"))){
        this.days = 29;
        holidays =Holiday.getHoliday(this.monthName);
    }
}

//method to check the holidays in a month to print in the calendar
public String checkHoliday(int day){
    String holiday = "";
        for(int i = 0; i < holidays.size(); i++){
            if (day == holidays.get(i).getDate()){
                 holiday += holidays.get(i).getHolidayName() + "\n";
            }
        }
        return holiday;
}
}
