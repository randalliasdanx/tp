package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {

    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }

    @Test
    public void getWindowCoordinates_defaultConstructor_returnsNull() {
        GuiSettings guiSettings = new GuiSettings();
        assertNull(guiSettings.getWindowCoordinates());
    }

    @Test
    public void getWindowCoordinates_withCoordinates_returnsCopy() {
        GuiSettings guiSettings = new GuiSettings(800, 600, 10, 20);
        assertEquals(10, (int) guiSettings.getWindowCoordinates().getX());
        assertEquals(20, (int) guiSettings.getWindowCoordinates().getY());
    }

    @Test
    public void equals() {
        GuiSettings defaultSettings = new GuiSettings();
        GuiSettings withCoords = new GuiSettings(800, 600, 10, 20);

        // same object -> returns true
        assertTrue(defaultSettings.equals(defaultSettings));

        // null -> returns false
        assertFalse(defaultSettings.equals(null));

        // different type -> returns false
        assertFalse(defaultSettings.equals("string"));

        // same values -> returns true
        assertTrue(defaultSettings.equals(new GuiSettings()));

        // different width -> returns false
        assertFalse(defaultSettings.equals(new GuiSettings(999, 600, 0, 0)));

        // different coordinates -> returns false
        assertFalse(defaultSettings.equals(withCoords));

        // same non-default values -> returns true
        assertTrue(withCoords.equals(new GuiSettings(800, 600, 10, 20)));
    }

    @Test
    public void hashcode() {
        GuiSettings settings = new GuiSettings();
        assertEquals(settings.hashCode(), new GuiSettings().hashCode());

        GuiSettings withCoords = new GuiSettings(800, 600, 10, 20);
        assertNotEquals(settings.hashCode(), withCoords.hashCode());
    }
}
