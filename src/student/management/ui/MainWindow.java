package student.management.ui;

import student.management.object.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static student.management.Main.Students;
import static student.management.ui.NewStudentDialog.CheckStudentInputFormat;

public class MainWindow extends JFrame {

    public JLabel StudentList = new JLabel("0 Students ~ "); // label for the student count.
    public JPanel DynamicPanel = new JPanel(); // panel which can display either edit window or list.

    public HButton IDSortBTN = new HButton(this, Student.Attribute.ID.getName());
    public HButton SurnameSortBTN = new HButton(this, Student.Attribute.SURNAME.getName());
    public HButton OtherNamesSortBTN = new HButton(this, Student.Attribute.OTHERNAMES.getName());
    public HButton GenderSortBTN = new HButton(this, Student.Attribute.GENDER.getName());
    public HButton AgeSortBTN = new HButton(this, Student.Attribute.AGE.getName());

    public String search = "";

    public MainWindow() {
        super("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);
        setResizable(false);
        setLocationRelativeTo(null); // to center the window on the screen.

        // uses a menu bar to place the Add and Search button.
        JMenuBar menuBar = new JMenuBar();
        JMenu Manage = new JMenu("Manage");

        // is used to open a new modal dialog box to create a new student
        // then adds it to the list
        JMenuItem Add = new JMenuItem("Add Student");
        Add.addActionListener(AddStudentClick());

        // opens an input dialog to enter a student id or name or surname and search for the student to display its data to edit.
        JMenu Search = new JMenu("Search Student");
        JMenuItem ByID = new JMenuItem("By ID");
        JMenuItem BySurname = new JMenuItem("By Surname");
        JMenuItem ByOtherNames = new JMenuItem("By Other names");
        ByID.addActionListener(SearchClick(Student.Attribute.ID));
        BySurname.addActionListener(SearchClick(Student.Attribute.SURNAME));
        ByOtherNames.addActionListener(SearchClick(Student.Attribute.OTHERNAMES));

        JMenuItem AdvancedSort = new JMenuItem("Advanced Sort");
        AdvancedSort.addActionListener(AdvancedSortClick());
        
        Search.add(ByID);
        Search.add(BySurname);
        Search.add(ByOtherNames);
        menuBar.add(Manage);
        Manage.add(Add);
        Manage.add(Search);
        Manage.add(AdvancedSort);
        setJMenuBar(menuBar);

        // the search bar is used to limit the students which appear.
        JTextField SearchBar = new JTextField();
        SearchBar.setToolTipText("Enter a student's surname or other names to filter the list.");
        SearchBar.setPreferredSize(new Dimension(350, 28));
        SearchBar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                search = SearchBar.getText().toLowerCase();
                if (Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) displayStudentList();
            }
        });

        // clears current column sort symbols, opens a dialog for the advanced sort window then display the list.
        JButton AdvSort = new JButton("Advanced Sort");
        AdvSort.setToolTipText("Sort the list by using multiple attributes in ascending or descending order.");
        AdvSort.setFocusable(false);
        AdvSort.setBackground(Color.decode("#DDDDDD"));
        AdvSort.addActionListener(AdvancedSortClick());
        
        // build the panels
        JPanel Top = new JPanel();
        Top.setLayout(new FlowLayout());
        Top.add(StudentList);
        Top.add(SearchBar);
        Top.add(AdvSort);

        DynamicPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        displayStudentList();

        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BorderLayout());
        MainPanel.add(Top, BorderLayout.NORTH);
        MainPanel.add(DynamicPanel, BorderLayout.CENTER);
        add(MainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // The panel where you can edit a student
    public void displayStudentEditor(Student student) {
        DynamicPanel.removeAll();
        // initializing inputs
        JTextField ID = new JTextField(student.getId() + "");
        ID.setEnabled(false); // ID shouldn't be editable
        JTextField Surname = new JTextField(student.getSurname());
        JTextField OtherNames = new JTextField(student.getOtherNames());
        JComboBox<String> Gender = new JComboBox<>();
        Gender.addItem("Male");
        Gender.addItem("Female");
        Gender.setSelectedItem(student.getGender());
        JTextField Age = new JTextField(student.getAge() + "");

        JButton Cancel = new JButton("Cancel");
        Cancel.setFocusable(false);
        Cancel.setBackground(Color.decode("#DDDDDD"));
        JButton Save = new JButton("Save");
        Save.setFocusable(false);
        Save.setBackground(Color.decode("#DDDDDD"));
        JButton Delete = new JButton("Delete");
        Delete.setFocusable(false);
        Delete.setBackground(Color.decode("#DDDDDD"));

        Cancel.addActionListener(e -> displayStudentList());
        // save the changes made in the input fields while checking if they are valid.
        Save.addActionListener(e -> {
            if (CheckStudentInputFormat(this, ID, Surname, OtherNames, Age)) {
                Student existingStudent = Students.getStudentByFullName(Surname.getText(), OtherNames.getText());
                if (existingStudent != null && existingStudent != student) {
                    JOptionPane.showMessageDialog(this, "There is already a student with the same surname and other names.");
                } else {
                    student.setSurname(Surname.getText());
                    student.setOtherNames(OtherNames.getText());
                    student.setGender(Gender.getSelectedItem().toString());
                    student.setAge(Integer.parseInt(Age.getText()));
                    displayStudentList();
                }
            }
        });
        Delete.addActionListener(e -> { // a confirmation dialog to confirm if the user really wants to delete.
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + student.getSurname() + "?") == 0) {
                Students.DeleteStudentById(student.getId());
                displayStudentList();
            }
        });

        // build the panels
        JPanel Center = new JPanel();
        Center.setPreferredSize(new Dimension(450, 140));
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

        JPanel Down = new JPanel();
        Down.add(Cancel);
        Down.add(Save);
        Down.add(Delete);

        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BorderLayout());
        MainPanel.add(new JLabel("• You are currently editing " + student.getSurname() + " " + student.getOtherNames() + "."), BorderLayout.NORTH);
        MainPanel.add(Center, BorderLayout.CENTER);
        MainPanel.add(Down, BorderLayout.SOUTH);
        DynamicPanel.add(MainPanel);
        DynamicPanel.revalidate();
        DynamicPanel.repaint();
    }

    // Refresh the student list panel and counter.
    public void displayStudentList() {
        StudentList.setText(Students.getSize() + " Students ~ ");
        DynamicPanel.removeAll();

        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
        JPanel headerPanel = new JPanel();
        headerPanel.setMaximumSize(new Dimension(this.getWidth()-25, 25));
        headerPanel.setMinimumSize(new Dimension(this.getWidth()-25, 25));
        headerPanel.setLayout(new GridLayout(1, 5));
        headerPanel.setBackground(Color.gray);
        headerPanel.add(IDSortBTN);
        headerPanel.add(SurnameSortBTN);
        headerPanel.add(OtherNamesSortBTN);
        headerPanel.add(GenderSortBTN);
        headerPanel.add(AgeSortBTN);
        headerPanel.add(new JLabel());
        MainPanel.add(headerPanel);

        
        
        // adding each student of the list as rows with panels.
        int StudentSize = Students.getSize();
        for (int i = 0; i < StudentSize; i++) {
            Student S = Students.get(i).Data;
            if (search.isEmpty() || S.getSurname().toLowerCase().contains(search) || S.getOtherNames().toLowerCase().contains(search)
               || (isNumberValid(search) && (S.getId() == Long.parseLong(search) || S.getAge() == Long.parseLong(search)))) {
                JPanel rowPanel = new JPanel();
                rowPanel.setMaximumSize(new Dimension(this.getWidth()-25, 25));
                rowPanel.setMinimumSize(new Dimension(this.getWidth()-25, 25));
                rowPanel.setLayout(new GridLayout(1, 5));
                rowPanel.setBackground(Color.LIGHT_GRAY);

                JButton Edit = new JButton("Edit");
                Edit.setFocusable(false);
                Edit.setBackground(Color.decode("#DDDDDD"));
                Edit.setToolTipText("Edit " + S.getSurname() + " " + S.getOtherNames() + "'s information.");
                Edit.addActionListener(e -> displayStudentEditor(S));

                rowPanel.add(new RLabel(S.getId() + ""));
                rowPanel.add(new RLabel(S.getSurname()));
                rowPanel.add(new RLabel(S.getOtherNames()));
                rowPanel.add(new RLabel(S.getGender()));
                rowPanel.add(new RLabel(S.getAge() + ""));
                rowPanel.add(Edit);
                MainPanel.add(rowPanel);
            }
        }
        JPanel P = new JPanel();
        P.setBackground(Color.LIGHT_GRAY);
        JButton btn = new JButton("Add Student");
        btn.setFocusable(false);
        btn.setBackground(Color.decode("#DDDDDD"));
        btn.setPreferredSize(new Dimension(this.getWidth()-50, 25));
        btn.addActionListener(AddStudentClick());
        P.add(btn);
        MainPanel.add(P);

        // makes the student list a scroll pane.
        JScrollPane scrollPane = new JScrollPane(MainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(this.getWidth()-20, 240)); // optional
        DynamicPanel.add(scrollPane);
        DynamicPanel.revalidate();
        DynamicPanel.repaint();
    }

    public ActionListener AddStudentClick() {
        return e -> {
            try {
                NewStudentDialog D = new NewStudentDialog(this);
                Student S = new Student(Long.parseLong(D.ID.getText()), D.Surname.getText(), D.OtherNames.getText(),
                        D.Gender.getSelectedItem().toString(), Integer.parseInt(D.Age.getText()));
                Students.AddStudent(S);
                displayStudentList();
                ClearButtonSymbols();
            } catch (Exception ignored) {
                // if the dialog is closed prematurely with empty values, does nothing.
            }
        };
    }
    public ActionListener SearchClick(Student.Attribute attribute) {
        return e -> {
            try {
                String input = JOptionPane.showInputDialog(this, "Enter student " + attribute.getName(), null);
                if (input == null) return;
                Student S = attribute.equals(Student.Attribute.ID) ? Students.SearchById(Long.parseLong(input)) :
                        attribute.equals(Student.Attribute.SURNAME) ? Students.SearchBySurname(input) : Students.SearchByOtherNames(input);
                if (S != null) displayStudentEditor(S);
                else JOptionPane.showMessageDialog(this, "Student not found.");
            } catch (NumberFormatException ignored) { // if the input isn't a number when the attribute is ID, returns an error message.
                JOptionPane.showMessageDialog(this, "This is not an " + attribute.getName() + ".");
            }
        };
    }
    public ActionListener AdvancedSortClick() {
        return e -> {
            ClearButtonSymbols();
            new AdvancedSortDialog(this);
            displayStudentList();
        };
    }

    public static boolean isNumberValid(String number) {
        try {
            Double.parseDouble(number);
            return true; // if the parse is successful, it means it is indeed a number
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Refresh the sort attributes and buttons.
    public void ClearButtonSymbols() {
        Students.SortAttribute1 = "";
        Students.SortAttribute2 = "";
        Students.SortAttribute3 = "";
        Students.SortAttribute4 = "";
        Students.SortAttribute5 = "";
        IDSortBTN.clear();
        SurnameSortBTN.clear();
        OtherNamesSortBTN.clear();
        GenderSortBTN.clear();
        AgeSortBTN.clear();
    }

    // H stands for Header Button. A custom button with a gray background.
    public static class HButton extends JButton {
        JLabel NameLabel = new JLabel();
        JLabel SymbolLabel = new JLabel();
        boolean toAscending = true;
        public HButton(MainWindow window, String attribute) {
            setLayout(new BorderLayout());
            setBackground(Color.gray);
            setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            setFocusable(false);
            setToolTipText("Click to sort by " + attribute + ".");
            NameLabel.setText(attribute);
            add(NameLabel, BorderLayout.WEST);
            add(SymbolLabel, BorderLayout.EAST);
            addActionListener(e -> {
                window.ClearButtonSymbols();
                Students.SortAttribute1 = attribute + "/" + (toAscending ? "Ascending" : "Descending");
                if (toAscending) SymbolLabel.setText("↓");
                else SymbolLabel.setText("↑");
                Students.Sort();
                toAscending = !toAscending;
                setBackground(Color.decode("#999999"));
                window.displayStudentList();
            });
        }
        public void clear() {
            SymbolLabel.setText("");
            setBackground(Color.gray);
        }
    }

    // R stands for Row Label. A custom label where the text is not bold.
    public static class RLabel extends JLabel {
        public RLabel(String text) {
            super(text);
            setFont(new Font("Arial", Font.PLAIN, 12));
        }
    }
}
