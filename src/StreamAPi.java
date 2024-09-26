import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamAPi {
	public static void main(String[] args) {
		List<Employee>person= Arrays.asList(
				new Employee(1,"mike",22,18000,"Male","HR","B'lore",2015),
				new Employee(2,"pooja",18,14500,"Female","IT","Noida",2012),
				new Employee(3,"adam",17,15400,"Male","HR","Pune",2018),
				new Employee(4,"nilam",20,13200,"Female","IT","Hydrabad",2014),
				new Employee(5,"rina",16,12800,"Female","HR","Delhi",2020),
				new Employee(6,"jons",14,18750,"Male","IT","B'lore",2021)
				);
		
		
		// Group the Employees by department.
		Map<String, List<Employee>> groupByDept = person.stream().collect(Collectors.groupingBy(Employee::getDepartment));
		System.out.println(groupByDept);
		
		// Group the Employees by city.
		Map<String, List<Employee>> groupByCity = person.stream().collect(Collectors.groupingBy(Employee::getCity));
		System.out.println(groupByCity);
		
		// Find the count of male and female employees present in the organization
		person.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()))
		     .entrySet().stream().forEach(e->System.out.println(e.getKey()+": "+e.getValue()));
		
		//Print the names of all departments in the organization.
		List<String> deptName = person.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList());
		System.out.println(deptName);
		
		// Print employee details whose age is greater than 28.
		person.stream().filter(e->e.getAge()>18).forEach(System.out::println);
		
		//  Find maximum age of employee.
		int maxAge = person.stream().mapToInt(Employee::getAge).max().getAsInt();
		System.out.println(maxAge);
		
		// Print Average age of Male and Female Employees.
		person.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.averagingDouble(Employee::getAge))).entrySet().stream().forEach(System.out::println);
		
		// Print the number of employees in each department.
		Map<String, Long> employees = person.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting()));
		employees.forEach((k,v)->{
			System.out.println("dept: "+k+" "+ "employee:"+v);
		});
		
		// Find oldest employee.
		Employee oldestEmployee = person.stream().sorted(Comparator.comparing(Employee::getAge).reversed()).findFirst().get();
		System.out.println("oldestEmployee:"+oldestEmployee);
		
		// Find youngest female employee.
		List<Employee> youngFemale = person.stream().filter(e->e.getGender().equals("Female")).sorted(Comparator.comparing(Employee::getAge)).limit(1).collect(Collectors.toList());
		System.out.println("youngFemale"+youngFemale);
		
		// Find employees whose age is greater than 30 and less than 30.
		person.stream().filter(e->e.getAge()<18).forEach(System.out::println);
		person.stream().filter(e->e.getAge()>18).forEach(System.out::println);
		
		// Find the department name which has the highest number of employees.
		String highestEmp = person.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).entrySet().stream()
		               .max(Map.Entry.comparingByValue()).get().getKey();
		System.out.println(highestEmp);
		
		//Find if there any employees from HR Department.
		person.stream().filter(e->e.getDepartment().equalsIgnoreCase("HR")).forEach(System.out::println);
		
		// Find the department names that these employees work for, where the number of employees in the department is over 3.
		person.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).entrySet().stream()
		               .filter(e->e.getValue()>3).forEach(e->System.out.println(e.getKey()+":"+e.getValue()));
		
		// Find distinct department names that employees work for.
		person.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);
		
		// Find all employees who lives in ‘Blore’ city, sort them by their name and print the names of employees.
		person.stream().filter(e->e.getCity().equalsIgnoreCase("B'lore")).sorted(Comparator.comparing(Employee::getName)).forEach(System.out::println);
		
		// No of employees in the organisation.
		long count = person.stream().count();
		System.out.println(count);
		
		// Find employee count in every department
		person.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).
		forEach((k,v)->{System.out.println(k+":"+v);
		});
		
		// 	Sorting a Stream by age and name fields.
		person.stream().sorted(Comparator.comparing(Employee::getAge).thenComparing(Comparator.comparing(Employee::getName))).forEach(System.out::println);
		
		// Highest experienced employees in the organization.
		Employee max = person.stream().min(Comparator.comparing(Employee::getYearOfJoiming)).get();
		Optional<Employee> experienced = person.stream().sorted(Comparator.comparing(Employee::getYearOfJoiming)).findFirst();
		System.out.println(experienced);
		
		// Print average and total salary of the organization.
		OptionalDouble average = person.stream().mapToInt(Employee::getAge).average();
		double sum = person.stream().mapToDouble(Employee::getSalary).sum();
		System.out.println(average+":"+sum);
		
		// Print Average salary of each department.
		Map<String, Double> avgSal = person.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.averagingDouble(Employee::getSalary)));
		System.out.println(avgSal);
		
		// Find Highest salary in the organisation.
		Optional<Employee> maxSalary = person.stream().collect(Collectors.maxBy(Comparator.comparing(Employee::getSalary)));
		System.out.println(maxSalary);
		
		
		// Find Second Highest salary in the organisation.
		Employee secondMax = person.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).findFirst().get();
		System.out.println(secondMax);
		
		// Nth Highest salary.
		int n=6;
		Employee employee = person.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(n-1).findFirst().get();		
		System.out.println(employee);
		
		// Find highest paid salary in the organisation based on gender.
		Map<String, Optional<Employee>> highestPaid = person.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.maxBy((t1,t2)->(int) (t1.getSalary()-t2.getSalary()))));;
		System.out.println("highestPaid"+highestPaid);
		
		// Find lowest paid salary in the organisation based in the organisation.
		Map<String, Optional<Employee>> lowestPaid = person.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.minBy((t1,t2)-> (int)(t1.getSalary()-t2.getSalary()))));
		System.out.println("lowestPaid"+lowestPaid);
		
		// 	Sort the employees salary in the organisation in ascending order
		List<Employee> sortEmployeeAsc = person.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.toList());
		System.out.println("sortEmployeeAsc"+sortEmployeeAsc);
		
		//Sort the employees salary in the organisation in descending order.Comp
		List<Employee> sortEmployeeDesc = person.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.toList());
		System.out.println("sortEmployeeDesc"+sortEmployeeDesc);
		
		//  Highest salary based on department.
		Map<String, Optional<Employee>> highSalDept = person.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
		System.out.println("highSalDept:"+highSalDept);
		
		// Print list of employee’s second highest record based on department
		Map<String, Optional<Employee>> secondHighest = person.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.collectingAndThen(Collectors.toList(), list->list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).findFirst())));
		System.out.println("secondHighest"+secondHighest);
		
		// Sort the employees salary in each department in ascending order
		Map<String, List<Employee>> empAsc = person.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.groupingBy(Employee::getDepartment));
		System.out.println("empAsc"+empAsc);
		
		//  Sort the employees salary in each department in descending order
		Map<String, List<Employee>> empDesc = person.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.groupingBy(Employee::getDepartment));
		System.out.println("empDesc"+empDesc);
	}

}
