package responseData;

import requestData.SolutionData;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentAssignment extends SolutionData {

    @JsonProperty
    public String id;

    @JsonProperty
    public Student student;

    @JsonProperty
    public Content content;

    @JsonProperty
    public Integer mark;

    public StudentAssignment(String solution, String id, Student student, Content content, Integer mark) {
        super(solution);
        this.id = id;
        this.student = student;
        this.content = content;
        this.mark = mark;
    }


    public StudentAssignment(){

    }



    @Override
    public String toString() {
        return "Solution{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", content=" + content +
                ", solution='" + super.solution + '\'' +
                ", mark=" + mark +
                '}';
    }
}
