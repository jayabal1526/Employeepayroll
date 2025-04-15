package org.example.payroll;

public class Employee {
    private int id;
    private String name;
    private double basicSalary;

    public Employee(int id, String name, double basicSalary) {
        this.id = id;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double calculateGrossSalary() {
        double hra = 0.2 * basicSalary;
        double da = 0.1 * basicSalary;
        return basicSalary + hra + da;
    }

    public void displayEmployee() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Gross Salary: " + calculateGrossSalary());
        System.out.println("----------------------------");
    }

    public String toFileString() {
        return id + "," + name + "," + basicSalary;
    }

    public static Employee fromFileString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        double salary = Double.parseDouble(parts[2]);
        return new Employee(id, name, salary);
    }
}