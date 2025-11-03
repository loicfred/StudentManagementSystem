package student.management.object;

public class StudentLinkedList {
    private Node Root = null; // the first element of the list.
    private int Size = 0; // the amount of elements in the list.

    public String SortAttribute1 = "";
    public String SortAttribute2 = "";
    public String SortAttribute3 = "";
    public String SortAttribute4 = "";
    public String SortAttribute5 = "";

    // uses default constructor as there are no needs to pass default attributes.

    public int getSize() {
        return Size; // returns the number of items in the list
    }

    public boolean isEmpty() {
        return Root == null; // if 1st element is null, it means the list is empty.
    }

    public void AddStudent(Student newStudent) {
        if (newStudent != null) {
            Node tempL = Root;
            Node newnode = new Node(newStudent);
            if (isEmpty()) { // if list is empty, set new student as the 1st element (root) of the list.
                Root = newnode;
            } else {
                while (tempL.Next != null) {
                    tempL = tempL.Next; // continuously move to the next node until the last one with no Next node.
                }
                tempL.Next = newnode; // then assign the new node as the next node of the last one.
            } Size++; // increment the size of the list by one.
        }
    }
    public void DeleteStudentById(long id) {
        if (!isEmpty()) {
            if (Root.Data.getId() == id) {
                Root = Root.Next; // if the 1st element (root) is the student, then assigns the next node as the root.
                Size--; // decrement the size of the list if found.
            } else {
                Node temp = Root;
                while (temp != null) { // continuously move to the next node until it finds the student.
                    if (temp.Next != null) {
                        if (temp.Next.Data.getId() == id) { // if the next node is the student
                            temp.Next = temp.Next.Next; // then assigns the next-next node as next node.
                            Size--; // decrement the size of the list if found.
                            break; // then exit the search loop
                        } else {
                            temp = temp.Next;
                        }
                    }
                }
            }
        }
    }

    public Node get(int index) {
        if (!isEmpty()) {
            int i = 0;
            Node tempL = Root;
            while (tempL != null) { // go through the list
                if (i++ == index) return tempL; // if the index of the list is equal to one searched, return its node.
                tempL = tempL.Next; // look for the next one
            }
        }
        // if not found, throw an exception.
        throw new IndexOutOfBoundsException("Out of bounds, the required range should be between: 0 - " + (getSize()-1));
    }
    // this is used to search a student by their ID.
    public Student SearchById(long id) { // uses a Bi-linear search algorithm for faster search.
        if (isEmpty()) return null;
        int size = getSize();
        for (int i = 0; i <= size / 2; i++) {
            // search the list on both ends (front and back) till the center.
            if (get(i).Data.getId() == id) return get(i).Data;
            if (get(size-i-1).Data.getId() == id) return get(size-i-1).Data;
        }
        return null;
    }
    // this is used to search a student by their surname.
    public Student SearchBySurname(String surname) { // uses a Bi-linear search algorithm for faster search.
        if (isEmpty()) return null;
        int size = getSize();
        for (int i = 0; i <= size / 2; i++) {
            // search the list on both ends (front and back) till the center.
            if (get(i).Data.getSurname().equalsIgnoreCase(surname)) return get(i).Data;
            if (get(size-i-1).Data.getSurname().equalsIgnoreCase(surname)) return get(size-i-1).Data;
        }
        return null;
    }
    // this is used to search a student by their other names.
    public Student SearchByOtherNames(String otherNames) { // uses a Bi-linear search algorithm for faster search.
        if (isEmpty()) return null;
        int size = getSize();
        for (int i = 0; i <= size / 2; i++) {
            // search the list on both ends (front and back) till the center.
            if (get(i).Data.getOtherNames().equalsIgnoreCase(otherNames)) return get(i).Data;
            if (get(size-i-1).Data.getOtherNames().equalsIgnoreCase(otherNames)) return get(size-i-1).Data;
        }
        return null;
    }

    // this is used to search a student by their surname + other names to see if they exist.
    public Student getStudentByFullName(String surname, String otherNames) {
        if (isEmpty()) return null;
        int size = getSize();
        for (int i = 0; i <= size / 2; i++) {
            // search the list on both ends (front and back) till the center.
            if (get(i).Data.getSurname().equalsIgnoreCase(surname)
                    && get(i).Data.getOtherNames().equalsIgnoreCase(otherNames)) {
                return get(i).Data;
            }
            if (get(size-i-1).Data.getSurname().equalsIgnoreCase(surname)
                    && get(size-i-1).Data.getOtherNames().equalsIgnoreCase(otherNames))
                return get(size-i-1).Data;
        }
        return null;
    }

