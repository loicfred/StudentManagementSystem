package student.management.ui;

import javax.swing.*;
import java.awt.*;

import static student.management.Main.Students;
import static student.management.ui.MainWindow.isNumberValid;

public class NewStudentDialog extends JDialog {

    // all the inputs for the attributes of a Student
    public JTextField ID;
    public JTextField Surname;
    public JTextField OtherNames;
    public JTextField Age;
    public JComboBox<String> Gender;

    // dialog constructor
    public NewStudentDialog(JFrame parent) {
        super(parent, "New Student", true);
        setSize(300,200);
        setResizable(false);
        setLocation(parent.getX()+parent.getWidth()/2-getWidth()/2, parent.getY()+parent.getHeight()/2-getHeight()/2);

        // initializing the input fields
        ID = new JTextField();
        Surname = new JTextField();
        OtherNames = new JTextField();
        Gender = new JComboBox<>();
        Gender.addItem("Male");
        Gender.addItem("Female");
        Age = new JTextField();


        // the inputs panel
        JPanel Center = new JPanel();
        Center.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        Center.setPreferredSize(new Dimension(250,200));
        Center.setLayout(new GridLayout(5, 2));
        Center.add(new JLabel("ID:"));
        Center.add(ID);
        Center.add(new JLabel("Student surname:"));
        Center.add(Surname);
        Center.add(new JLabel("Student other names:"));
        Center.add(OtherNames);
        Center.add(new JLabel("Student gender:"));
        Center.add(Gender);
        Center.add(new JLabel("Student age:"));
        Center.add(Age);


        // initializing buttons
        JPanel Down = new JPanel();
        JButton OK = new JButton("OK");
        OK.addActionListener(e -> {
            // doing data validation to see if the inputs are of the right format and not duplicate
            // as per the requirements
            if (CheckStudentInputFormat(this, ID, Surname, OtherNames, Age)) {
                if (Students.getStudentByFullName(Surname.getText(), OtherNames.getText()) != null)
                    JOptionPane.showMessageDialog(parent, "There is already a student with the same surname and other names.");
                else if (Students.SearchById(Long.parseLong(ID.getText())) != null)
                    JOptionPane.showMessageDialog(parent, "ID should be unique.");
                else dispose();
            }
        });
        Down.add(OK);

        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BorderLayout());
        MainPanel.add(Center, BorderLayout.CENTER);
        MainPanel.add(Down, BorderLayout.SOUTH);
        add(MainPanel);
        setVisible(true);
    }

    public static boolean CheckStudentInputFormat(Container parent, JTextField ID, JTextField Surname, JTextField OtherNames, JTextField Age) {
        if (ID.getText().isEmpty()) JOptionPane.showMessageDialog(parent, "ID shouldn't be empty.");
        else if (Surname.getText().isEmpty()) JOptionPane.showMessageDialog(parent, "Surname shouldn't be empty.");
        else if (OtherNames.getText().isEmpty()) JOptionPane.showMessageDialog(parent, "Other names shouldn't be empty.");
        else if (Age.getText().isEmpty()) JOptionPane.showMessageDialog(parent, "Age shouldn't be empty.");
        else if (isNumberValid(ID.getText())) {
            if (isNumberValid(Age.getText())) return true;
            else JOptionPane.showMessageDialog(parent, "Age should be an integer.");
        } else JOptionPane.showMessageDialog(parent, "ID should be an integer.");
        return false;
    }
}
