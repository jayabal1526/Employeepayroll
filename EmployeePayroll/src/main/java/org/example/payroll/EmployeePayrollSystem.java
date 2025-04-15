package org.example.payroll;

import org.example.payroll.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeePayrollSystem {
    private static final String FILE_NAME = "employees.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employeeList = loadEmployeesFromFile();

        while (true) {
            System.out.println("\n--- Employee Payroll System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee Salary");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Basic Salary: ");
                    double salary = scanner.nextDouble();

                    Employee emp = new Employee(id, name, salary);
                    employeeList.add(emp);
                    saveEmployeeToFile(emp);
                    System.out.println("Employee added and saved successfully.");
                    break;

                case 2:
                    System.out.println("\n--- Employee List ---");
                    for (Employee e : employeeList) {
                        e.displayEmployee();
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID to search: ");
                    int searchId = scanner.nextInt();
                    boolean found = false;
                    for (Employee e : employeeList) {
                        if (e.getId() == searchId) {
                            e.displayEmployee();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Employee ID to update salary: ");
                    int updateId = scanner.nextInt();
                    boolean updated = false;
                    for (int i = 0; i < employeeList.size(); i++) {
                        Employee e = employeeList.get(i);
                        if (e.getId() == updateId) {
                            System.out.print("Enter new Basic Salary: ");
                            double newSalary = scanner.nextDouble();
                            employeeList.set(i, new Employee(e.getId(), e.getName(), newSalary));
                            saveAllEmployeesToFile(employeeList);
                            System.out.println("Salary updated.");
                            updated = true;
                            break;
                        }
                    }
                    if (!updated) {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteId = scanner.nextInt();
                    boolean deleted = employeeList.removeIf(e -> e.getId() == deleteId);
                    if (deleted) {
                        saveAllEmployeesToFile(employeeList);
                        System.out.println("Employee deleted.");
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void saveEmployeeToFile(Employee emp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(emp.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving employee: " + e.getMessage());
        }
    }

    private static void saveAllEmployeesToFile(ArrayList<Employee> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : list) {
                writer.write(emp.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    private static ArrayList<Employee> loadEmployeesFromFile() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                employees.add(Employee.fromFileString(line));
            }
        } catch (FileNotFoundException e) {
            // File may not exist initially — that’s okay
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return employees;
    }
}