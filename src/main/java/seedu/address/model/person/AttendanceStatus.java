package seedu.address.model.person;

/**
 * Represents the attendance status of a student for a lesson.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public enum AttendanceStatus {

    PRESENT("Present"),
    ABSENT("Absent"),
    EXCUSED("Excused");

    public static final String MESSAGE_CONSTRAINTS =
            "Attendance status should be one of: Present, Absent, Excused (case-insensitive)";

    public final String value;

    AttendanceStatus(String value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid attendance status.
     */
    public static boolean isValidStatus(String test) {
        if (test == null) {
            return false;
        }
        String lower = test.trim().toLowerCase();
        return lower.equals("present") || lower.equals("absent") || lower.equals("excused");
    }

    /**
     * Returns the {@code AttendanceStatus} corresponding to the given string, case-insensitively.
     *
     * @param test A string to parse.
     * @return The matching {@code AttendanceStatus}.
     * @throws IllegalArgumentException if the string does not match any valid status.
     */
    public static AttendanceStatus fromString(String test) {
        if (test == null) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        String lower = test.trim().toLowerCase();
        switch (lower) {
        case "present":
            return PRESENT;
        case "absent":
            return ABSENT;
        case "excused":
            return EXCUSED;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
