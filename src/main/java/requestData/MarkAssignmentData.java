package requestData;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Mark;

public class MarkAssignmentData {
    @JsonProperty
    public Integer mark;

    public MarkAssignmentData(Mark mark) {
        this.mark = mark.getMarkNum();
    }

    public MarkAssignmentData() {
    }

    @Override
    public String toString() {
        return "MarkAssignmentData{" +
                "mark=" + mark +
                '}';
    }
}
