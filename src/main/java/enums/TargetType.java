package enums;

public enum TargetType {

    STUDENT("student"),
    GROUP("group");

    private String targetName;

    TargetType(String targetName){
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }
}
