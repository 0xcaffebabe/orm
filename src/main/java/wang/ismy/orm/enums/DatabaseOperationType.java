package wang.ismy.orm.enums;

public enum DatabaseOperationType {

    INSERT(0),UPDATE(1),DELETE(2),SELECT(3);

    private int code;

    DatabaseOperationType(int code) {
        this.code = code;
    }
}