    // this is a Bubble sort V2 that can order the list either in ascending or descending using the data of our choice.
    public void Sort() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                Node J = get(j);
                Node J2 = get(j + 1);
                if (checkSortRecursive(J, J2, SortAttribute1, 1)) {
                    Student temp = J.Data;
                    J.Data = J2.Data;
                    J2.Data = temp;
                }
            }
        }
    }

    private boolean checkSortRecursive(Node J, Node J2, String sortAttribute, int level) {
        if (level > 5 || sortAttribute.isEmpty()) return false;
        // check if attributes need swap
        if ((sortAttribute.contains(Student.Attribute.ID.getName()) && compareID(J, J2, sortAttribute.contains("Ascending")))
                || (sortAttribute.contains(Student.Attribute.SURNAME.getName()) && compareSurname(J, J2, sortAttribute.contains("Ascending")))
                || (sortAttribute.contains(Student.Attribute.OTHERNAMES.getName()) && compareOther(J, J2, sortAttribute.contains("Ascending")))
                || (sortAttribute.contains(Student.Attribute.GENDER.getName()) && compareGender(J, J2, sortAttribute.contains("Ascending")))
                || (sortAttribute.contains(Student.Attribute.AGE.getName()) && compareAge(J, J2, sortAttribute.contains("Ascending")))) {
            return true;
        }
        // check if attributes are equal
        else if ((sortAttribute.contains(Student.Attribute.ID.getName()) && J.Data.getId() == J2.Data.getId())
                || (sortAttribute.contains(Student.Attribute.SURNAME.getName()) && J.Data.getSurname().equals(J2.Data.getSurname()))
                || (sortAttribute.contains(Student.Attribute.OTHERNAMES.getName()) && J.Data.getOtherNames().equals(J2.Data.getOtherNames()))
                || (sortAttribute.contains(Student.Attribute.GENDER.getName()) && J.Data.getGender().equals(J2.Data.getGender()))
                || (sortAttribute.contains(Student.Attribute.AGE.getName()) && J.Data.getAge() == J2.Data.getAge())) {
            // check the next sort attribute recursively
            return switch (level) {
                case 1 -> checkSortRecursive(J, J2, SortAttribute2, 2);
                case 2 -> checkSortRecursive(J, J2, SortAttribute3, 3);
                case 3 -> checkSortRecursive(J, J2, SortAttribute4, 4);
                case 4 -> checkSortRecursive(J, J2, SortAttribute5, 5);
                default -> false;
            };
        }
        return false;
    }

    public boolean compareID(Node J, Node J2, boolean ascending) {
        return ((ascending && J.Data.getId() > J2.Data.getId()) || (!ascending && J.Data.getId() < J2.Data.getId()));
    }
    public boolean compareAge(Node J, Node J2, boolean ascending) {
        return ((ascending && J.Data.getAge() > J2.Data.getAge()) || (!ascending && J.Data.getAge() < J2.Data.getAge()));
    }
    public boolean compareSurname(Node J, Node J2, boolean ascending) {
        return ((ascending && J.Data.getSurname().compareTo(J2.Data.getSurname()) > 0) || (!ascending && J.Data.getSurname().compareTo(J2.Data.getSurname()) < 0));
    }
    public boolean compareOther(Node J, Node J2, boolean ascending) {
        return ((ascending && J.Data.getOtherNames().compareTo(J2.Data.getOtherNames()) > 0) || (!ascending && J.Data.getOtherNames().compareTo(J2.Data.getOtherNames()) < 0));
    }
    public boolean compareGender(Node J, Node J2, boolean ascending) {
        return ((ascending && J.Data.getGender().compareTo(J2.Data.getGender()) > 0) || (!ascending && J.Data.getGender().compareTo(J2.Data.getGender()) < 0));
    }

    public static class Node {
        public Student Data; // the node containing the student's data.
        public Node Next = null; // the node with the next student of the list, null by default.
        public Node(Student data){
            this.Data = data;
        }
    }
}