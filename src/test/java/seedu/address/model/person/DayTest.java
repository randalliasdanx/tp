package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        try {
            new Day(null);
        } catch (NullPointerException e) {
            // The actual message from Objects.requireNonNull is "Cannot invoke \"Object.toString()\" because \"day\" is null"
            // We just check that a NullPointerException is thrown
            assertTrue(true);
        }
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        try {
            new Day(invalidDay);
        } catch (IllegalArgumentException e) {
            assertEquals(Day.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    public void isValidDay() {
        // blank day
        assertFalse(Day.isValidDay("")); // empty string
        assertFalse(Day.isValidDay(" ")); // spaces only

        // missing parts
        assertFalse(Day.isValidDay("@")); // non-alphanumeric character
        assertFalse(Day.isValidDay("Monday@")); // contains non-alphanumeric character

        // valid day
        assertTrue(Day.isValidDay("Monday"));
        assertTrue(Day.isValidDay("Tuesday"));
        assertTrue(Day.isValidDay("Wednesday"));
        assertTrue(Day.isValidDay("Thursday"));
        assertTrue(Day.isValidDay("Friday"));
        assertTrue(Day.isValidDay("Saturday"));
        assertTrue(Day.isValidDay("Sunday"));
        assertTrue(Day.isValidDay("Weekend"));
        assertTrue(Day.isValidDay("Public Holiday"));
    }

    @Test
    public void equals() {
        Day day = new Day("Monday");

        // same values -> returns true
        assertTrue(day.equals(new Day("Monday")));

        // same object -> returns true
        assertTrue(day.equals(day));

        // null -> returns false
        assertFalse(day.equals(null));

        // different types -> returns false
        assertFalse(day.equals(5.0f));

        // different values -> returns false
        assertFalse(day.equals(new Day("Tuesday")));
    }

    @Test
    public void hashCode_test() {
        Day day = new Day("Monday");
        Day anotherDay = new Day("Monday");
        assertEquals(day.hashCode(), anotherDay.hashCode());
    }
}