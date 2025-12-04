//here we should write the salary, it will be new beacuse we have cut it
//because of the absents and leaves

package service;

import java.time.LocalDate;

import model.Employee;
import model.PaymentRecord;

public class SalaryCalculator {
	
	//give payment 
    public PaymentRecord generatePayment(Employee e,
                                         double overtimeHours,
                                         double overtimeRate,
                                         double lopAmount) {

        double gross = e.getBasicSalary()
                + e.getHra()
                + e.getDa()
                + e.getAllowance()
                + (overtimeHours * overtimeRate);

        gross -= lopAmount;

        double tax = gross * e.getTaxRate();
        double pf = gross * e.getPfRate();
        double totalDeductions = tax + pf;

        double net = gross - totalDeductions;

        PaymentRecord record = new PaymentRecord(LocalDate.now(), gross, totalDeductions, net);
        e.addPaymentRecord(record);
        return record;
    }
}
