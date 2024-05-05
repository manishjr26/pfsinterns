package task_Four;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student 
{
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) 
    {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRollNumber() 
    {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) 
    {
        this.rollNumber = rollNumber;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    @Override
    public String toString() 
    {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

public class StudentRecordManager
{
    private static final String FILENAME = "student_records.txt";
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        loadRecordsFromFile();
        
       
        while (true)
        {
            System.out.println("\n\n\t\t\t Student Record Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("\n Enter your choice: ");
            int choice = getUserChoice();

            switch (choice) 
            {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    saveRecordsToFile();
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.err.println("\n\t\t\t Invalid choice! Please try again.");
            }
        }
        
    }

    private static int getUserChoice() 
    {
        try
        {
            return scanner.nextInt();
        } catch (InputMismatchException e)
        {
            scanner.nextLine(); // Clear input buffer
            System.err.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

    private static void loadRecordsFromFile()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(",");
                students.add(new Student(parts[0], Integer.parseInt(parts[1]), parts[2]));
            }
        } catch (IOException e) 
        {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void saveRecordsToFile()
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME)))
        {
            for (Student student : students)
            {
                writer.println(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
            }
        } catch (IOException e)
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void addStudent()
    {
        System.out.print("\nEnter student name: ");
        String name = scanner.next();
        System.out.print("Enter roll number: ");
        int rollNumber = getUserChoice();
        System.out.print("Enter grade: ");
        String grade = scanner.next();
        
        // Check if student with the same roll number already exists
        boolean studentExists = false;
        for (Student student : students) 
        {
            if (student.getRollNumber() == rollNumber) 
            {
                studentExists = true;
                break;
            }
        }
        
        // If student exists, display message and return
        if (studentExists) 
        {
            System.err.println("\n\t\t\t Student with the same roll number already exists!");
            return;
        }
        
        // If student doesn't exist, add the new student
        students.add(new Student(name, rollNumber, grade));
        System.out.println("\n\t\t\t Student added successfully!\n");
    }


    private static void viewAllStudents()
    {
        if (students.isEmpty())
        {
            System.out.println("No students found.");
        } else 
        {
            for (Student student : students) 
            {
                System.out.println(student);
            }
        }
    }

    private static void updateStudent() 
    {
        System.out.print("\nEnter the roll number of the student to update: ");
        int rollNumberToUpdate = getUserChoice();
        boolean found = false;

        for (Student student : students)
        {
            if (student.getRollNumber() == rollNumberToUpdate)
            {
                System.out.println("Enter updated details for the student:");
                System.out.print("\nNew name: ");
                String newName = scanner.next();
                System.out.print("\nNew roll number: ");
                int newRollNumber = getUserChoice();
                System.out.print("\nNew grade: ");
                String newGrade = scanner.next();

                student.setName(newName);
                student.setRollNumber(newRollNumber);
                student.setGrade(newGrade);
                found = true;
                System.out.println("\n\t\t\t\t Student details updated successfully!");
                break;
            }
        }

        if (!found) 
        {
            System.err.println("\n\t\t\t Student with roll number " + rollNumberToUpdate + " not found.");
        }
    }

    private static void deleteStudent() 
    {
        System.out.print("\n Enter the roll number of the student to delete: ");
        int rollNumberToDelete = getUserChoice();
        boolean found = false;

        for (Student student : students) 
        {
            if (student.getRollNumber() == rollNumberToDelete) 
            {
                students.remove(student);
                found = true;
                System.out.println("\n \t\t\t Student deleted successfully!");
                break;
            }
        }

        if (!found) 
        {
            System.err.println("\n\t\t\t Student with roll number " + rollNumberToDelete + " not found.");
        }
    }

}

