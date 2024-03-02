public class Student {
    private String roll;
    private String deptCode;
    private String name;
    private String address;
    private String phone;
    private  String deptName;
    public Student(String roll,String name, String deptCode,String deptName,String address, String phone) {
        this.roll = roll;
        this.deptCode = deptCode;
        this.name = name;
        this.deptName = deptName;
        this.address = address;
        this.phone = phone;
    }

//    public void copy(Student obj)
//    {
//        this.roll = obj.roll;
//        this.deptCode = obj.deptCode;
//        this.name = obj.name;
//        this.deptName = obj.deptName;
//        this.address = obj.address;
//        this.phone = obj.phone;
//    }

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
        return  this.deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
