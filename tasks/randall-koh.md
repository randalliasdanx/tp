# v1.5 Task Brief — Randall Koh

**Role:** `mark` command implementation + TestFX screenshots + DeveloperGuide  
**Status:** ✅ Completed (handled by Claude Code)

---

## What Was Done

### ✅ GitHub Setup
- Created `alpha-bug` label (red, #D73A4A)
- Verified v1.5 milestone exists (milestone #5)

### ✅ `mark` Command Implementation
- `src/main/java/seedu/address/logic/commands/MarkCommand.java`
- `src/main/java/seedu/address/logic/parser/MarkCommandParser.java`
- `src/main/java/seedu/address/logic/parser/AddressBookParser.java` — wired in dispatch
- Tests: `MarkCommandTest.java`, `MarkCommandParserTest.java`
- All tests pass + checkstyle clean

**Usage:** `mark INDEX ps/PAYMENT_STATUS`  
**Example:** `mark 1 ps/Paid`

### ✅ TestFX + ScreenshotTest
- Added TestFX 4.0.18 to `build.gradle`
- `src/test/java/seedu/address/ui/ScreenshotTest.java` exercises all UG commands
- Screenshots saved to `docs/images/` (startup, add, list, find, edit, mark, remark, view, delete, help, Ui)
- Run: `./gradlew screenshotTest`
- Regular `./gradlew test` excludes screenshot tests (CI safe)

### ✅ DeveloperGuide Updates
- Added `mark` command to Implementation section
- Filled User Stories table (mark, remark, find filter, view)
- Added comprehensive manual test cases (add, mark, remark, find, delete, saving data)
- Filled design alternatives section

---

## Still To Do (Alpha Testing)

Submit at least **5 issues** labelled `alpha-bug` tagged to the **v1.5 milestone**.

Test using: `java -jar build/libs/addressbook.jar`

Areas to test:
- `mark` command edge cases: `mark 0 ps/Paid`, `mark 999 ps/Paid`, `mark 1 ps/invalid`
- Case sensitivity: `MARK 1 PS/PAID` (should fail — commands are case-insensitive but prefix values are validated)
- `remark` with very long text (> 500 characters)
- Add/edit/delete during filtered view — does the list reset correctly?
- Closing the app while a `view` dialog is open
