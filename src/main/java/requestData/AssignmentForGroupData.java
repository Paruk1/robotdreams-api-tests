package requestData;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.TargetType;

public class AssignmentForGroupData {

    @JsonProperty
    public String target_type;

    @JsonProperty
    public String group_id;

    @JsonProperty
    public String content_id;

    public AssignmentForGroupData(TargetType target_type, String group_id, String content_id) {
        this.target_type = target_type.getTargetName();
        this.group_id = group_id;
        this.content_id = content_id;
    }

    public AssignmentForGroupData() {
    }
}
