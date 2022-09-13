package responseData;

import requestData.ContentData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Content extends ContentData {

    @JsonProperty
    public String id;

    public Content(String id,String content) {
        super(content);
        this.id = id;
    }

    public Content() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Content)) return false;
        if (!super.equals(o)) return false;
        Content content = (Content) o;
        return id.equals(content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
