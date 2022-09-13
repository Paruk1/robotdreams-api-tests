package requestData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupData {

    @JsonProperty
    public String name;

    @JsonProperty
    public Integer[] students;

    public GroupData(String name, Integer[] students) {
        this.name = name;
        this.students = students;
    }

    public GroupData(){}


}
