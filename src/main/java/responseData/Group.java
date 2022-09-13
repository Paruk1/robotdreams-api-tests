package responseData;

import requestData.GroupData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Group extends GroupData {

    @JsonProperty
    public Integer id;

    @JsonProperty
    public Student[] students;

    public Group(Integer id, String name, Integer[] students,Student[] studentsList) {
        super(name, students);
        this.id = id;
        this.name = name;
        this.students = studentsList;
    }

    public Group() {
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", studentList=" + Arrays.toString(students) +
                ", id=" + id +
                '}';
    }
}
