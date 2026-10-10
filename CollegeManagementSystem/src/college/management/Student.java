package college.management;

public class Student {
    private int id;
    private String name, email, phone, course, branch;
    private int semester;

    public Student(int id, String name, String email, String phone,
                   String course, String branch, int semester) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
        this.branch = branch;
        this.semester = semester;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCourse() { return course; }
    public String getBranch() { return branch; }
    public int getSemester() { return semester; }
}