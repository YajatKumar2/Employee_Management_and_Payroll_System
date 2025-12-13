//Here we will create and save the employee in a list make sure there are many types of details that we need
//to take and save

package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class Employee extends Person implements Payable{
	private int id;
	private String department;
	private String designation;
	
	private double basicSalary;
    private double hra;
    private double da;
    private double allowance;
    private double taxRate;
    private double pfRate;
    
    private String gender;
    private String maritalStatus;
    
    //for photo new filed
    private String photoPath;

    private List<PaymentRecord> paymentHistory = new ArrayList<>();

    public Employee(int id, String name, String email, String phone,
                    String department, String designation,
                    double basicSalary, double hra, double da,
                    double allowance, double taxRate, double pfRate) {

        super(name, email, phone);
        this.id = id;
        this.department = department;
        this.designation = designation;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.da = da;
        this.allowance = allowance;
        this.taxRate = taxRate;
        this.pfRate = pfRate;
    
    }
    
    //already we have payable, implement that
    //because we need this logic, it is important,
    //so i will write interface and you guys write the logic
    
    @Override
    public double calculateNetSalary() {
        double gross = basicSalary + hra + da + allowance;
        double totalTax = gross * taxRate;
        double totalPf = gross * pfRate;
        return gross - (totalTax + totalPf);
    }

    public void addPaymentRecord(PaymentRecord p) {
        paymentHistory.add(p);
    }
    
    //use the eclipse getter and setter and write them
    //for above variables since we need to be channging them

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}

	public double getHra() {
		return hra;
	}

	public void setHra(double hra) {
		this.hra = hra;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}

	public double getAllowance() {
		return allowance;
	}

	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getPfRate() {
		return pfRate;
	}

	public void setPfRate(double pfRate) {
		this.pfRate = pfRate;
	}

	public List<PaymentRecord> getPaymentHistory() {
		return paymentHistory;
	}

	public void setPaymentHistory(List<PaymentRecord> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
    
    //we will use here getter and setter
    
	//gett and set for photo
	/*public void setPhotoPath(String photoPath) {
	    this.photoPath = photoPath;
	}

	public String getPhotoPath() {
	    return photoPath;
	}*/

}
