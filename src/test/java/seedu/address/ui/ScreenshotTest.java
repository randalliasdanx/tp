package seedu.address.ui;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import javax.imageio.ImageIO;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

/**
 * Generates screenshots of each major command for the User Guide.
 * Run with: ./gradlew screenshotTest
 * Screenshots are saved to docs/images/.
 */
@Tag("screenshot")
@ExtendWith(ApplicationExtension.class)
public class ScreenshotTest {

    private static final Path DOCS_IMAGES = Paths.get("docs", "images");

    private MainWindow mainWindow;
    private Stage primaryStage;

    /**
     * Initialises the application with a typical address book for screenshot generation.
     */
    @Start
    void start(Stage stage) throws Exception {
        primaryStage = stage;

        Path tempDir = Files.createTempDirectory("tutorcentral-screenshot");
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(tempDir.resolve("data.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(tempDir.resolve("prefs.json"));
        Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Logic logic = new LogicManager(model, storage);

        mainWindow = new MainWindow(stage, logic);
        mainWindow.show();
        mainWindow.fillInnerParts();
    }

    /**
     * Generates screenshots of all major commands documented in the User Guide.
     */
    @Test
    void generateAllScreenshots(FxRobot robot) throws IOException, InterruptedException {
        Files.createDirectories(DOCS_IMAGES);

        takeScreenshot("startup");

        runCommand(robot, "list");
        takeScreenshot("list-result");

        runCommand(robot,
                "add n/John Tan e/johntan@example.com a/21 Lower Kent Ridge Rd "
                + "s/Mathematics d/Monday ti/1400 ec/91234567 ps/Due");
        takeScreenshot("add-result");

        runCommand(robot, "list");
        takeScreenshot("list-after-add");

        runCommand(robot, "find n/John");
        takeScreenshot("find-name-result");

        runCommand(robot, "find ps/Due");
        takeScreenshot("find-payment-result");

        runCommand(robot, "list");
        runCommand(robot, "edit 1 ps/Paid");
        takeScreenshot("edit-result");

        runCommand(robot, "list");
        runCommand(robot, "mark 1 ps/Overdue");
        takeScreenshot("mark-result");

        runCommand(robot, "list");
        runCommand(robot, "remark 1 r/Needs extra help with algebra.");
        takeScreenshot("remark-result");

        runCommand(robot, "list");
        runCommand(robot, "view 1");
        WaitForAsyncUtils.waitForFxEvents();
        takeScreenshot("view-result");

        runCommand(robot, "delete 8");
        takeScreenshot("delete-result");

        runCommand(robot, "help");
        WaitForAsyncUtils.waitForFxEvents();
        takeScreenshot("help-result");
    }

    /**
     * Generates a screenshot of the empty/list state for UG overview images.
     */
    @Test
    void generateUiOverviewScreenshot(FxRobot robot) throws IOException, InterruptedException {
        Files.createDirectories(DOCS_IMAGES);
        runCommand(robot, "list");
        takeScreenshot("Ui");
    }

    private void runCommand(FxRobot robot, String command) {
        robot.clickOn(".text-field");
        robot.write(command);
        robot.push(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();
    }

    private void takeScreenshot(String name) throws IOException, InterruptedException {
        WaitForAsyncUtils.waitForFxEvents();
        AtomicReference<WritableImage> imageRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            imageRef.set(primaryStage.getScene().snapshot(null));
            latch.countDown();
        });
        latch.await();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageRef.get(), null);
        Path outputPath = DOCS_IMAGES.resolve(name + ".png");
        ImageIO.write(bufferedImage, "PNG", outputPath.toFile());
        System.out.println("Saved: " + outputPath.toAbsolutePath());
    }
}
