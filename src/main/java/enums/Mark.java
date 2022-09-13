package enums;

public enum Mark {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private Integer markNum;

    Mark(Integer markNum){
        this.markNum = markNum;
    }

    public Integer getMarkNum() {
        return markNum;
    }
}
