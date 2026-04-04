package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a lesson associated with a student.
 * Guarantees: immutable; lesson name is valid as declared in {@link #isValidLessonName(String)};
 * status is non-null.
 */
public class Lesson {

    public static final String MESSAGE_CONSTRAINTS =
            "Lesson names should only contain alphanumeric characters and spaces, and should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String lessonName;
    public final AttendanceStatus status;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param lessonName A valid lesson name.
     * @param status     The attendance status for this lesson.
     */
    public Lesson(String lessonName, AttendanceStatus status) {
        requireNonNull(lessonName);
        requireNonNull(status);
        checkArgument(isValidLessonName(lessonName), MESSAGE_CONSTRAINTS);
        this.lessonName = lessonName;
        this.status = status;
    }

    /**
     * Returns true if a given string is a valid lesson name.
     */
    public static boolean isValidLessonName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return lessonName + "(" + status + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return lessonName.equals(otherLesson.lessonName)
                && status == otherLesson.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonName, status);
    }
}
