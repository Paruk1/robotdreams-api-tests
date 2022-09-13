package responseData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Assignment {

    @JsonProperty
    public Integer id;
    @JsonProperty
    public Integer student_id;
    @JsonProperty
    public String solution;
    @JsonProperty
    public Integer mark;
    @JsonProperty
    public Integer content_id;

    public Assignment(Integer id, Integer student_id, String solution, Integer mark, Integer content_id) {
        this.id = id;
        this.student_id = student_id;
        this.solution = solution;
        this.mark = mark;
        this.content_id = content_id;
    }

    public Assignment() {
    }

    @Override
    public String toString() {
        return "AssignmentForGroup{" +" content_id='" + content_id + '\'' +
                ", id=" + id +
                ", student_id=" + student_id +
                ", solution='" + solution + '\'' +
                ", mark=" + mark +
                '}';
    }
}
