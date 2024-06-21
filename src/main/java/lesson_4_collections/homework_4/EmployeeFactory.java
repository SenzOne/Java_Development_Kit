package lesson_4_collections.homework_4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EmployeeFactory {


    private static <T> T getRandom(List<? extends T> items) {
        return items.get(ThreadLocalRandom.current().nextInt(0, items.size()));
    }

    private static String generatePhoneNumber() {
        int areaCode = ThreadLocalRandom.current().nextInt(100, 1000);
        int centralOfficeCode = ThreadLocalRandom.current().nextInt(100, 1000);
        int lineNumber = ThreadLocalRandom.current().nextInt(1000, 10000);
        return String.format("%03d-%03d-%04d", areaCode, centralOfficeCode, lineNumber);
    }

    public static List<Employee> createEmployee(int countEmployee) {
        List<String> names = List.of("John Doe", "Jane Smith", "Alice Johnson", "Bob Brown",
                "Charlie Davis", "Dana Alexander", "Robert Valdez", "Laura Johnson");

        List<Employee> employees = new ArrayList<>(countEmployee);
        for (int i = 0; i < countEmployee; i++) {
            Integer work_exp = ThreadLocalRandom.current().nextInt(0, 20);
            employees.add(new Employee(i, generatePhoneNumber(), getRandom(names), work_exp));
        }
        return employees;
    }
}
