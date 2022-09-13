package responseData;

import requestData.StudentData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Student extends StudentData {

    @JsonProperty
    public Integer id;

    public Student(Integer id, String first_name, String last_name) {
        super(first_name, last_name);
        this.id = id;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
