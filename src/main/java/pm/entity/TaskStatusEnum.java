package pm.entity;

public enum TaskStatusEnum {
    UNKNOWN(0, "Неизвестная"),
    NEW(1, "Новая"),
    PROGRESS(2, "Выполняется"),
    DONE(3, "Выполнена");

    private Integer code;
    private String value;

    TaskStatusEnum(Integer code, String value) {
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

    public static TaskStatusEnum byCode(int status) {
        switch(status) {
            case 1:
                return NEW;
            case 2:
                return PROGRESS;
            case 3:
                return DONE;
            default:
                return null;
        }
    }
}
