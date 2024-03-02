import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class StudentManagementSystem extends JFrame {

    private ArrayList<Department> departmentList;
    RandomAccessFile fileStudent;
    private JTextField rollField, nameField, addressField, phoneField;
    private JComboBox<String> deptComboBox;
    private JSpinner displayField;
    private JButton addButton, searchButton, editButton, deleteButton, displayButton;
    private int studentObjectSize = 512;
    private  int deleteCount = 1;
    private int maxLogicalDeletionLimit = 0;

    String filePath1;
    public StudentManagementSystem() throws IOException {
        departmentList = new ArrayList<>();
        filePath1 = "studentData.bin";
        fileStudent = new RandomAccessFile(filePath1, "rws");


//        File fStudent = new File(filePath1);

//        if (fStudent.exists()) {
//            // Attempt to delete the file
//            if (fStudent.delete()) {
//                System.out.println("File deleted successfully.");
//            } else {
//                System.out.println("Failed to delete the file.");
//            }
//        } else {
//            System.out.println("File does not exist.");
//        }



        departmentList.add(new Department("D1", "Computer Science"));
        departmentList.add(new Department("D2", "Electrical Engineering"));
        departmentList.add(new Department("D3", "Civil Engineering"));
        departmentList.add(new Department("D4", "Mechanical Engineering"));
        departmentList.add(new Department("D5", "Electronics Engineering"));
        departmentList.add(new Department("D6", "Pharmaceuticals Engineering And Technology"));



        setLayout(null);

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

        JLabel displayLablel = new JLabel("Set students per page ");
        displayLablel.setBounds(300,100,150,25);
        add(displayLablel);

        SpinnerNumberModel model = new SpinnerNumberModel(5, 1, Integer.MAX_VALUE, 1);
        displayField = new JSpinner(model);
        displayField.setBounds(330,130,50,25);
        add(displayField);



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
                try {
                    addStudent();
                } catch (IOException  | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("I'm here");
            }
        });
        add(addButton);

        searchButton = new JButton("Search");
        searchButton.setBounds(110, 180, 80, 25);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchStudent();
                    System.out.println(" search end...");
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(searchButton);

        editButton = new JButton("Edit");
        editButton.setBounds(200, 180, 80, 25);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editStudent();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(290, 180, 80, 25);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteStudent();
                    deleteCount = getDeleteCount();
                    if(deleteCount>=maxLogicalDeletionLimit)
                    {
                        deleteParmanently();
                        deleteCount = 0;
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(deleteButton);

        displayButton = new JButton("Display");
        displayButton.setBounds(380, 180, 80, 25);
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayStudents();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(displayButton);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    closeFile(fileStudent);
                    System.out.println("File closed successfully");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        setSize(480, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }




    public void addStudent() throws IOException, ClassNotFoundException {
        String roll = rollField.getText();
        if(roll.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please enter a roll no.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        fileStudent.seek(0L);
        boolean rollExists = false;

        while (fileStudent.getFilePointer() < fileStudent.length()) {
            byte[] studentBytes = new byte[studentObjectSize];
            int bytesRead = fileStudent.read(studentBytes);
            byte[] trimmedBytes = Arrays.copyOf(studentBytes, bytesRead);
            Student temp = Student.fromBytes(trimmedBytes);

            if (roll.equals(temp.getRoll())) {
                showWarningMessage("Warning", "A student already exists with the same roll");
                rollField.setText("");
                rollExists = true;
                break;
            }
        }
        if (!rollExists) {
            String name = nameField.getText();
            String address = addressField.getText();
            String phoneNo = phoneField.getText();

            if(name.equals(""))
            {
                showWarningMessage("Warning", "Please enter a name");
                return;
            }
            if(address.equals(""))
            {
                showWarningMessage("Warning", "Please enter a address");
                return;
            }

            if (isValidPhoneNumber(phoneNo)) {
                showWarningMessage("Warning", "Please provide a valid phone number");
                phoneField.setText("");
            } else {
                int selectedIndex = deptComboBox.getSelectedIndex();

                if (selectedIndex == -1) {
                    showWarningMessage("Warning", "Please select a department");
                } else {
                    System.out.println("phone number is valid");

                    String deptCode = departmentList.get(selectedIndex).getDeptCode();
                    String deptName = departmentList.get(selectedIndex).getDeptName();

                    Student student = new Student(roll, name, deptCode, deptName, address, phoneNo);

                    byte[] studentBytes = student.toBytes();
                    System.out.println("student bytes = " + studentBytes.length);

                    int len = studentBytes.length;
                    byte[] temp = new byte[studentObjectSize];
                    int i =0;
                    while (i<len)
                    {
                        temp[i] = studentBytes[i++];
                    }
                    while (i<studentObjectSize){
                        temp[i++] = (byte) 0;
                    }



                    fileStudent.write(temp);
                    deptComboBox.setSelectedIndex(-1);
                    rollField.setText("");
                    nameField.setText("");
                    addressField.setText("");
                    phoneField.setText("");

                    System.out.println("Student added successfully");
                }
            }
        }
        System.out.println("file pointer at end = " + fileStudent.getFilePointer());
    }




    private void searchStudent() throws IOException, ClassNotFoundException {
        String roll = rollField.getText();

        if (roll.equals("")) {
            System.out.println("Roll is not entered");
            return;
        }

        fileStudent.seek(0);
        Student student = null;
        boolean rollExists = false;
        while (fileStudent.getFilePointer() < fileStudent.length()) {
            byte[] studentBytes = new byte[studentObjectSize];
            int bytesRead = fileStudent.read(studentBytes);

            if (bytesRead < studentObjectSize) {
                System.out.println("returning from here "+bytesRead);
                break;
            }

            byte[] trimmedBytes = Arrays.copyOf(studentBytes, bytesRead);
            student = Student.fromBytes(trimmedBytes);
            if(roll.equals(student.getRoll()))
            {
                rollField.setText("");
                rollExists = true;
                break;
            }
        }
        if (!rollExists)
        {
            JOptionPane.showMessageDialog(this, "Student not found", "Student Details", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Student not found");
            return;
        }

        if (!student.getIsDeleted()) {
            String message = "Roll: " + student.getRoll() + "\n" +
                    "Name: " + student.getName() + "\n" +
                    "Dept Code: " + student.getDeptCode() + "\n" +
                    "Dept Name: " + student.getDeptName() + "\n" +
                    "Address: " + student.getAddress() + "\n" +
                    "Phone: " + student.getPhone() + "\n";
            JOptionPane.showMessageDialog(this, message, "Student Details", JOptionPane.INFORMATION_MESSAGE);

            rollField.setText("");
            nameField.setText("");
            addressField.setText("");
            phoneField.setText("");
            System.out.println("Student found = " + student.getName());
        } else {
            JOptionPane.showMessageDialog(this, "Student not found", "Student Details", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Student not found");
        }
    }


    private void editStudent() throws IOException, ClassNotFoundException {
        String roll = rollField.getText();
        if(roll.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please enter a roll no.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean rollExists = false;
        Student student = null;
        fileStudent.seek(0L);
        while (fileStudent.getFilePointer() < fileStudent.length()) {
            byte[] studentBytes = new byte[studentObjectSize];
            int bytesRead = fileStudent.read(studentBytes);
            byte[] trimmedBytes = Arrays.copyOf(studentBytes, bytesRead);
            student = Student.fromBytes(trimmedBytes);

            if (roll.equals(student.getRoll())) {
                rollField.setText("");
                rollExists = true;
                break;
            }
        }
        if (!rollExists || student.getIsDeleted())
        {
            showWarningMessage("Warning", "No such student exists with this roll : "+roll);
            return;
        }
        String phoneNumber = phoneField.getText();
        if(!phoneNumber.equals("")) {
            if (isValidPhoneNumber(phoneNumber)) {
                showWarningMessage("Warning", "Please provide a valid phone number");
                phoneField.setText("");
                return;
            }
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

        byte[] studentBytes = student.toBytes();
        int len = studentBytes.length;
        byte[] temp = new byte[studentObjectSize];
        int i =0;
        while (i<len)
        {
            temp[i] = studentBytes[i++];
        }
        while (i<studentObjectSize){
            temp[i++] = (byte) 0;
        }
        fileStudent.seek(fileStudent.getFilePointer()-studentObjectSize);
        fileStudent.write(temp);
    }

    private void deleteStudent() throws IOException, ClassNotFoundException {
        String roll = rollField.getText();
        if(roll.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please enter a roll no.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean rollExists = false;
        Student student = null;
        fileStudent.seek(0L);
        while (fileStudent.getFilePointer() < fileStudent.length()) {
            byte[] studentBytes = new byte[studentObjectSize];
            int bytesRead = fileStudent.read(studentBytes);
            byte[] trimmedBytes = Arrays.copyOf(studentBytes, bytesRead);
            student = Student.fromBytes(trimmedBytes);

            if (roll.equals(student.getRoll())) {
                rollField.setText("");
                rollExists = true;
                break;
            }
        }
        if (!rollExists)
        {
            showWarningMessage("Warning", "No such student exists with this roll : "+roll);
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,"Deletion Aborted", "Abort Deletion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(student.getIsDeleted())
        {
            showWarningMessage("Warning", "Student with this roll already deleted");
            rollField.setText("");
            return;
        }
        student.setIsDeleted();

        byte[] studentBytes = student.toBytes();
        int len = studentBytes.length;
        byte[] temp = new byte[studentObjectSize];
        int i =0;
        while (i<len) { temp[i] = studentBytes[i++]; }
        while (i<studentObjectSize){ temp[i++] = (byte) 0; }

        fileStudent.seek(fileStudent.getFilePointer()-studentObjectSize);
        fileStudent.write(temp);

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
        deleteCount++;
    }
    private int getDeleteCount() throws IOException, ClassNotFoundException {
        int count = 0;
        while (fileStudent.getFilePointer() < fileStudent.length()) {
            byte[] studentBytes = new byte[studentObjectSize];
            int bytesRead = fileStudent.read(studentBytes);
            byte[] trimmedBytes = Arrays.copyOf(studentBytes, bytesRead);
            Student student = Student.fromBytes(trimmedBytes);
            if(student.getIsDeleted())
            {
                count++;
            }
        }
        System.out.println("logical deletion count = " + count);
        return  count;
    }
    private void deleteParmanently(){
        try{
            File tempFile = new File("tempFile.bin");
            RandomAccessFile tempFileRandom = new RandomAccessFile(tempFile,"rw");
            fileStudent.seek(0);
            while (fileStudent.getFilePointer() < fileStudent.length()) {
                byte[] studentBytes = new byte[studentObjectSize];
                int bytesRead = fileStudent.read(studentBytes);
                byte[] trimmedBytes = Arrays.copyOf(studentBytes, bytesRead);
                Student student = Student.fromBytes(trimmedBytes);
                if(!student.getIsDeleted())
                {
                    tempFileRandom.write(studentBytes);
                }
            }
            tempFileRandom.close();
            fileStudent.close();
            File fStudent = new File(filePath1);
            if (fStudent.exists()) {
                if (fStudent.delete()) {
                    tempFile.renameTo(new File(filePath1));
                    System.out.println("Permanent deletion done.");
                    fileStudent = new RandomAccessFile(filePath1,"rw");
                } else {
                    System.out.println("Failed to delete the original file.");
                }
            } else {
                System.out.println("Original file does not exist.");
            }
        }
        catch (IOException |ClassNotFoundException e)
        {
            System.out.println("Exception: "+e);
        }
    }


    private int currentIndex;
    private int studentsPerPage;
    private void fetchStudentsFromFile(ArrayList<Student> studentList, Long filePointer) {
        try{
            System.out.println("File pointer  = "+filePointer);
            studentList.clear();
            fileStudent.seek(filePointer);
            for (int i = 0; i < studentsPerPage;) {
                if (fileStudent.getFilePointer() < fileStudent.length()) {
                    byte[] studentBytes = new byte[studentObjectSize];
                    int bytesRead = fileStudent.read(studentBytes);
                    byte[] trimmedBytes = Arrays.copyOf(studentBytes, bytesRead);
                    Student student = Student.fromBytes(trimmedBytes);
//                    if (!student.getIsDeleted())
                    {
                        studentList.add(student);
                        i++;
                    }
                }
                else {
                    break;
                }
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Fetch file error: "+e.getMessage());
        }


    }
    private void displayStudents() throws IOException, ClassNotFoundException {

        ArrayList<Student> studentList = new ArrayList<>();
        Stack<Long> lastFilePointerStack = new Stack<>();
        currentIndex = 0;
        studentsPerPage = (int) displayField.getValue();

        JFrame displayFrame = new JFrame("Student Table");
        displayFrame.setBounds(300,300,800,250);
        DefaultTableModel tableModel = new DefaultTableModel();
        for (String column : new String[]{"Roll", "Name", "DeptCode", "Dept Name", "Address", "Phone"}) {
            tableModel.addColumn(column);
        }

        JTable displayTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(displayTable);
        displayFrame.add(scrollPane);


        JPanel buttonPanel = new JPanel();


        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");

        final long[][] lastFilePointer = {{0L}};

        prevButton.addActionListener(e -> {
            if (currentIndex - studentsPerPage >= 0) {
                currentIndex -= studentsPerPage;
                updateTable(tableModel, studentList);
            } else {
                currentIndex = 0;
            }
            long lastPointer=0L;
            if (!lastFilePointerStack.empty()) {
                lastPointer = lastFilePointerStack.pop();
                fetchStudentsFromFile(studentList, lastPointer);
                updateTable(tableModel, studentList);
            }
            if(lastPointer==0L) {
                prevButton.setEnabled(false);
                try {
                    if(fileStudent.getFilePointer()<fileStudent.length())
                        nextButton.setEnabled(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                lastFilePointer[0] = new long[]{0L};
            }
        });

        nextButton.addActionListener(e -> {
            long pointer;
            lastFilePointerStack.push(lastFilePointer[0][0]);
            try {
                pointer = fileStudent.getFilePointer();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lastFilePointer[0][0] = pointer;

            fetchStudentsFromFile(studentList, pointer);
            currentIndex += studentsPerPage;
            updateTable(tableModel, studentList);
            try {
                if(fileStudent.getFilePointer()==fileStudent.length())
                    nextButton.setEnabled(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            prevButton.setEnabled(true);
        });

        fileStudent.seek(0);
//        lastFilePointerStack.push(0L);
        fetchStudentsFromFile(studentList, fileStudent.getFilePointer());
        currentIndex = studentList.size();
        updateTable(tableModel, studentList);
        prevButton.setEnabled(false);
        if(studentsPerPage>studentList.size())
        {
            nextButton.setEnabled(false);
        }

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        displayFrame.add(buttonPanel, BorderLayout.SOUTH);
        displayFrame.setVisible(true);
    }

    private void updateTable(DefaultTableModel tableModel, ArrayList<Student> studentList) {
        tableModel.setRowCount(0);
        System.out.println("studentList.size = "+studentList.size());
        int start = currentIndex;
        int end = Math.min(currentIndex + studentsPerPage, studentList.size());

        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            Object[] rowData = {student.getRoll(), student.getName(), student.getDeptCode(),
                    student.getDeptName(), student.getAddress(), student.getPhone()};
            tableModel.addRow(rowData);
        }
    }



    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[0-9\\s-()]+$";

        return !phoneNumber.matches(regex) || phoneNumber.length() != 10;
    }
    private void showNextSet() {
        // Implement logic to show the next set of students
    }
    public static void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public void closeFile(RandomAccessFile fileStudent) throws IOException {
        if (fileStudent != null) {
            fileStudent.close();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    new StudentManagementSystem();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
