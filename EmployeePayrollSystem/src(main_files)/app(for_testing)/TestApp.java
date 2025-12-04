/*package app;

import java.time.LocalDate;

//import all of our codes
import dao.EmployeeDao;
import model.AttendanceStatus;
import model.Employee;
import service.AttendanceService;
import service.SalaryCalculator;

public class TestApp {

    public static void main(String[] args) {

        EmployeeDao empDao = new EmployeeDao();
        AttendanceService attendanceService = new AttendanceService();
        SalaryCalculator payrollService = new SalaryCalculator();
        
        //an example run test
        Employee emp = new Employee(
                1,
                "Rahul Sharma",
                "rahul@example.com",
                "9876543210",
                "IT",
                "Software Engineer",
                40000, 8000, 4000, 3000,
                0.10, 0.12
        );

        empDao.add(emp);

        attendanceService.markAttendance(1, LocalDate.of(2025, 11, 1), AttendanceStatus.PRESENT);
        attendanceService.markAttendance(1, LocalDate.of(2025, 11, 2), AttendanceStatus.ABSENT);
        attendanceService.markAttendance(1, LocalDate.of(2025, 11, 3), AttendanceStatus.HALF_DAY_LEAVE);

        double perDay = emp.getBasicSalary() / 30.0;
        double lop = attendanceService.calculateLossOfPay(1, 2025, 11, perDay);

        double overtimeHours = 10;
        double overtimeRate = 200;

        var payment = payrollService.generatePayment(emp, overtimeHours, overtimeRate, lop);

        System.out.println("Gross: " + payment.getGross());
        System.out.println("Deductions: " + payment.getDeductions());
        System.out.println("Net Salary: " + payment.getNet());
    }
}
*/