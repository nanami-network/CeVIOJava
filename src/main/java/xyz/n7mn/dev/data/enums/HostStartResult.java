package xyz.n7mn.dev.data.enums;

public enum HostStartResult {
    SUCCEEDED(0),
    ALREADY_STARTED(1),
    NOT_REGISTERED(-1),
    FILE_NOT_FOUND(-2),
    STARTING_FAILED(-3),
    HOST_ERROR(-4),
    UNKNOWN(-999);

    private final int value;

    HostStartResult(int value) {
        this.value = value;
    }

    private final static HostStartResult[] values = values();

    public static HostStartResult getByOriginal(int value) {
        for (HostStartResult result : values) {
            if (value == result.value) {
                return result;
            }
        }
        return UNKNOWN;
    }

    public boolean isError() {
        return value < 0;
    }
}
