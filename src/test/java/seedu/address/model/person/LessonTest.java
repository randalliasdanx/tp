package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void constructor_nullLessonName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, AttendanceStatus.PRESENT));
    }

    @Test
    public void constructor_nullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson("Lesson 1", null));
    }

    @Test
    public void constructor_invalidLessonName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Lesson("", AttendanceStatus.PRESENT));
        assertThrows(IllegalArgumentException.class, () -> new Lesson("  ", AttendanceStatus.PRESENT));
        assertThrows(IllegalArgumentException.class, () -> new Lesson("@lesson", AttendanceStatus.PRESENT));
    }

    @Test
    public void constructor_validArgs_success() {
        Lesson lesson = new Lesson("Lesson 1", AttendanceStatus.PRESENT);
        assertEquals("Lesson 1", lesson.lessonName);
        assertEquals(AttendanceStatus.PRESENT, lesson.status);
    }

    @Test
    public void isValidLessonName_invalidValues_returnsFalse() {
        assertFalse(Lesson.isValidLessonName(""));
        assertFalse(Lesson.isValidLessonName("  "));
        assertFalse(Lesson.isValidLessonName("@Lesson"));
        assertFalse(Lesson.isValidLessonName("Lesson!"));
    }

    @Test
    public void isValidLessonName_validValues_returnsTrue() {
        assertTrue(Lesson.isValidLessonName("Lesson 1"));
        assertTrue(Lesson.isValidLessonName("MathLesson"));
        assertTrue(Lesson.isValidLessonName("1"));
        assertTrue(Lesson.isValidLessonName("Chapter 3 Review"));
    }

    @Test
    public void toStringMethod() {
        Lesson lesson = new Lesson("Lesson 1", AttendanceStatus.PRESENT);
        assertEquals("Lesson 1(Present)", lesson.toString());
    }

    @Test
    public void equals() {
        Lesson lesson = new Lesson("Lesson 1", AttendanceStatus.PRESENT);

        assertEquals(lesson, lesson);
        assertEquals(lesson, new Lesson("Lesson 1", AttendanceStatus.PRESENT));

        assertNotEquals(lesson, null);
        assertNotEquals(lesson, "Lesson 1");
        assertNotEquals(lesson, new Lesson("Lesson 2", AttendanceStatus.PRESENT));
        assertNotEquals(lesson, new Lesson("Lesson 1", AttendanceStatus.ABSENT));
    }

    @Test
    public void hashCodeMethod() {
        Lesson lesson = new Lesson("Lesson 1", AttendanceStatus.PRESENT);
        assertEquals(lesson.hashCode(), new Lesson("Lesson 1", AttendanceStatus.PRESENT).hashCode());
        assertNotEquals(lesson.hashCode(), new Lesson("Lesson 1", AttendanceStatus.ABSENT).hashCode());
    }
}
