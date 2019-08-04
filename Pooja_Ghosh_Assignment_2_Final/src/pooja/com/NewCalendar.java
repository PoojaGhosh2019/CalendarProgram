package pooja.com;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewCalendar extends JFrame {

    private int year;
    private ArrayList<Month> months = new ArrayList<>();
    private String[] listOfMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

    //Declaring JComponents in the frame
    private JPanel maincalendarPanel = new JPanel(new BorderLayout());
    private JPanel customizeCalendarPanel = new JPanel(new BorderLayout());
    private JPanel totalCalendarPanel = new JPanel(new BorderLayout());
    private static JTextField eventField = new JTextField(15);
    private static JTextField eventDate = new JTextField(15);
    private JButton deleteEventButton = new JButton("Delete Event");
    private JTextArea historyTextArea = new JTextArea();
    private String[] color = {"white" , "red", "yellow", "black", "cyan"};
    private JComboBox colorBox = new JComboBox(color);
    ArrayList<String> evetAdded = new ArrayList<>();//just check

    //Constructor
    public NewCalendar(int year) {
        this.year = year;
    }

    //Method to create the month objects
    public ArrayList<Month> create() {
        for (int i = 0; i < listOfMonths.length; i++) {
            Month month = new Month(this.year, listOfMonths[i]);
            month.create();
            months.add(month);
        }
        return months;
    }

    public void showCalendar() {
        JSplitPane splitPane = new JSplitPane();

        //Splitting the pane into two parts
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(1000);
        splitPane.setLeftComponent(maincalendarPanel);
        splitPane.setRightComponent(customizeCalendarPanel);

        //Adding the image panel on top side of maincalendarPanel
        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setLayout(new BorderLayout());
        try {
            BufferedImage myPicture = ImageIO.read(new File("C:\\MS_Studies\\2nd_quarter_study_materials\\OOADP\\Assignments\\sc.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            Border border = LineBorder.createGrayLineBorder();
            picLabel.setBorder(border);
            imagePanel.add(picLabel, BorderLayout.WEST);
        } catch (IOException ex) {
            System.out.println("Image not found");
        }

        //Adding the year label in the image panel in center part
        JLabel yearLabel = new JLabel("2019" + "Calendar");
        imagePanel.add(yearLabel, BorderLayout.CENTER);

        //Adding the radio buttons on the imagePanel in right most side
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        ButtonGroup group = new ButtonGroup();
        JRadioButton btn1 = new JRadioButton("Month");
        JRadioButton btn2 = new JRadioButton("Week");
        JRadioButton btn3 = new JRadioButton("Day");
        btn1.setActionCommand("1");
        group.add(btn1);
        btn2.setActionCommand("2");
        group.add(btn2);
        btn3.setActionCommand("3");
        group.add(btn3);
        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        imagePanel.add(buttonPanel, BorderLayout.EAST);

        //different views of calendar action listener
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalCalendarPanel.removeAll();
                monthlyCalendar();
                totalCalendarPanel.revalidate();
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalCalendarPanel.removeAll();
                weeklyCalendar();
                totalCalendarPanel.revalidate();
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalCalendarPanel.removeAll();
                dailyCalendar();
                totalCalendarPanel.revalidate();
            }
        });


        //Customize Calendar Panel
        //Adding a blank topPanel to match imagePanel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.white);
        topPanel.setPreferredSize(new Dimension(40, 100));

        //Adding all the rest components on the bottom panel of customizeCalendarPanel
        JPanel bottomPanel = new JPanel(new BorderLayout());


        //Adding the label and text area in the north part of bottomPanel
        JPanel labelPanel1 = new JPanel(new BorderLayout());
        JLabel customizeCalendarLabel = new JLabel("Customize Calendar");
        customizeCalendarLabel.setPreferredSize(new Dimension(100, 50));
        customizeCalendarLabel.setHorizontalAlignment(JLabel.CENTER);
        customizeCalendarLabel.setVerticalAlignment(JLabel.CENTER);

        //Adding the innerLabelPanel1 in the labelPanel1
        JPanel innerLabelPanel1 = new JPanel();
        innerLabelPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label1 = new JLabel("Event Name");
        label1.setBorder(new EmptyBorder(15, 0, 25, 50));
        JLabel label2 = new JLabel("Event Date");
        label2.setBorder(new EmptyBorder(15, 0, 25, 50));
        innerLabelPanel1.add(label1);
        innerLabelPanel1.add(label2);

        //Adding the innerLabelPanel2 in the labelPanel1
        JPanel innerLabelPanel2 = new JPanel();
        innerLabelPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton addEventButton = new JButton("Add Event");
        addEventButton.setHorizontalAlignment(JButton.CENTER);
        addEventButton.setBorder(new EmptyBorder(15, 15, 15, 30));
        innerLabelPanel2.add(eventField);
        innerLabelPanel2.add(eventDate);
        innerLabelPanel2.add(addEventButton);

        //date format checking
        String pattern = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setLenient(false);
        String dateInput = simpleDateFormat.format(new Date());
        eventDate.setText(dateInput);

        //doubt working but what does ex.printStrackTrace does
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date date = simpleDateFormat.parse(eventDate.getText());
                    if ((simpleDateFormat.format(date).equals(eventDate.getText())) && (eventDate.getText().length() == pattern.length())) {
                        String selectedOption = group.getSelection().getActionCommand();
                        boolean eventValidation = eventAddedValidation(eventDate.getText(),eventField.getText());
                        if(eventValidation == false) {
                            System.out.println("Event already present");
                            JOptionPane.showMessageDialog(addEventButton,"Event Already added");
                        }else {
                            System.out.println("Event not present");
                            switch (selectedOption) {
                                case "1":
                                    setEvent();
                                    totalCalendarPanel.removeAll();
                                    monthlyCalendar();
                                    totalCalendarPanel.revalidate();
                                    historyTextArea.append(eventField.getText() + " " + eventDate.getText() + System.lineSeparator());
                                    break;
                                case "2":
                                    setEvent();
                                    totalCalendarPanel.removeAll();
                                    weeklyCalendar();
                                    totalCalendarPanel.revalidate();
                                    historyTextArea.append(eventField.getText() + " " + eventDate.getText() + System.lineSeparator());
                                    break;
                                case "3":
                                    setEvent();
                                    totalCalendarPanel.removeAll();
                                    dailyCalendar();
                                    totalCalendarPanel.revalidate();
                                    historyTextArea.append(eventField.getText() + " " + eventDate.getText() + System.lineSeparator());
                                    break;
                            }
                            evetAdded.add(eventField.getText()+" "+eventDate.getText());
                        }
                    } else {
                        JOptionPane.showMessageDialog(addEventButton, "Invalid date format " + eventDate.getText() + " enter date in MM/DD/YY format ");
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(addEventButton, "Invalid date format " + eventDate.getText() + " enter date in MM/DD/YY format");
                }

            }
        });


        labelPanel1.add(customizeCalendarLabel, BorderLayout.NORTH);
        labelPanel1.add(innerLabelPanel1, BorderLayout.CENTER);
        labelPanel1.add(innerLabelPanel2, BorderLayout.SOUTH);
        bottomPanel.add(labelPanel1, BorderLayout.NORTH);

        //Adding the third eventPanel in the bottomPanel in center
        JPanel eventPanel1 = new JPanel(new BorderLayout());

        //Adding the label in the eventPanel1
        JPanel innerEventPanel1 = new JPanel(new BorderLayout());
        JLabel textColorLabel = new JLabel("Change Text Color");
        innerEventPanel1.add(textColorLabel, BorderLayout.NORTH);

        //Adding the drop down  in the eventPanel
        colorBox.setBounds(50, 50, 90, 20);
        innerEventPanel1.add(colorBox, BorderLayout.CENTER);

        eventPanel1.add(innerEventPanel1, BorderLayout.NORTH);
        bottomPanel.add(eventPanel1, BorderLayout.CENTER);

        //Adding the history panel in the south of bottomPanel
        JPanel historyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //adding history label north of historyPanel
        JLabel historyLabel = new JLabel("History");
        historyPanel.add(historyLabel);

        //Adding historyTextArea center of historyPanel
        historyTextArea.setBorder(new EmptyBorder(15, 30, 15, 0));
        historyTextArea.setPreferredSize(new Dimension(200, 100));
        historyPanel.add(historyTextArea);

        //Adding deleteEvent Button to the south of history panel
        deleteEventButton.setHorizontalAlignment(JButton.CENTER);
        deleteEventButton.setBorder(new EmptyBorder(15, 15, 15, 30));
        historyPanel.add(deleteEventButton);
        bottomPanel.add(historyPanel, BorderLayout.SOUTH);

        deleteEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] temp = historyTextArea.getSelectedText().split(" ");
                System.out.println("Going to be as input "+temp[0]+" "+temp[1]);
                String selectedOption = group.getSelection().getActionCommand();
                switch (selectedOption){
                    case "1":
                        deleteEvent();
                        totalCalendarPanel.removeAll();
                        monthlyCalendar();
                        totalCalendarPanel.revalidate();
                        historyTextArea.setText(historyTextArea.getText().replace(historyTextArea.getSelectedText(), ""));
                        deleteEventValidation(temp[1],temp[0]);
                        break;
                    case "2":
                        deleteEvent();
                        totalCalendarPanel.removeAll();
                        weeklyCalendar();
                        totalCalendarPanel.revalidate();
                        historyTextArea.setText(historyTextArea.getText().replace(historyTextArea.getSelectedText(), ""));
                        deleteEventValidation(temp[1],temp[0]);
                        break;
                    case "3":
                        deleteEvent();
                        totalCalendarPanel.removeAll();
                        dailyCalendar();
                        totalCalendarPanel.revalidate();
                        historyTextArea.setText(historyTextArea.getText().replace(historyTextArea.getSelectedText(), ""));
                        deleteEventValidation(temp[1],temp[0]);
                        break;
                }


            }
        });

        //Adding all containers in the main frame
        JScrollPane pane = new JScrollPane(totalCalendarPanel);
        pane.getViewport().setView(totalCalendarPanel);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        maincalendarPanel.add(pane, BorderLayout.CENTER);
        maincalendarPanel.add(imagePanel, BorderLayout.NORTH);
        customizeCalendarPanel.add(topPanel, BorderLayout.NORTH);
        customizeCalendarPanel.add(bottomPanel, BorderLayout.CENTER);
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(splitPane);
        setTitle("MyCalendar");
        setSize(2000, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void monthlyCalendar() {

        //DayNameCreation
        JPanel dayPanelGrid = new JPanel();
        GridLayout gridLayout = new GridLayout(1, 7);
        dayPanelGrid.setBackground(Color.white);
        dayPanelGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dayPanelGrid.setLayout(gridLayout);
        String[] dayNames = {"Sun", "Mon", "Tues", "Weds", "Thurs", "Fri", "Sat"};
        for (int i = 0; i < dayNames.length; i++) {
            JLabel label = new JLabel(dayNames[i]);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dayPanelGrid.add(label);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
        }
        totalCalendarPanel.add(dayPanelGrid, BorderLayout.NORTH);

        JPanel calendarPanel = new JPanel();
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        calendarPanel.setLayout(new GridLayout(0, 7));

        //BlankGridCreation
        int z = 30;
        for (int j = 0; j <= 1; j++) {
            JPanel blankGrid = new JPanel();
            blankGrid.setLayout(new BorderLayout());
            blankGrid.setBackground(Color.white);
            blankGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel disabledDate = new JLabel("Dec " + z);
            disabledDate.setForeground(Color.gray);
            z++;

            blankGrid.add(disabledDate, BorderLayout.NORTH);
            calendarPanel.add(blankGrid);
        }

        //Main Calendar Display Panel
        for (int i = 0; i < months.size(); i++){
            for(int k = 1; k <= months.get(i).getDays(); k++){
                // creates a panel for for every grid in gridlayout
                JPanel grid = new JPanel();
                grid.setLayout(new BorderLayout());
                grid.setBackground(Color.white);
                grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // creates label for each date
                JLabel date = new JLabel(months.get(i).getMonthName() + " - " + k + " ");
                grid.add(date, BorderLayout.NORTH);
                //For printing the 1st day of month with different font color
                String value = months.get(i).getMonthName() + " " + k;
                if (value.equals("Jan 1") || value.equals("Feb 1") || value.equals("Mar 1") || value.equals("Apr 1")
                        || value.equals("May 1") || value.equals("Jun 1") || value.equals("July 1") || value.equals("Aug 1")
                        || value.equals("Sep 1") || value.equals("Oct 1") || value.equals("Nov 1") || value.equals("Dec 1")) {
                    date.setForeground(Color.BLUE);
                } else {
                    date.setForeground(Color.black);
                }
                //Creating text area for holiday names and events/
                JTextArea event = new JTextArea();
                event.setBackground(Color.blue);
                event.setLineWrap(true);
                //check if string returned by checkholiday.lenght() is > 0
                if (months.get(i).checkHoliday(k) != null) {
                    event.setText(months.get(i).checkHoliday(k) +System.lineSeparator());
                    event.setForeground(Color.white);
                }

                colorBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedColor = (String) colorBox.getSelectedItem();
                        if(selectedColor.equals("red")){
                            event.setForeground(Color.red);
                        }else if(selectedColor.equals("cyan")){
                            event.setForeground(Color.cyan);
                        }else if (selectedColor.equals("white")){
                            event.setForeground(Color.white);
                        }else if (selectedColor.equals("yellow")){
                            event.setForeground(Color.yellow);
                        }else if (selectedColor.equals("black")){
                            event.setForeground(Color.black);
                        }
                    }
                });

                grid.add(event, BorderLayout.CENTER);

                calendarPanel.add(grid);
                totalCalendarPanel.add(calendarPanel, BorderLayout.CENTER);


            }

        }
    }


    public void weeklyCalendar() {
        //DayNameCreation
        JPanel dayPanelGrid = new JPanel();
        for (int i = 0; i < 53; i++) {
            GridLayout gridLayout = new GridLayout(1, 7);
            dayPanelGrid.setBackground(Color.white);
            dayPanelGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dayPanelGrid.setLayout(gridLayout);
            String[] dayNames = {"Sun", "Mon", "Tues", "Weds", "Thurs", "Fri", "Sat"};
            for (int j = 0; j < dayNames.length; j++) {
                JLabel label = new JLabel(dayNames[j]);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                dayPanelGrid.add(label);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
            }
        }
        totalCalendarPanel.add(dayPanelGrid, BorderLayout.NORTH);

        JPanel calendarPanel = new JPanel();
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        calendarPanel.setLayout(new GridLayout(1, 7));

        //BlankGridCreation
        int z = 30;
        for (int j = 0; j <= 1; j++) {
            JPanel blankGrid = new JPanel();
            blankGrid.setLayout(new BorderLayout());
            blankGrid.setBackground(Color.white);
            blankGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel disabledDate = new JLabel("Dec " + z);
            disabledDate.setForeground(Color.gray);
            z++;

            blankGrid.add(disabledDate, BorderLayout.NORTH);
            calendarPanel.add(blankGrid);
        }

        //Main Calendar Display Panel
        for (int i = 0; i < months.size(); i++) {
            for (int k = 1; k <= months.get(i).getDays(); k++) {
                // creates a panel for for every grid in gridlayout
                JPanel grid = new JPanel();
                grid.setLayout(new BorderLayout());
                grid.setBackground(Color.white);
                grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // creates label for each date
                JLabel date = new JLabel(months.get(i).getMonthName() + " - " + k + " ");
                grid.add(date, BorderLayout.NORTH);
                //For printing the 1st day of month with different font color
                String value = months.get(i).getMonthName() + " " + k;
                if (value.equals("Jan 1") || value.equals("Feb 1") || value.equals("Mar 1") || value.equals("Apr 1")
                        || value.equals("May 1") || value.equals("Jun 1") || value.equals("July 1") || value.equals("Aug 1")
                        || value.equals("Sep 1") || value.equals("Oct 1") || value.equals("Nov 1") || value.equals("Dec 1")) {
                    date.setForeground(Color.BLUE);
                } else {
                    date.setForeground(Color.black);
                }
                //Creating text area for holiday names and events/
                JTextArea event = new JTextArea();
                event.setBackground(Color.blue);
                event.setLineWrap(true);
                if (months.get(i).checkHoliday(k) != null) {
                    event.setText(months.get(i).checkHoliday(k));
                    event.setForeground(Color.white);
                }

                colorBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedColor = (String) colorBox.getSelectedItem();
                        if (selectedColor.equals("red")) {
                            event.setForeground(Color.red);
                        } else if (selectedColor.equals("cyan")) {
                            event.setForeground(Color.cyan);
                        } else if (selectedColor.equals("white")) {
                            event.setForeground(Color.white);
                        } else if (selectedColor.equals("yellow")) {
                            event.setForeground(Color.yellow);
                        } else if (selectedColor.equals("black")) {
                            event.setForeground(Color.black);
                        }
                    }
                });

                grid.add(event, BorderLayout.CENTER);

                calendarPanel.add(grid);

            }

        }
        //3 more BlankGrid at the end
        int x = 1;
        for (int a = 0; a <= 3; a++) {
            JPanel blankGrid1 = new JPanel();
            blankGrid1.setLayout(new BorderLayout());
            blankGrid1.setBackground(Color.white);
            blankGrid1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel disabledDate = new JLabel("Jan " + x);
            disabledDate.setForeground(Color.gray);
            x++;

            blankGrid1.add(disabledDate, BorderLayout.NORTH);
            calendarPanel.add(blankGrid1);
        }
        totalCalendarPanel.add(calendarPanel, BorderLayout.CENTER);
    }

    public void dailyCalendar() {
        JPanel calendarPanel = new JPanel();
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        String[] dayNames = {"Sun", "Mon", "Tues", "Weds", "Thurs", "Fri", "Sat"};//*for daily calendar view, delete if not works*
        int j = 2;
        for (int i = 0; i < months.size(); i++) {
            for (int k = 1; k <= months.get(i).getDays(); k++) {
                if (j >= 7) {
                    j = 0;
                }
                String dayName = dayNames[j];
                JPanel grid = new JPanel();
                grid.setLayout(new BorderLayout());
                grid.setBackground(Color.white);
                grid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                grid.setPreferredSize(new Dimension(2000, 800));
                JLabel date = new JLabel(dayName + " : " + months.get(i).getMonthName() + " - " + k + " ");
                date.setPreferredSize(new Dimension(2000, 100));
                grid.add(date, BorderLayout.NORTH);
                j++;

                String value = months.get(i).getMonthName() + " " + k;
                if (value.equals("Jan 1") || value.equals("Feb 1") || value.equals("Mar 1") || value.equals("Apr 1")
                        || value.equals("May 1") || value.equals("Jun 1") || value.equals("July 1") || value.equals("Aug 1")
                        || value.equals("Sep 1") || value.equals("Oct 1") || value.equals("Nov 1") || value.equals("Dec 1")) {
                    date.setForeground(Color.BLUE);
                } else {
                    date.setForeground(Color.black);
                }
                JTextArea event = new JTextArea();
                event.setBackground(Color.blue);
                event.setLineWrap(true);
                event.setPreferredSize(new Dimension(2000, 200));
                if (months.get(i).checkHoliday(k) != null) {
                    event.setText(months.get(i).checkHoliday(k));
                    event.setForeground(Color.white);
                }

                colorBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedColor = (String) colorBox.getSelectedItem();
                        if (selectedColor.equals("red")) {
                            event.setForeground(Color.red);
                        } else if (selectedColor.equals("cyan")) {
                            event.setForeground(Color.cyan);
                        } else if (selectedColor.equals("white")) {
                            event.setForeground(Color.white);
                        } else if (selectedColor.equals("yellow")) {
                            event.setForeground(Color.yellow);
                        } else if (selectedColor.equals("black")) {
                            event.setForeground(Color.black);
                        }
                    }
                });

                grid.add(event, BorderLayout.CENTER);
                calendarPanel.add(grid);
            }
        }
        totalCalendarPanel.add(calendarPanel);
    }

    public void setEvent() {
        String[] temp = eventDate.getText().split("/");
        String monthName = listOfMonths[Integer.parseInt(temp[0]) - 1];
        for (int i = 0; i < months.size(); i++) {
            if (monthName.equals(months.get(i).getMonthName())) {
                System.out.println("Event " + eventField.getText() + " adding to " + monthName);
                months.get(i).setEvent(Integer.parseInt(temp[1]), eventField.getText());
                break;
            }
        }
    }

    public void deleteEvent() {
        String[] temp = historyTextArea.getSelectedText().split(" ");
        String tempEventName = temp[0];
        String[] tempEventDate = temp[1].split("/");
        String monthName = listOfMonths[Integer.parseInt(tempEventDate[0]) - 1];
        for (int i = 0; i < months.size(); i++){
            if (monthName.equals(months.get(i).getMonthName())) {
                System.out.println("Event " + tempEventName +  " deleting for month " + monthName + " for date "+ tempEventDate[1] );
                months.get(i).deleteEvent(Integer.parseInt(tempEventDate[1]),tempEventName);
                break;
            }
        }
    }

    public boolean eventAddedValidation(String eventDate, String eventName){
        if(evetAdded.size()!= 0){
            for(int i = 0 ; i < evetAdded.size();i++) {
                String[] temp = evetAdded.get(i).split(" ");
                if (temp[0].equals(eventName) && temp[1].trim().equals(eventDate)) {
                    return false;
                }
            }

        }
        return true;
    }

    public void deleteEventValidation(String eventDate, String eventName) {
        if (evetAdded.size() != 0) {
            for (int i = 0; i < evetAdded.size(); i++) {
                String[] temp = evetAdded.get(i).split(" ");
                if (temp[0].equals(eventName) && temp[1].trim().equals(eventDate.trim())) {
                    System.out.println("Deleted event " + evetAdded.remove(i));
                    break;
                }
            }
        }
    }

}




