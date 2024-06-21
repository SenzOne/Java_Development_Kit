package lesson_4_collections.homework_4;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        final int EMPLOYEE_COUNT = 8;

        List<Employee> employees = EmployeeFactory.createEmployees(EMPLOYEE_COUNT);
        employees.forEach(System.out::println);

        System.out.println("Самый опытный сотрудник: " + findMostExperiencedEmployee(employees).orElse(null));
        System.out.println("Номер телефона сотрудника \"John Doe\": "
                           + findPhoneNumberByName(employees, "John Doe"));
        System.out.println("поиск по id 1: " + findEmployeeById(employees, 1));

        //    Добавить метод добавления нового сотрудника в справочник
        Employee newEmployee = EmployeeFactory.createEmployee("555-123-4567", "New Employee", 10);
        System.out.println(newEmployee);
    }

    // Добавить метод, который ищет сотрудника по стажу (может быть список)
    static Optional<Employee> findMostExperiencedEmployee(List<Employee> employees) {
        return employees.stream()
                .max(Comparator.comparing(Employee::work_experience));
    }

    //Добавить метод, который возвращает номер телефона сотрудника по имени (может быть список)
    static List<String> findPhoneNumberByName(List<Employee> employees, String name) {
        return employees.stream()
                .filter(employee -> employee.Name().equals(name))
                .map(Employee::phoneNumber)
                .toList();
    }


    //    Добавить метод, который ищет сотрудника по табельному номеру
    static Optional<Employee> findEmployeeById(List<Employee> employees, Integer id) {
        return employees.stream()
                .filter(e -> e.id().equals(id))
                .findFirst();
    }
}
