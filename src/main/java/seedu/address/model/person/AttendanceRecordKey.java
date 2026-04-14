package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Utility methods for attendance record keys.
 * A record key uses the format "Day Time - Lesson",
 * e.g. "Monday 1400 - 2026-04-13 Algebra Lesson 2".
 */
public final class AttendanceRecordKey {

    private AttendanceRecordKey() {}

    /**
     * Creates an attendance record key from the given day, time, and lesson label.
     */
    public static String of(String day, String time, String lesson) {
        requireNonNull(day);
        requireNonNull(time);
        requireNonNull(lesson);
        return day + " " + time + " - " + lesson;
    }

    /**
     * Extracts the lesson slot key ("Day Time") from a full attendance record key.
     */
    public static String getLessonSlotKey(String recordKey) {
        requireNonNull(recordKey);
        int idx = recordKey.indexOf(" - ");
        return idx == -1 ? "" : recordKey.substring(0, idx);
    }

    /**
     * Returns true if the given string is a valid attendance record key.
     */
    public static boolean isValid(String recordKey) {
        requireNonNull(recordKey);
        int idx = recordKey.indexOf(" - ");
        if (idx == -1) {
            return false;
        }
        String slotKey = recordKey.substring(0, idx);
        String[] parts = slotKey.split(" ");
        if (parts.length != 2 || !Day.isValidDay(parts[0])
                || !Time.isValidTime(parts[1])) {
            return false;
        }
        String lesson = recordKey.substring(idx + 3);
        return !lesson.isEmpty() && Lesson.isValidLessonName(lesson);
    }
}
