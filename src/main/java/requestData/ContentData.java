package requestData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ContentData {

    @JsonProperty
    public String content;

    public ContentData(String content) {
        this.content = content;
    }

    public ContentData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentData)) return false;
        ContentData that = (ContentData) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}

