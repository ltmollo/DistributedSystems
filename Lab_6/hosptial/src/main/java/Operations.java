public enum Operations {
    HIP("hip"),
    KNEE("knee"),
    ELBOW("elbow");

    private final String value;

    Operations(String value) {
        this.value = value;
    }

    // Method to get enum from string
    public static Operations fromString(String value) {
        for (Operations operation : Operations.values()) {
            if (operation.value.equalsIgnoreCase(value)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }

    public String getValue() {
        return value;
    }
}
