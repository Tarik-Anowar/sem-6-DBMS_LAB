import java.io.*;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String roll;
    private String deptCode;
    private String name;
    private String address;
    private String phone;
    private String deptName;
    private boolean isDeleted;


    public Student(String roll, String name, String deptCode, String deptName, String address, String phone) {
        this.roll = roll;
        this.deptCode = deptCode;
        this.name = name;
        this.deptName = deptName;
        this.address = address;
        this.phone = phone;
        this.isDeleted = false;
    }

    public String getRoll() {
        return this.roll;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public boolean getIsDeleted()
    {
        return this.isDeleted;
    }
    public void setIsDeleted()
    {
        this.isDeleted = true;
    }

    public byte[] toBytes() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(this);
            return baos.toByteArray();
        }
    }

    public static Student fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Student) ois.readObject();
        }
    }

}
