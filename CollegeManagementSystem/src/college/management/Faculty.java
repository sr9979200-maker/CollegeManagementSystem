package college.management;

public class Faculty {
    private int id;
    private String name, email, phone, department, subject;

    public Faculty(int id, String name, String email, String phone, String department, String subject) {
        this.id=id; this.name=name; this.email=email; this.phone=phone;
        this.department=department; this.subject=subject;
    }
    public int getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPhone(){return phone;}
    public String getDepartment(){return department;}
    public String getSubject(){return subject;}
}