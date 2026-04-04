package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    @Test
    public void isValidStatus_nullInput_returnsFalse() {
        assertFalse(AttendanceStatus.isValidStatus(null));
    }

    @Test
    public void isValidStatus_invalidValues_returnsFalse() {
        assertFalse(AttendanceStatus.isValidStatus(""));
        assertFalse(AttendanceStatus.isValidStatus("unknown"));
        assertFalse(AttendanceStatus.isValidStatus("late"));
        assertFalse(AttendanceStatus.isValidStatus("123"));
    }

    @Test
    public void isValidStatus_validValues_returnsTrue() {
        assertTrue(AttendanceStatus.isValidStatus("Present"));
        assertTrue(AttendanceStatus.isValidStatus("present"));
        assertTrue(AttendanceStatus.isValidStatus("PRESENT"));
        assertTrue(AttendanceStatus.isValidStatus("Absent"));
        assertTrue(AttendanceStatus.isValidStatus("absent"));
        assertTrue(AttendanceStatus.isValidStatus("ABSENT"));
        assertTrue(AttendanceStatus.isValidStatus("Excused"));
        assertTrue(AttendanceStatus.isValidStatus("excused"));
        assertTrue(AttendanceStatus.isValidStatus("EXCUSED"));
    }

    @Test
    public void fromString_validValues_returnsCorrectEnum() {
        assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.fromString("Present"));
        assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.fromString("present"));
        assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.fromString("PRESENT"));
        assertEquals(AttendanceStatus.ABSENT, AttendanceStatus.fromString("Absent"));
        assertEquals(AttendanceStatus.ABSENT, AttendanceStatus.fromString("absent"));
        assertEquals(AttendanceStatus.EXCUSED, AttendanceStatus.fromString("Excused"));
        assertEquals(AttendanceStatus.EXCUSED, AttendanceStatus.fromString("excused"));
    }

    @Test
    public void fromString_nullInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString(null));
    }

    @Test
    public void fromString_invalidValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString("unknown"));
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString(""));
    }

    @Test
    public void toStringMethod() {
        assertEquals("Present", AttendanceStatus.PRESENT.toString());
        assertEquals("Absent", AttendanceStatus.ABSENT.toString());
        assertEquals("Excused", AttendanceStatus.EXCUSED.toString());
    }

    @Test
    public void valueField() {
        assertEquals("Present", AttendanceStatus.PRESENT.value);
        assertEquals("Absent", AttendanceStatus.ABSENT.value);
        assertEquals("Excused", AttendanceStatus.EXCUSED.value);
    }
}
