package ui;

//first imorting all the necessary libraries
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

//import all the necessary files
import dao.EmployeeDao;
import model.Employee;
import model.PaymentRecord;
import service.SalaryCalculator;

public class ReportsPanel extends JPanel{
	//declaring the types fields
	private EmployeeDao employeeDao;
	private SalaryCalculator salaryCalculator;
	
	//all the gui things here
	private JButton btnDeptCost, btnTopEarners, btnMonthlySummary;
    private JTextField txtTopN;
    private JComboBox<Integer> monthCombo;
    private JComboBox<Integer> yearCombo;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea infoArea;
    
    //date things
    private final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
    
    //rest of the code
    public ReportsPanel(EmployeeDao employeeDao, SalaryCalculator salaryCalculator) {
        this.employeeDao = employeeDao;
        this.salaryCalculator = salaryCalculator;

        initComponents();
        layoutComponents();
        initActions();
    }
    
    //initialsing things
    private void initComponents() {
        btnDeptCost = new JButton("Department Cost");
        btnTopEarners = new JButton("Top Earners");
        btnMonthlySummary = new JButton("Monthly Summary");

        txtTopN = new JTextField("5", 3);

        monthCombo = new JComboBox<>();
        for (int m = 1; m <= 12; m++) monthCombo.addItem(m);

        yearCombo = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 2; y <= currentYear + 1; y++) yearCombo.addItem(y);
        yearCombo.setSelectedItem(currentYear);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        infoArea = new JTextArea(6, 60);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }
    
    //design the layoutt
    private void layoutComponents() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Top N earners
        gbc.gridx=0; gbc.gridy=row; top.add(new JLabel("Top N:"), gbc);
        gbc.gridx=1; top.add(txtTopN, gbc);
        gbc.gridx=2; top.add(btnTopEarners, gbc);

        row++;
        // Month + Year Summary
        gbc.gridx=0; gbc.gridy=row; top.add(new JLabel("Month:"), gbc);
        gbc.gridx=1; top.add(monthCombo, gbc);
        gbc.gridx=2; top.add(new JLabel("Year:"), gbc);
        gbc.gridx=3; top.add(yearCombo, gbc);
        gbc.gridx=4; top.add(btnMonthlySummary, gbc);

        row++;
        gbc.gridx=0; gbc.gridy=row; top.add(btnDeptCost, gbc);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(new JScrollPane(infoArea), BorderLayout.SOUTH);
    }
    
    private void initActions() {
        btnDeptCost.addActionListener(e -> showDepartmentCost());
        btnTopEarners.addActionListener(e -> showTopEarners());
        btnMonthlySummary.addActionListener(e -> showMonthlySummary());
    }
    
    //here write the report contents
    private void showDepartmentCost() {
        List<Employee> employees = employeeDao.getAll();

        Map<String, Double> deptSum = new HashMap<>();

        for (Employee emp : employees) {
            double value = getLatestNetOrEstimate(emp);
            String dept = emp.getDepartment() == null ? "Unknown" : emp.getDepartment();
            deptSum.put(dept, deptSum.getOrDefault(dept, 0.0) + value);
        }

        tableModel.setColumnIdentifiers(new String[]{"Department", "Total (₹)"});
        tableModel.setRowCount(0);

        deptSum.entrySet().stream()
                .sorted((a,b) -> Double.compare(b.getValue(), a.getValue()))
                .forEach(e -> tableModel.addRow(new Object[]{
                        e.getKey(),
                        String.format("%.2f", e.getValue())
                }));

        infoArea.setText("Shows total latest net payment or the estimated salary, which is, grouped by department.");
    }
    
    private void showTopEarners() {
        int n;
        try {
            n = Integer.parseInt(txtTopN.getText().trim());
            if (n <= 0) n = 5;
        } catch (Exception ex) {
            n = 5;
        }

        List<Employee> employees = employeeDao.getAll();

        List<EmployeeValue> sorted = employees.stream().map(emp -> new EmployeeValue(emp, getLatestNetOrEstimate(emp)))
                .sorted((a,b) -> Double.compare(b.value, a.value))
                .collect(Collectors.toList());

        tableModel.setColumnIdentifiers(new String[]{"Rank", "ID", "Name", "Department", "Value (₹)"});
        tableModel.setRowCount(0);

        int rank = 1;
        for (EmployeeValue ev : sorted.stream().limit(n).collect(Collectors.toList())) {
            tableModel.addRow(new Object[]{
                    rank++, ev.emp.getId(), ev.emp.getName(), ev.emp.getDepartment(),
                    String.format("%.2f", ev.value)
            });
        }

        //an area to provide info, remove these if you guys want
        infoArea.setText("Shows top " + n + " earners by latest net payment or salary estimate.");
    }
    
    private void showMonthlySummary() {
        int month = (Integer) monthCombo.getSelectedItem();
        int year = (Integer) yearCombo.getSelectedItem();

        double totalGross = 0, totalDed = 0, totalNet = 0;
        int count = 0;

        for (Employee emp : employeeDao.getAll()) {
            for (PaymentRecord p : emp.getPaymentHistory()) {
                
            	if (p.getSalaryMonth() == month &&
                        p.getSalaryYear() == year) {
                    totalGross += p.getGross();
                    totalDed += p.getDeductions();
                    totalNet += p.getNet();
                    count++;
                }
            }
        }

        tableModel.setColumnIdentifiers(new String[]{"Metric", "Value"});
        tableModel.setRowCount(0);

        tableModel.addRow(new Object[]{"Month/Year", month + "/" + year});
        tableModel.addRow(new Object[]{"Records Found", count});
        tableModel.addRow(new Object[]{"Total Gross", String.format("%.2f", totalGross)});
        tableModel.addRow(new Object[]{"Total Deductions", String.format("%.2f", totalDed)});
        tableModel.addRow(new Object[]{"Total Net", String.format("%.2f", totalNet)});

        infoArea.setText("Monthly summary across ALL employees for " + month + "/" + year);
    }

    //write helpers here

    private double getLatestNetOrEstimate(Employee emp) {
        List<PaymentRecord> list = emp.getPaymentHistory();
        if (list != null && !list.isEmpty()) {
            return list.get(list.size()-1).getNet();
        } else {
            return emp.getBasicSalary() + emp.getHra() + emp.getDa() + emp.getAllowance();
        }
    }

    private static class EmployeeValue {
        Employee emp;
        double value;
        EmployeeValue(Employee e, double v) { this.emp = e; this.value = v; }
    }
    
}

//now we designed it, we have to add
//it and display it