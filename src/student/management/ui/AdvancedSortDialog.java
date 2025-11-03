package student.management.ui;

import student.management.object.Student;

import javax.swing.*;
import java.awt.*;

import static student.management.Main.Students;

public class AdvancedSortDialog extends JDialog {

    // Counter for tracking sorting priority
    private int order = 1;

    public AdvancedSortDialog(JFrame parent) {
        super(parent, "Advanced Sort", true);
        setSize(300, 250);
        setResizable(false);
        setLocation(parent.getX() + parent.getWidth() / 2 - getWidth() / 2, parent.getY() + parent.getHeight() / 2 - getHeight() / 2);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add sort options for each student attribute
        AddInputs(buttonPanel, Student.Attribute.ID);
        AddInputs(buttonPanel, Student.Attribute.SURNAME);
        AddInputs(buttonPanel, Student.Attribute.OTHERNAMES);
        AddInputs(buttonPanel, Student.Attribute.GENDER);
        AddInputs(buttonPanel, Student.Attribute.AGE);

        // The OK button sorts the student list and also close the modal.
        JButton okButton = new JButton("OK");
        okButton.setFocusable(false);
        okButton.setBackground(Color.decode("#DDDDDD"));
        okButton.addActionListener(ee -> {
            Students.Sort();
            dispose();
        });

        // build the panels
        JPanel panel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        bottomPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void AddInputs(JPanel buttonPanel, Student.Attribute attribute) {
        // create the button and the combobox for each student attribute.
        String attributeName = attribute.getName();
        JButton Check = new JButton(attributeName);
        Check.setFocusable(false);
        Check.setBackground(Color.WHITE);
        JComboBox<String> Order = new JComboBox<>();
        Order.setEnabled(false);
        Order.addItem("Ascending");
        Order.addItem("Descending");

        // when clicking an attribute check button, assign the attribute to the first empty sort attribute.
        Check.addActionListener(e -> {
            Check.setText(attributeName + " (" + order++ + ")");
            Check.setBackground(Color.LIGHT_GRAY);
            Check.setEnabled(false);
            Order.setEnabled(true);
            // assign sort attribute to the first empty slot
            if (Students.SortAttribute1.isEmpty()) Students.SortAttribute1 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute2.isEmpty()) Students.SortAttribute2 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute3.isEmpty()) Students.SortAttribute3 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute4.isEmpty()) Students.SortAttribute4 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute5.isEmpty()) Students.SortAttribute5 = attributeName + "/" + Order.getSelectedItem();
        });

        // update the sort attribute of the current attribute to set it as either ascending or descending from order.
        Order.addItemListener(e -> {
            if (Students.SortAttribute1.contains(attributeName)) Students.SortAttribute1 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute2.contains(attributeName)) Students.SortAttribute2 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute3.contains(attributeName)) Students.SortAttribute3 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute4.contains(attributeName)) Students.SortAttribute4 = attributeName + "/" + Order.getSelectedItem();
            else if (Students.SortAttribute5.contains(attributeName)) Students.SortAttribute5 = attributeName + "/" + Order.getSelectedItem();
        });

        buttonPanel.add(Check);
        buttonPanel.add(Order);
    }
}
