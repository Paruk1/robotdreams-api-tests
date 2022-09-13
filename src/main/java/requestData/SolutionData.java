package requestData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolutionData {

    @JsonProperty
    public String solution;

    public SolutionData(String solution) {
        this.solution = solution;
    }

    public SolutionData(){

    }

    public String getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        return "SolutionData{" +
                "solution='" + solution + '\'' +
                '}';
    }
}
