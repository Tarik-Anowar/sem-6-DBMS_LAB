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

}