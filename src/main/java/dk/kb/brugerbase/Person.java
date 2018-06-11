package dk.kb.brugerbase;

/**
 * Created by dgj on 07-06-2018.
 */
public class Person {
    private String uid;
    private String fullName;
    private String lastName;
    private String mail;

    public Person() {
    }

    public Person(String uid, String fullName, String lastName, String mail) {
        this.uid = uid;
        this.fullName = fullName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "dk.kb.brugerbase.Person{" +
                "fullName='" + fullName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
