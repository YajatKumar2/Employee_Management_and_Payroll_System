//this si the user interface for the payment things
package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

//always the first step, importing
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//then connect our codes
import dao.EmployeeDao;
import model.Employee;
import model.PaymentRecord;
import service.AttendanceService;
import service.SalaryCalculator;

//then the code
public class SalarySlipPanel extends JPanel {

    private EmployeeDao employeeDao;
    private SalaryCalculator salaryCalculator;
    private AttendanceService attendanceService;

    private JComboBox<String> employeeSelector;
    private JTextField txtOvertimeHours, txtOvertimeRate;
    private JTextArea payslipArea;
    private JButton btnGenerate, btnClear;
    //new fields, because the pay slip was not good enough
    //because it didnot display month and year, and
    //calculations lo it was not clear which month we were calculating
    private JComboBox<Integer> monthComboSlip;
    private JComboBox<Integer> yearComboSlip;
    

    public SalarySlipPanel(EmployeeDao employeeDao, SalaryCalculator salalryCalculator, AttendanceService attendanceService) {
        this.employeeDao = employeeDao;
        this.salaryCalculator = salalryCalculator;
        this.attendanceService = attendanceService;

        initComponents();
        layoutComponents();
        initActions();
        loadEmployeeList();
    }
    
    //now we need to initialise the components
    private void initComponents() {
        employeeSelector = new JComboBox<>();

        txtOvertimeHours = new JTextField(10);
        txtOvertimeRate = new JTextField(10);
        txtOvertimeRate.setText("200"); // default rate
        
        payslipArea = new JTextArea(15, 50);
        payslipArea.setEditable(false);
        payslipArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        btnGenerate = new JButton("Generate Payroll");
        btnClear = new JButton("Clear Payslip");
        
        //the new fields lines here
        monthComboSlip = new JComboBox<>();
        for (int m = 1; m <= 12; m++) monthComboSlip.addItem(m);

        yearComboSlip = new JComboBox<>();
        for (int y = 2023; y <= 2027; y++) yearComboSlip.addItem(y);

    }
    
    //now for the latout we will use vertical one, where
    //there is from on the top and there is bottom payslip(
    private void layoutComponents() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        gbc.gridx=0; gbc.gridy=row; top.add(new JLabel("Select Employee:"), gbc);
        gbc.gridx=1; top.add(employeeSelector, gbc);
        row++;

        gbc.gridx=0; gbc.gridy=row; top.add(new JLabel("Overtime Hours:"), gbc);
        gbc.gridx=1; top.add(txtOvertimeHours, gbc);
        row++;

        gbc.gridx=0; gbc.gridy=row; top.add(new JLabel("Overtime Rate:"), gbc);
        gbc.gridx=1; top.add(txtOvertimeRate, gbc);
        row++;

        gbc.gridx=0; gbc.gridy=row; top.add(btnGenerate, gbc);
        gbc.gridx=1; top.add(btnClear, gbc);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(payslipArea), BorderLayout.CENTER);
        // the row for Month
        gbc.gridx = 0; gbc.gridy = 3;
        top.add(new JLabel("Month:"), gbc);

        gbc.gridx = 1;
        top.add(monthComboSlip, gbc);

        //write roow for year here
        gbc.gridx = 0; gbc.gridy = 4;
        top.add(new JLabel("Year:"), gbc);

        gbc.gridx = 1;
        top.add(yearComboSlip, gbc);

        //move generate & clear buttons down one row
        //because we are adding new one above
        gbc.gridx = 0; gbc.gridy = 5;
        top.add(btnGenerate, gbc);

        gbc.gridx = 1;
        top.add(btnClear, gbc);

    
    }
    
    //refresh the employee list in the dao
    //to make sure it is not called before and we will not get the employee list on 
    //dropdown, this has happened to me
    public void refreshEmployeeList() {
    	loadEmployeeList();
    }
    
    private void loadEmployeeList() {
        employeeSelector.removeAllItems();
        java.util.List<Employee> list = employeeDao.getAll();
        
        //guys this is for testing, 
        //to seen the input
        //remove or comment this print line
        //or we can keep it to show that the employees are getting
        //recorded
        System.out.println("Employees seen in SalarySlipPanel: " + list.size());

        for (Employee e : list) {
            employeeSelector.addItem(e.getId() + " - " + e.getName()); 
        }
    }

    //we need a helper
    private int getSelectedEmployeeId() {
        String selected = (String) employeeSelector.getSelectedItem();
        if (selected == null) return -1;
        return Integer.parseInt(selected.split(" - ")[0]);
    }
    private void initActions() {
        btnGenerate.addActionListener(e -> generatePayslip());
        btnClear.addActionListener(e -> payslipArea.setText(""));
    }

    private void generatePayslip() {
        try {
            int id = getSelectedEmployeeId();
            if (id == -1) {
                JOptionPane.showMessageDialog(this, "No employee selected!");
                return;
            }

            Employee emp = employeeDao.findById(id);
            double overtimeHours = Double.parseDouble(txtOvertimeHours.getText().trim());
            double overtimeRate = Double.parseDouble(txtOvertimeRate.getText().trim());

            
            //here we have to make it select the
            //date which the user is using
            int month = (Integer) monthComboSlip.getSelectedItem();
            int year = (Integer) yearComboSlip.getSelectedItem();
            
            double perDay = emp.getBasicSalary() / 30.0;
            
            
            double lop = attendanceService.calculateLossOfPay(id, year, month, perDay);

            
            //later we can make month selectable le

            PaymentRecord pay = salaryCalculator.generatePayment(emp, overtimeHours, overtimeRate, lop);

            String slip = """
                    ============!! PAYSLIP !!============
                    Employee: %s (%d)
                    Department: %s
                    Designation: %s
                    ---------------------------------
                    The Basic Salary is  	: %.2f
                    HRA            			: %.2f
                    DA(Dearness Allowence   : %.2f
                    Allowance      			: %.2f
                    Overtime Pay   			: %.2f
                    LOP Deduction  			: %.2f
                    -------------------------------------
                    GROSS          : %.2f
                    DEDUCTIONS     : %.2f
                    NET SALARY     : %.2f
                    =================================
                    """.formatted(
                            emp.getName(), emp.getId(),
                            emp.getDepartment(), emp.getDesignation(),
                            emp.getBasicSalary(), emp.getHra(), emp.getDa(), emp.getAllowance(),
                            overtimeHours * overtimeRate, lop,
                            pay.getGross(), pay.getDeductions(), pay.getNet()
                    );

            payslipArea.setText(slip);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating payroll: "+ex.getMessage());
        }
    }
}
