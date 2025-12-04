//we will need to store the employee details all his details,
//we will not ise sql  or database
//we will do storing in array on our computer
//also write the code we will need to edit and update the details

package dao;

import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDao {
	private List<Employee> employees = new ArrayList<>();
	
	//to add employee here
    public void add(Employee e) {
        employees.add(e);
    }
    
    //to update employee here
    public void update(Employee updated) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == updated.getId()) {
                employees.set(i, updated);
                return;
            }
        }
    }
    
    //to delete here
    public void deleteById(int id) {
        employees.removeIf(e -> e.getId() == id);
    }
    
    //to find/search here
    public Employee findById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public List<Employee> getAll() {
        return employees;
    }

}
