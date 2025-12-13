//first we will do employee tab, then attendance, then salary slip, and the
//then report things
package ui;

//importing all swing modules
//here we imported all,
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

//import our codes
import dao.EmployeeDao;
import service.AttendanceService;
import service.SalaryCalculator;

public class PayrollWindow extends JFrame {

    private EmployeeDao employeeDao;
    private AttendanceService attendanceService;
    private SalaryCalculator salaryCalculator;
    
    //new fields because ofthe drop down error
    private EmployeePanel employeePanel;
    private SalarySlipPanel salarySlipPanel;
    //thisis for the attendance reference, the panel
    private AttendancePanel attendancepanel;

    public PayrollWindow() {
        //initializeing core objects here
        this.employeeDao = new EmployeeDao();
        this.attendanceService = new AttendanceService();
        this.salaryCalculator = new SalaryCalculator();

        initWindow();
        initTabs();
    }

    private void initWindow() {
        setTitle("Employee & Payroll Management System");
        setSize(900, 600);
        setLocationRelativeTo(null); //this is for center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initTabs() {
        JTabbedPane tabs = new JTabbedPane();
        
        //order is important, first employee tab
        //then attendance, then overtime, then report,
        //first write the panel lines, then the tab line
        //i think it works better that way

        //create panels that we will implement, follow the order
        //above
        EmployeePanel employeePanel = new EmployeePanel(employeeDao); // passes DAO
        SalarySlipPanel salarySlipPanel = new SalarySlipPanel(employeeDao, salaryCalculator, attendanceService);
        AttendancePanel attendancePanel = new AttendancePanel(employeeDao, attendanceService);
        //reports was not planned but tabs not enough annaru so
        //we are doing tabs
        ReportsPanel reportsPanel = new ReportsPanel(employeeDao, salaryCalculator);
       
        
        //tabs for employee
        tabs.addTab("Employees", employeePanel);
        
        //create the payroll panel here
        //tabs for payroll
        
        //next for attendance
        tabs.addTab("Attendance", attendancePanel);
        
        tabs.addTab("Payroll ", salarySlipPanel);
        
        //next only report one is left
        tabs.addTab("Report of Employee", reportsPanel);
        
        //when user switches tabs, refresh the combo box list
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() == salarySlipPanel) {
                salarySlipPanel.refreshEmployeeList();
            } 
            //here else if because i dont want dao issues
            //we got them in the beginning kadha, the
            //dao was not updating
            else if (tabs.getSelectedComponent() == attendancePanel) {
                attendancePanel.loadEmployeesIntoCombo(); // make this public if needed
            }
        });

        setContentPane(tabs);
    }

    //Optional: test launch from here
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PayrollWindow().setVisible(true);
        });
    }
}


//the flow now is app starting then salaryslip which see 0,
//like i have written
//adding employee, the dao is updated,
//then the slalry slip tab is switched, refresh,
//the employee list is called, the drop down workings
//arey eveything is working, the new attendence is working too
//but for attendence it like stores every single time we save a
//attendence like different or same attendence
//on the same day
//will we solve this at last or now?
//i think we have a lot in our code
