import java.io.*;

class Department {
    private String deptCode;
    private String name;

    public Department(String deptCode, String name) {
        this.deptCode = deptCode;
        this.name = name;
    }
    public String getDeptName() {
        return name;
    }

    public  String getDeptCode(){
        return  this.deptCode;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

    public static Department fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (Department) ois.readObject();
        }
    }

}