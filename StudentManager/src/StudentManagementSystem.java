import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentManagementSystem extends JFrame {

    private ArrayList<Student> studentList;
    private ArrayList<Department> departmentList;

    private JTextField rollField, nameField, addressField, phoneField;
    private JComboBox<String> deptComboBox;
    private JButton addButton, searchButton, editButton, deleteButton, displayButton;
    private JTable studentTable;
    public StudentManagementSystem() {
        studentList = new ArrayList<>();
        departmentList = new ArrayList<>();
        departmentList.add(new Department("D1", "Computer Science"));
        departmentList.add(new Department("D2", "Electrical Engineering"));
        departmentList.add(new Department("D3", "Civil Engineering"));
        departmentList.add(new Department("D4", "Mechanical Engineering"));
        departmentList.add(new Department("D5", "Electronics Engineering"));



        setLayout(null);

//        String[] columnNames = {"Roll", "Name", "DeptCode", "Dept Name", "Address", "Phone"};
//        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
//        studentTable = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(studentTable);
//        scrollPane.setBounds(20, 220, 800, 150);
//        add(scrollPane);

        JLabel rollLabel = new JLabel("Roll:");
        rollLabel.setBounds(20, 20, 80, 25);
        add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(100, 20, 150, 25);
        add(rollField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 50, 80, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 50, 150, 25);
        add(nameField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 80, 80, 25);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(100, 80, 150, 25);
        add(addressField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 110, 80, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(100, 110, 150, 25);
        add(phoneField);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setBounds(20, 140, 80, 25);
        add(deptLabel);

        String[] deptNames = new String[departmentList.size()];
        for (int i = 0; i < departmentList.size(); i++) {
            deptNames[i] = departmentList.get(i).getDeptName();
        }
        deptComboBox = new JComboBox<>(deptNames);
        deptComboBox.setBounds(100, 140, 150, 25);
        add(deptComboBox);

        addButton = new JButton("Add");
        addButton.setBounds(20, 180, 80, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
                System.out.println("I'm here");
            }
        });
        add(addButton);

        searchButton = new JButton("Search");
        searchButton.setBounds(110, 180, 80, 25);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });
        add(searchButton);

        editButton = new JButton("Edit");
        editButton.setBounds(200, 180, 80, 25);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editStudent();
            }
        });
        add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(290, 180, 80, 25);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        add(deleteButton);

        displayButton = new JButton("Display");
        displayButton.setBounds(380, 180, 80, 25);
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStudents();
            }
        });
        add(displayButton);


        setSize(480, 250); // Increased the height to accommodate the table
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void addStudent() {
        String roll = rollField.getText();

        for (Student stu : studentList) {
            if (roll.equals(stu.getRoll())) {
                showWarningMessage("Warning", "A student already exists with the same roll");
                rollField.setText("");
                return;
            }
        }

        String name = nameField.getText();
        String address = addressField.getText();
        String phoneNo = phoneField.getText();

        if (!isValidPhoneNumber(phoneNo)) {
            showWarningMessage("Warning", "Please provide a valid phone number");
            phoneField.setText("");
            return;
        }

        int selectedIndex = deptComboBox.getSelectedIndex();

        if (selectedIndex == -1) {
            showWarningMessage("Warning", "Please select a department");
            return;
        }
        else {
            System.out.println("phone number is valid");
        }

        String deptCode = departmentList.get(selectedIndex).getDeptCode();
        String deptName = departmentList.get(selectedIndex).getDeptName();

        for (Student stu : studentList) {
            if (name.equals(stu.getName()) && address.equals(stu.getAddress()) &&
                    phoneNo.equals(stu.getPhone()) && deptCode.equals(stu.getDeptCode())) {
                showWarningMessage("Warning", "A student already exists with the same details");
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                return;
            }
        }

        // Add the new student to the list
        studentList.add(new Student(roll, name, deptCode,deptName, address, phoneNo));
        deptComboBox.setSelectedIndex(-1);
        rollField.setText("");
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        System.out.println("Student added successfully");
    }


    private void searchStudent() {
        String roll = rollField.getText();
        if(roll.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please enter a roll no.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Student foundStudent =null;
        for (Student stu:studentList)
        {
            if(roll.equals(stu.getRoll())){
                foundStudent = stu;
            }
        }
        StringBuilder message = new StringBuilder();
        if (foundStudent != null)
        {
            message.append("Roll: ").append(foundStudent.getRoll()).append("\n");
            message.append("Name: ").append(foundStudent.getName()).append("\n");
            message.append("Dept Code: ").append(foundStudent.getDeptCode()).append("\n");
            message.append("Dept Name: ").append(foundStudent.getDeptName()).append("\n");
            message.append("Address: ").append(foundStudent.getAddress()).append("\n");
            message.append("Phone: ").append(foundStudent.getPhone()).append("\n");
            JOptionPane.showMessageDialog(this, message.toString(), "Student Details", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "No student is present of this Roll no.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        rollField.setText("");
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");

    }

    private void editStudent() {
        String roll = rollField.getText();
        if(roll.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please enter a roll no.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Student student = null;
        String phoneNumber = phoneField.getText();
        if(!phoneNumber.equals("")) {
            if (!isValidPhoneNumber(phoneNumber)) {
                showWarningMessage("Warning", "Please provide a valid phone number");
                phoneField.setText("");
                return;
            }
        }
        for (Student stu : studentList) {
            if (roll.equals(stu.getRoll())) {
                student = stu;
                studentList.remove(stu);
                break;
            }
        }
        if(student==null)
        {
            JOptionPane.showMessageDialog(this, "No student is present of this Roll no.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        String name = nameField.getText();
        String address = addressField.getText();

        int selectedIndex = deptComboBox.getSelectedIndex();
        String deptCode  = "";
        String deptName = "";
        if(selectedIndex!=-1)
        {
            deptCode = departmentList.get(selectedIndex).getDeptCode();
            deptName = departmentList.get(selectedIndex).getDeptName();
        }
        rollField.setText("");
        if(!name.equals(""))
        {
            student.setName(name);
            nameField.setText("");
        }
        if(!deptCode.equals(""))
        {
            student.setDeptCode(deptCode);
            deptComboBox.setSelectedIndex(-1);
        }
        if(!deptName.equals(""))
        {
            student.setDeptName(deptName);
        }
        if(!phoneNumber.equals(""))
        {
            student.setPhone(phoneNumber);
            phoneField.setText("");
        }
        if(!address.equals(""))
        {
            student.setAddress(address);
            addressField.setText("");
        }

        studentList.add(student);

    }

    private void deleteStudent() {
        String roll = rollField.getText();
        if(roll.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please enter a roll no.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Student student = null;
        for (Student stu : studentList) {
            if (roll.equals(stu.getRoll())) {
                student = stu;
                studentList.remove(stu);
                break;
            }
        }
        if(student!=null)
        {
            StringBuffer message = new StringBuffer();
            message.append("Roll: ").append(student.getRoll()).append("\n");
            message.append("Name: ").append(student.getName()).append("\n");
            message.append("Dept Code: ").append(student.getDeptCode()).append("\n");
            message.append("Dept Name: ").append(student.getDeptName()).append("\n");
            message.append("Address: ").append(student.getAddress()).append("\n");
            message.append("Phone: ").append(student.getPhone()).append("\n");
            JOptionPane.showMessageDialog(this,message.toString() + "\n Deleted successfully", "Successful Deletion", JOptionPane.INFORMATION_MESSAGE);

            deptComboBox.setSelectedIndex(-1);
            rollField.setText("");
            rollField.setText("");
            nameField.setText("");
            addressField.setText("");
            phoneField.setText("");
            System.out.println("Student deleted successfully");
            return;
        }
        JOptionPane.showMessageDialog(this, "No student is present of this Roll no.", "Error", JOptionPane.ERROR_MESSAGE);

    }

    private int currentIndex = 0;
    private int studentsPerPage = 5;

    private void displayStudents() {
        JPanel buttonPanel = new JPanel();

        JFrame displayFrame = new JFrame("Student Table");
        displayFrame.setBounds(300,300,800,250);
        DefaultTableModel tableModel = new DefaultTableModel();
        for (String column : new String[]{"Roll", "Name", "DeptCode", "Dept Name", "Address", "Phone"}) {
            tableModel.addColumn(column);
        }

        JTable displayTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(displayTable);
        displayFrame.add(scrollPane);

        int start = currentIndex;
        int end = Math.min(currentIndex + studentsPerPage, studentList.size());

        for (int i = start; i < end; i++) {
            Student student = studentList.get(i);
            Object[] rowData = {student.getRoll(), student.getName(), student.getDeptCode(),
                    student.getDeptName(), student.getAddress(), student.getPhone()};
            tableModel.addRow(rowData);
        }
        if(currentIndex>=5) {
            JButton prevButton = new JButton("Previous");
            prevButton.addActionListener(e -> {
                if (currentIndex - studentsPerPage >= 0) {
                    currentIndex -= studentsPerPage;
                    updateTable(tableModel);
                }
            });
            buttonPanel.add(prevButton);
        }
        if(currentIndex<=studentList.size()-5) {
            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(e -> {
                if (currentIndex + studentsPerPage < studentList.size()) {
                    currentIndex += studentsPerPage;
                    updateTable(tableModel);
                }
            });
            buttonPanel.add(nextButton);
        }




        displayFrame.add(buttonPanel, BorderLayout.SOUTH);
        displayFrame.setVisible(true);
    }

    private void updateTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        int start = currentIndex;
        int end = Math.min(currentIndex + studentsPerPage, studentList.size());

        for (int i = start; i < end; i++) {
            Student student = studentList.get(i);
            Object[] rowData = {student.getRoll(), student.getName(), student.getDeptCode(),
                    student.getDeptName(), student.getAddress(), student.getPhone()};
            tableModel.addRow(rowData);
        }
    }



    private void showPrevSet() {

    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[0-9\\s-()]+$";

        return phoneNumber.matches(regex) && phoneNumber.length() == 10;
    }
    private void showNextSet() {
        // Implement logic to show the next set of students
    }
    public static void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementSystem();
            }
        });
    }
}
