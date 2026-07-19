import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentInformationSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    running = false;
                    System.out.println("Thank you for using the Student Information System!");
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== STUDENT INFORMATION SYSTEM ===");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
    }

    // ---------- CORE FEATURES ----------

    private static void addStudent() {
        System.out.println("\n=== ADD NEW STUDENT ===");

        String studentId = readLine("Enter Student ID: ");
        if (findStudent(studentId) != null) {
            System.out.println("⚠️  A student with that ID already exists!");
            return;
        }

        String name = readLine("Enter Name: ");
        int age = readInt("Enter Age: ");
        double grade = readDouble("Enter Grade (0-100): ");
        String contact = readLine("Enter Contact: ");

        Student student = new Student(studentId, name, age, grade, contact);
        students.add(student);
        System.out.println("✅ Student added successfully!");
    }

    private static void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        System.out.printf("%-12s %-20s %-6s %-8s %-20s%n",
                "Student ID", "Name", "Age", "Grade", "Contact");
        System.out.println("-".repeat(70));

        for (Student s : students) {
            System.out.println(s.toRow());
        }
    }

    private static void searchStudent() {
        System.out.println("\n=== SEARCH STUDENT ===");
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        String query = readLine("Enter Student ID or Name: ").trim().toLowerCase();
        boolean found = false;

        for (Student s : students) {
            if (s.getStudentId().toLowerCase().equals(query)
                    || s.getName().toLowerCase().contains(query)) {
                System.out.println("\nFound Student:");
                s.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching student found.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n=== UPDATE STUDENT ===");
        String id = readLine("Enter Student ID to update: ");
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("No student found with that ID.");
            return;
        }

        student.displayInfo();
        System.out.println("Which field do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Age");
        System.out.println("3. Grade");
        System.out.println("4. Contact");
        System.out.println("5. Cancel");

        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> student.setName(readLine("Enter new Name: "));
            case 2 -> student.setAge(readInt("Enter new Age: "));
            case 3 -> student.setGrade(readDouble("Enter new Grade: "));
            case 4 -> student.setContact(readLine("Enter new Contact: "));
            case 5 -> System.out.println("Update cancelled.");
            default -> System.out.println("Invalid choice.");
        }

        if (choice >= 1 && choice <= 4) {
            System.out.println("✅ Student updated successfully!");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n=== DELETE STUDENT ===");
        String id = readLine("Enter Student ID to delete: ");
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("No student found with that ID.");
            return;
        }

        String confirm = readLine("Are you sure you want to delete " + student.getName() + "? (y/n): ");
        if (confirm.equalsIgnoreCase("y")) {
            students.remove(student);
            System.out.println("✅ Student deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // ---------- HELPERS ----------

    private static Student findStudent(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Please enter a valid number.");
            }
        }
    }
}
