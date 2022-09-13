package responseData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLogin {

    @JsonProperty
    public String session_token;

    @JsonProperty
    public String username;

    @JsonProperty
    public String valid_to;

    public UserLogin(String session_token, String username, String valid_to) {
        this.session_token = session_token;
        this.username = username;
        this.valid_to = valid_to;
    }

    public UserLogin() {
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "session_token='" + session_token + '\'' +
                ", username='" + username + '\'' +
                ", valid_to='" + valid_to + '\'' +
                '}';
    }
}
