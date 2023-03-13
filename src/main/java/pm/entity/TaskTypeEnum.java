package pm.entity;

public enum TaskTypeEnum {
    UNKNOWN(0, "Неизвестный"),
    MANAGER(1, "Менеджер"),
    TECHSPECIALIST(2, "Технический специалист");

    private Integer code;
    private String value;

    TaskTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }

    public static TaskTypeEnum byCode(int status) {
        switch(status) {
            case 1:
                return MANAGER;
            case 2:
                return TECHSPECIALIST;
            default:
                return null;
        }
    }
}
