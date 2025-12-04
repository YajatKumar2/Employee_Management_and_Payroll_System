//the plan for employee panel
//tot he top or left we will put the form fields
//to the bottom we will put a table showing all employees
//to the right or under the form we will add buttons
package ui;

//first importing all the packages we need
//import all swing ones
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

//import all awt ones
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

//import our codes
import dao.EmployeeDao;
import model.Employee;

public class EmployeePanel extends JPanel {

    private EmployeeDao employeeDao;

    //form fields
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtDepartment;
    private JTextField txtDesignation;
    private JTextField txtBasic;
    private JTextField txtHra;
    private JTextField txtDa;
    private JTextField txtAllowance;
    private JTextField txtTaxRate;
    private JTextField txtPfRate;

    //create the table
    private JTable table;
    private DefaultTableModel tableModel;

    public EmployeePanel(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
        initComponents();
        layoutComponents();
        initListeners();
        refreshTable();
    }
    
    private void initComponents() {
    	//innitialsing the components
        txtId = new JTextField(10);
        txtName = new JTextField(15);
        txtEmail = new JTextField(15);
        txtPhone = new JTextField(12);
        txtDepartment = new JTextField(10);
        txtDesignation = new JTextField(10);
        txtBasic = new JTextField(10);
        txtHra = new JTextField(10);
        txtDa = new JTextField(10);
        txtAllowance = new JTextField(10);
        txtTaxRate = new JTextField(10);
        txtPfRate = new JTextField(10);
        
        //wrtite the column names here
        //but write full names for the boxes and directly using grid
        //it is simple this way 

        String[] columns = {
            "ID", "Name", "Email", "Phone", "Dept", "Desig",
            "Basic", "HRA", "DA", "Allow", "Tax%", "PF%"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //make the  table read-only it shouldnt be able to edit
            }
        };
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
    }
    
    //we should use borderlayout for the panel thing
    //and gridbaglayout for the form
    private void layoutComponents() {
        setLayout(new BorderLayout());

        // ---------- FORM PANEL ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        //row 0: ID, Name
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtId, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtName, gbc);

        row++;

        //row 1: Email, Phone
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtPhone, gbc);

        row++;

        //row 2: Department, Designation
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDepartment, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Designation:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtDesignation, gbc);

        row++;

        //row 3: Basic, HRA
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Basic:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtBasic, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("HRA(House Rent Allowance):"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtHra, gbc);

        row++;

        //row 4: DA, Allowance
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("DA(Dearness Allowance):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDa, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Allowance:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtAllowance, gbc);

        row++;

        //row 5: TaxRate, PfRate
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Tax Rate (e.g. 0.1):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTaxRate, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Provinent Fund Rate (e.g. 0.12):"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtPfRate, gbc);

        row++;

        // ---------- BUTTON PANEL ----------
        //we write adding, updating, deleting and reseting the grids
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // store buttons in fields if you want in listeners,
        // or wire listeners here directly (we'll do below).

        // Add formPanel + buttonPanel at NORTH
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // ---------- TABLE ----------
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // For listeners we’ll need references to buttons:
        // easiest: move button creation to fields.
        // To keep code shorter here, we'll add listeners right now:
        initButtonActions(btnAdd, btnUpdate, btnDelete, btnClear);
    }
    
    //now we should do the coddes for buttons
    private void initListeners() {
        // left empty if using initButtonActions; or you can use this instead.
    }

    //we need action listners for closing adding updating and all
    //arey also we will write employee selection like when selected in the list
    //it should display in the grids
    //so it is easy to edit
    //we need getter for this i think
    
    private void initButtonActions(JButton btnAdd, JButton btnUpdate,
                                   JButton btnDelete, JButton btnClear) {

        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnClear.addActionListener(e -> clearForm());

        // When user clicks a row in the table, load it into form
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    loadEmployeeFromTable(selectedRow);
                }
            }
        });
    }
    
    //we should start implementing these codes
    //add the employee
    private void addEmployee() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String dept = txtDepartment.getText().trim();
            String desig = txtDesignation.getText().trim();

            double basic = Double.parseDouble(txtBasic.getText().trim());
            double hra = Double.parseDouble(txtHra.getText().trim());
            double da = Double.parseDouble(txtDa.getText().trim());
            double allowance = Double.parseDouble(txtAllowance.getText().trim());
            double taxRate = Double.parseDouble(txtTaxRate.getText().trim());
            double pfRate = Double.parseDouble(txtPfRate.getText().trim());

            //simple validation, name cant be empty
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required");
                return;
            }

            Employee emp = new Employee(
                    id, name, email, phone,
                    dept, desig,
                    basic, hra, da, allowance,
                    taxRate, pfRate
            );

            employeeDao.add(emp);
            refreshTable();
            clearForm();
            //write exceptions also, only number in number grid
 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please check numeric fields (ID, salaries, rates).",
                    "Invalid number", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error adding employee: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    //update employee for matching we will use the id or name
    private void updateEmployee() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Employee existing = employeeDao.findById(id);
            if (existing == null) {
                JOptionPane.showMessageDialog(this, "Employee ID not found.");
                return;
            }

            existing.setName(txtName.getText().trim());
            existing.setEmail(txtEmail.getText().trim());
            existing.setPhone(txtPhone.getText().trim());
            existing.setDepartment(txtDepartment.getText().trim());
            existing.setDesignation(txtDesignation.getText().trim());
            existing.setBasicSalary(Double.parseDouble(txtBasic.getText().trim()));
            existing.setHra(Double.parseDouble(txtHra.getText().trim()));
            existing.setDa(Double.parseDouble(txtDa.getText().trim()));
            existing.setAllowance(Double.parseDouble(txtAllowance.getText().trim()));
            existing.setTaxRate(Double.parseDouble(txtTaxRate.getText().trim()));
            existing.setPfRate(Double.parseDouble(txtPfRate.getText().trim()));

            employeeDao.update(existing);
            refreshTable();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Check numeric values.", "Invalid number",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            employeeDao.deleteById(id);
            refreshTable();
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid numeric ID for delete.");
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtDepartment.setText("");
        txtDesignation.setText("");
        txtBasic.setText("");
        txtHra.setText("");
        txtDa.setText("");
        txtAllowance.setText("");
        txtTaxRate.setText("");
        txtPfRate.setText("");
        table.clearSelection();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0); // clear

        List<Employee> list = employeeDao.getAll();
        for (Employee emp : list) {
            Object[] row = {
                    emp.getId(),
                    emp.getName(),
                    emp.getEmail(),
                    emp.getPhone(),
                    emp.getDepartment(),
                    emp.getDesignation(),
                    emp.getBasicSalary(),
                    emp.getHra(),
                    emp.getDa(),
                    emp.getAllowance(),
                    emp.getTaxRate(),
                    emp.getPfRate()
            };
            tableModel.addRow(row);
        }
    }

    private void loadEmployeeFromTable(int rowIndex) {
        int id = (int) tableModel.getValueAt(rowIndex, 0);
        Employee emp = employeeDao.findById(id);
        if (emp == null) return;

        txtId.setText(String.valueOf(emp.getId()));
        txtName.setText(emp.getName());
        txtEmail.setText(emp.getEmail());
        txtPhone.setText(emp.getPhone());
        txtDepartment.setText(emp.getDepartment());
        txtDesignation.setText(emp.getDesignation());
        txtBasic.setText(String.valueOf(emp.getBasicSalary()));
        txtHra.setText(String.valueOf(emp.getHra()));
        txtDa.setText(String.valueOf(emp.getDa()));
        txtAllowance.setText(String.valueOf(emp.getAllowance()));
        txtTaxRate.setText(String.valueOf(emp.getTaxRate()));
        txtPfRate.setText(String.valueOf(emp.getPfRate()));
    }
}

