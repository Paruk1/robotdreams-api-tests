package requestData;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.TargetType;

public class AssignmentForStudentData {

    @JsonProperty
    public String target_type;

    @JsonProperty
    public String content_id;

    @JsonProperty
    public String student_id;


    public AssignmentForStudentData(TargetType target_type, String content_id, String student_id) {
        this.target_type = target_type.getTargetName();
        this.content_id = content_id;
        this.student_id = student_id;
    }

    public AssignmentForStudentData(){

    }

    @Override
    public String toString() {
        return "AssignmentForStudentData{" +
                "target_type=" + target_type +
                ", content_id='" + content_id + '\'' +
                ", student_id='" + student_id + '\'' +
                '}';
    }
}
