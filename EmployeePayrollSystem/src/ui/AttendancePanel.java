//the panel of attendence recording
//we have created this using a super class addition
//that option is given by eclipse

package ui;

//as usual first we will imort the packages
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

//then we will connect our codes
import dao.EmployeeDao;
import model.Employee;
import model.AttendanceStatus;
import model.AttendanceRecord;
import service.AttendanceService;

//write the code of our attendence
public class AttendancePanel extends JPanel {

    private EmployeeDao employeeDao;
    private AttendanceService attendanceService;

    private JComboBox<String> employeeCombo;
    private JComboBox<Integer> dayCombo;
    private JComboBox<Integer> monthCombo;
    private JComboBox<Integer> yearCombo;
    private JComboBox<AttendanceStatus> statusCombo;

    private JButton btnMark, btnLoad;
    private JTable table;
    private DefaultTableModel tableModel;

    public AttendancePanel(EmployeeDao employeeDao, AttendanceService attendanceService) {
        this.employeeDao = employeeDao;
        this.attendanceService = attendanceService;

        initComponents();
        layoutComponents();
        initActions();
        loadEmployeesIntoCombo();
        setDefaultDate();
    }
    
    //write the init for the components we need
    private void initComponents() {
        employeeCombo = new JComboBox<>();

        //first in our logic write day 1–31
        dayCombo = new JComboBox<>();
        for (int d = 1; d <= 31; d++) {
            dayCombo.addItem(d);
        }

        //then add the months 1–12, we can write names too but we decided on 
        //numbers since they are easy
        monthCombo = new JComboBox<>();
        for (int m = 1; m <= 12; m++) {
            monthCombo.addItem(m);
        }

        //write year options, we will write only a few 
        yearCombo = new JComboBox<>();
        for (int y = 2024; y <= 2026; y++) {
            yearCombo.addItem(y);
        }

        //status list from the enum which gives the status
        statusCombo = new JComboBox<>(AttendanceStatus.values());

        btnMark = new JButton("Mark Attendance");
        btnLoad = new JButton("Load Records");

        String[] cols = { "Date", "Status" };
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
    }
    
    //now the plan for the layout is writing the form
    //on the top and then writing the table bottom or centre also
    private void layoutComponents() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        //the first row 0 employee
        gbc.gridx = 0; gbc.gridy = row;
        top.add(new JLabel("Employee:"), gbc);
        gbc.gridx = 1;
        top.add(employeeCombo, gbc);

        row++;

        //then for row 1, date in d/m/y
        gbc.gridx = 0; gbc.gridy = row;
        top.add(new JLabel("Date:"), gbc);

        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Day"));
        datePanel.add(dayCombo);
        datePanel.add(new JLabel("Month"));
        datePanel.add(monthCombo);
        datePanel.add(new JLabel("Year"));
        datePanel.add(yearCombo);

        gbc.gridx = 1;
        top.add(datePanel, gbc);

        row++;

        //then for row 2: Status
        gbc.gridx = 0; gbc.gridy = row;
        top.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        top.add(statusCombo, gbc);

        row++;

        //and then for row 3 put buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnMark);
        buttonPanel.add(btnLoad);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        top.add(buttonPanel, gbc);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    
    //up until here we did, now we need to add the employess 
    //into the combo
    //arey, dont declare private methods man, can't use them
    public void loadEmployeesIntoCombo() {
        employeeCombo.removeAllItems();
        List<Employee> list = employeeDao.getAll();

        for (Employee e : list) {
            employeeCombo.addItem(e.getId() + " - " + e.getName());
        }
    }

    private int getSelectedEmployeeId() {
        String selected = (String) employeeCombo.getSelectedItem();
        if (selected == null || selected.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(selected.split(" - ")[0]);
    }
    //for the date i am using tyhis day, i mean today
    //as the default day because i will be testing it everyday
    private void setDefaultDate() {
        LocalDate today = LocalDate.now();
        dayCombo.setSelectedItem(today.getDayOfMonth());
        monthCombo.setSelectedItem(today.getMonthValue());
        yearCombo.setSelectedItem(today.getYear());
    }
    
    //now i have the data and the date, i should write the code
    //for buttons, get it from ruthvik and vaishu files
    private void initActions() {
        btnMark.addActionListener(e -> markAttendance());
        btnLoad.addActionListener(e -> loadAttendanceForSelectedMonth());
    }

    private void markAttendance() {
        int empId = getSelectedEmployeeId();
        if (empId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee.");
            return;
        }

        Integer day = (Integer) dayCombo.getSelectedItem();
        Integer month = (Integer) monthCombo.getSelectedItem();
        Integer year = (Integer) yearCombo.getSelectedItem();
        AttendanceStatus status = (AttendanceStatus) statusCombo.getSelectedItem();

        try {
            LocalDate date = LocalDate.of(year, month, day);
            attendanceService.markAttendance(empId, date, status);
            JOptionPane.showMessageDialog(this, "Attendance marked for " + date);
            loadAttendanceForSelectedMonth(); // refresh table
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date selected: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAttendanceForSelectedMonth() {
        int empId = getSelectedEmployeeId();
        if (empId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee.");
            return;
        }

        Integer month = (Integer) monthCombo.getSelectedItem();
        Integer year = (Integer) yearCombo.getSelectedItem();

        tableModel.setRowCount(0);

        List<AttendanceRecord> records =
                attendanceService.getRecordsForEmployeeMonth(empId, year, month);

        for (AttendanceRecord r : records) {
            Object[] row = {
                    r.getDate().toString(),
                    r.getStatus().name()
            };
            tableModel.addRow(row);
        }
    }
}



    
