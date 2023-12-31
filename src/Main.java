import java.util.*;
// This is an example of Strategy Pattern
// for csc 3250
class Course{
    private String _cnum;
    private int _credits;
    public Course(){}
    public Course(String num, int cred){
        _cnum = num;  _credits = cred;
    }
    public void setNumber(String num){_cnum = num;}
    public void setCredits(int cred){_credits = cred;}
    public String getNumber(){return _cnum;}
    public int getCredits(){return _credits;}
    public String toString(){return _cnum + " " + _credits;}
}
class Student{
    private String _sid;
    public Student(){}
    public Student(String d){_sid = d;}
    public void setID(String id){_sid = id;}
    public String getID(){return _sid;}
    public String toString(){return _sid;}
}
interface SearchBehavior <T, S> { // interfaces don't have a body method. no need for public or private
    // T is the object S is the value
    boolean search (T obj, S v);

}
class StudentSearch implements SearchBehavior<Student, String>{
    @Override
    public boolean search(Student obj, String v) {
        return obj.getID().equals(v); // returns the one line of code that was varying
    }
}
class CourseSearch implements  SearchBehavior<Course, String> {
    @Override
    public boolean search(Course obj, String v) {
        return obj.getNumber().equals(v);
    }
}

class AllItems <T> { // generic class, not needed for a strategy pattern just makes it a little easier.
    // Any time we have a templated class we can call it a generic.
    private ArrayList<T> _items;

    public AllItems(){_items = new ArrayList<T>();} // templating
    public AllItems(int x){_items = new ArrayList<T>(x);} // this had to be added so we could have a default const in allstudents

    public void addItem (T t) {_items.add(t);}
    public <S> boolean isItem(S v, SearchBehavior<T, S> sb){
        for (T item : _items){
            if (sb.search(item, v)){
                return  true;
            }
        }
        return false;
    }

    public <S> int findItem(S v, SearchBehavior<T, S> sb){ // polymorphic reference SearchBehavior<T, S> sb
        for (int i=0; i< _items.size(); i++){
            if (sb.search(_items.get(i), v)){
                return i;
            }
        }
        return -1;
    }

    public int size () {return _items.size();}
    public void removeItem(int i ){
        if (i >= 0 && i < _items.size()) {
            _items.remove(i);
        }
    }
    public T getItem (int i) {return _items.get(i);}



}
class AllStudents{
    private AllItems<Student> _students;
    public AllStudents(){
        _students = new AllItems<Student>();// use students here so the template variable is set to Student
    }
    public AllStudents(int x) {
        _students = new AllItems<Student>(x);
    }
    public void addStudent(String id){
        _students.addItem(new Student(id));
    }
    public boolean isStudent(String id){
        return _students.isItem(id, new StudentSearch());
    }
    public int findStudent(String id){
        return _students.findItem(id, new StudentSearch());
    }
    public int size () {
        return _students.size();
    }
    public boolean modifyStudentID (String id, String nid) {
        int idx = findStudent(id);
        if (idx < 0) {
            return false;
        }
        else {
            _students.getItem(idx).setID(nid);
            return true;
        }
    }
    public void removeStudent(String id){
        int i = _students.findItem(id, new StudentSearch());
        _students.removeItem(i);
    }
    public String toString(){
        String s = "Students:\n";
        for (int i=0; i<_students.size(); i++)
            s += (_students.getItem(i).toString() + "\n");
        return s;
    }
}
class AllCourses{
    private AllItems<Course> _courses;
    public AllCourses(){
        _courses = new AllItems<Course>();
    }
    public AllCourses(int x) {
        _courses = new AllItems<Course>(x);
    }
    public void addCourse(String cnum, int c){
        _courses.addItem(new Course(cnum, c));
    }
    public boolean isCourse(String cnum){
        return _courses.isItem(cnum, new CourseSearch());
    }
    public int findCourse(String cnum){
        return _courses.findItem(cnum, new CourseSearch());
    }
    public int size () {
        return _courses.size();
    }
    public boolean modifyCourseNum (String cnum, String ncnum) {
        int idx = findCourse(cnum);
        if (idx < 0) {
            return false;
        }
        else {
            _courses.getItem(idx).setNumber(ncnum);
            return true;
        }
    }
    public void removeCourse(String cnum){
        int i = _courses.findItem(cnum, new CourseSearch());
        _courses.removeItem(i);
    }
    public String toString(){
        String s = "Courses:\n";
        for (int i=0; i<_courses.size(); i++)
            s += (_courses.getItem(i).toString() + "\n");
        return s;
    }
}
// encapsulate using interfaces
public class Main {
    public static void main(String[] args) {
        AllStudents as = new AllStudents();
        AllCourses ac = new AllCourses();
        as.addStudent("100");
        as.addStudent("200");
        as.addStudent("300");
        ac.addCourse("CSC3250", 4);
        ac.addCourse("CSC1700", 4);
        ac.addCourse("MTH3270", 4);
        System.out.println(as);
        System.out.println(ac);
        System.out.println("Is Student 300: " + as.isStudent("300"));
        System.out.println("Find Student 300: " + as.findStudent("300"));
        System.out.println("Is Course CSC3250: " + ac.isCourse("CSC3250") );
        System.out.println("Find Course CSC3250: " + ac.findCourse("CSC3250"));
        as.removeStudent("300");
        System.out.println("\nAfter removing student 300");
        as.removeStudent("300");
        System.out.println(as);
        System.out.println("Is Student 300: " + as.isStudent("300"));
        System.out.println("Find Student 300: " + as.findStudent("300"));
        System.out.println("\nAfter removing course 3250");
        ac.removeCourse("CSC3250");
        System.out.println(ac);
        System.out.println("Is Course CSC3250: " + ac.isCourse("CSC3250") );
        System.out.println("Find Course CSC3250: " + ac.findCourse("CSC3250"));

    }
}
