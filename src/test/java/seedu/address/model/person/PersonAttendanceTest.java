package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonAttendanceTest {

    @Test
    public void defaultConstructor_attendanceRecordsIsEmpty() {
        Person person = new PersonBuilder().build();
        assertTrue(person.getAttendanceRecords().isEmpty());
    }

    @Test
    public void attendanceConstructor_copiesRecordsDefensively() {
        Map<String, Map<String, AttendanceStatus>> records = new LinkedHashMap<>();
        Map<String, AttendanceStatus> inner = new LinkedHashMap<>();
        inner.put("Lesson 1", AttendanceStatus.PRESENT);
        records.put("Mathematics", inner);

        Person person = new PersonBuilder().build();
        Person personWithRecords = new Person(
                person.getName(), person.getEmail(), person.getAddress(),
                person.getSubjects(), person.getDays(), person.getTimes(),
                person.getEmergencyContact(), person.getPaymentStatus(),
                person.getRemark(), person.getTags(), records);

        // Mutating original map should not affect person's records
        inner.put("Lesson 2", AttendanceStatus.ABSENT);
        assertEquals(1, personWithRecords.getAttendanceRecords().get("Mathematics").size());
    }

    @Test
    public void getAttendanceRecords_returnsUnmodifiableView() {
        Person person = new PersonBuilder().build().markAttendance("Mathematics", "Lesson 1", AttendanceStatus.PRESENT);
        Map<String, Map<String, AttendanceStatus>> records = person.getAttendanceRecords();
        org.junit.jupiter.api.Assertions.assertThrows(UnsupportedOperationException.class, ()
                -> records.put("Science", new LinkedHashMap<>()));
    }

    @Test
    public void markAttendance_returnsNewPersonWithUpdatedRecord() {
        Person original = new PersonBuilder().build();
        Person updated = original.markAttendance("Mathematics", "Lesson 1", AttendanceStatus.PRESENT);

        assertNotSame(original, updated);
        assertTrue(original.getAttendanceRecords().isEmpty());
        assertEquals(AttendanceStatus.PRESENT,
                updated.getAttendanceRecords().get("Mathematics").get("Lesson 1"));
    }

    @Test
    public void markAttendance_multipleSubjectsAndLessons() {
        Person person = new PersonBuilder().build()
                .markAttendance("Mathematics", "Lesson 1", AttendanceStatus.PRESENT)
                .markAttendance("Mathematics", "Lesson 2", AttendanceStatus.ABSENT)
                .markAttendance("Science", "Lesson 1", AttendanceStatus.EXCUSED);

        Map<String, Map<String, AttendanceStatus>> records = person.getAttendanceRecords();
        assertEquals(2, records.size());
        assertEquals(AttendanceStatus.PRESENT, records.get("Mathematics").get("Lesson 1"));
        assertEquals(AttendanceStatus.ABSENT, records.get("Mathematics").get("Lesson 2"));
        assertEquals(AttendanceStatus.EXCUSED, records.get("Science").get("Lesson 1"));
    }

    @Test
    public void markAttendance_overwritesExistingRecord() {
        Person person = new PersonBuilder().build()
                .markAttendance("Mathematics", "Lesson 1", AttendanceStatus.ABSENT)
                .markAttendance("Mathematics", "Lesson 1", AttendanceStatus.PRESENT);

        assertEquals(AttendanceStatus.PRESENT,
                person.getAttendanceRecords().get("Mathematics").get("Lesson 1"));
    }

    @Test
    public void equals_sameAttendanceRecords_areEqual() {
        Person p1 = new PersonBuilder().build().markAttendance("Math", "L1", AttendanceStatus.PRESENT);
        Person p2 = new PersonBuilder().build().markAttendance("Math", "L1", AttendanceStatus.PRESENT);
        assertEquals(p1, p2);
    }

    @Test
    public void equals_differentAttendanceRecords_areNotEqual() {
        Person p1 = new PersonBuilder().build().markAttendance("Math", "L1", AttendanceStatus.PRESENT);
        Person p2 = new PersonBuilder().build().markAttendance("Math", "L1", AttendanceStatus.ABSENT);
        assertNotEquals(p1, p2);
    }

    @Test
    public void equals_emptyVsNonEmptyAttendance_areNotEqual() {
        Person empty = new PersonBuilder().build();
        Person withRecords = new PersonBuilder().build().markAttendance("Math", "L1", AttendanceStatus.PRESENT);
        assertNotEquals(empty, withRecords);
    }

    @Test
    public void hashCode_sameAttendanceRecords_sameHash() {
        Person p1 = new PersonBuilder().build().markAttendance("Math", "L1", AttendanceStatus.PRESENT);
        Person p2 = new PersonBuilder().build().markAttendance("Math", "L1", AttendanceStatus.PRESENT);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void attendanceConstructor_emptyRecordsMap_isEquivalentToDefault() {
        Person defaultPerson = new PersonBuilder().build();
        Person person = new PersonBuilder().build();
        Person explicitEmpty = new Person(
                person.getName(), person.getEmail(), person.getAddress(),
                person.getSubjects(), person.getDays(), person.getTimes(),
                person.getEmergencyContact(), person.getPaymentStatus(),
                person.getRemark(), person.getTags(), Collections.emptyMap());
        assertEquals(defaultPerson, explicitEmpty);
    }
}
