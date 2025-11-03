package student.management;

import student.management.object.Student;
import student.management.object.StudentLinkedList;
import student.management.ui.MainWindow;

// This code has been written by Cheerkoot Loic Fred.
public class Main {

    // The main static object which will be used as the list throughout the program.
    public static StudentLinkedList Students = new StudentLinkedList();

    public static void main(String[] args) {
        Students.AddStudent(new Student(1, "Cheerkoot", "Loic Fred", "Male", 20));
        Students.AddStudent(new Student(2, "Ajay", "Dooshan", "Male", 20));
        Students.AddStudent(new Student(3, "Harry", "Buckhory", "Male", 20));
        Students.AddStudent(new Student(4, "Brijraj", "Diya Devi", "Female", 21));
        Students.AddStudent(new Student(5, "David", "Helene Jordan", "Male", 20));
        Students.AddStudent(new Student(6, "Tesci", "Fifi", "Female", 20));
        Students.AddStudent(new Student(7, "Zaynab", "Bibi", "Female", 20));
        Students.AddStudent(new Student(8, "Adisayam", "Morane", "Female", 20));
        Students.AddStudent(new Student(9, "Bancha", "Narvish", "Male", 21));
        Students.AddStudent(new Student(10, "Keshav", "Ancharruz", "Male", 21));
        // create and open the JFrame
        new MainWindow();
    }

}
