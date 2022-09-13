package requestData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class StudentData {

    @JsonProperty
    public String first_name;

    @JsonProperty
    public String last_name;

    public StudentData(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public StudentData(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentData)) return false;
        StudentData that = (StudentData) o;
        return first_name.equals(that.first_name) && last_name.equals(that.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name);
    }

    @Override
    public String toString() {
        return "StudentData{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
