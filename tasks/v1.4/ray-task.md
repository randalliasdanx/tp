# Ray -- Payment Status Shortcut Command

**Branch name:** `feature/mark-payment-command`
**PR target:** `master`
**Milestone:** `v1.4`
**Deadline:** Wednesday March 25, end of day
**Depends on:** Yi Zhong's persistence PR must be merged first

---

## Context

Currently, to update a student's payment status, you have to use the full `edit` command:
```
edit 1 ps/Paid
```

We want a dedicated shortcut command:
```
mark 1 ps/Paid
```

---

## What you need to do (4+ files)

### 1. Create `MarkCommand.java`

**File:** `src/main/java/seedu/address/logic/commands/MarkCommand.java`

```java
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the payment status of the student identified "
            + "by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "ps/PAYMENT_STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 ps/Paid";
    public static final String MESSAGE_MARK_SUCCESS =
            "Updated payment status of %1$s to %2$s";

    private final Index targetIndex;
    private final PaymentStatus newStatus;

    // constructor, execute(), equals()
}
```

In `execute()`:
1. Get the person at `targetIndex` from the filtered list
2. Create a new `Person` with the same fields but the new `PaymentStatus`
3. Call `model.setPerson(personToMark, markedPerson)`
4. Return success message with the person's name and new status

**Important:** When constructing the new Person, preserve all existing fields including the `Remark` field (if Yi Zhong's PR is merged before yours). If not merged yet, use the current constructor signature and update after.

### 2. Create `MarkCommandParser.java`

**File:** `src/main/java/seedu/address/logic/parser/MarkCommandParser.java`

```java
public class MarkCommandParser implements Parser<MarkCommand> {
    public MarkCommand parse(String args) throws ParseException {
        // Use ArgumentTokenizer with PREFIX_PAYMENT_STATUS
        // Parse the index from the preamble
        // Parse the payment status
        // Return new MarkCommand(index, paymentStatus)
    }
}
```

Validation:
- Index must be present and valid
- `ps/` prefix must be present
- Payment status must be one of: `Paid`, `Due`, `Overdue`

### 3. Register in `AddressBookParser.java`

**File:** `src/main/java/seedu/address/logic/parser/AddressBookParser.java`

Add import:
```java
import seedu.address.logic.commands.MarkCommand;
```

Add case in the switch statement (around line 90, before `default:`):
```java
case MarkCommand.COMMAND_WORD:
    return new MarkCommandParser().parse(arguments);
```

### 4. Tests

**Create `src/test/java/seedu/address/logic/commands/MarkCommandTest.java`:**
- Test successful mark on unfiltered list
- Test successful mark on filtered list
- Test invalid index (out of bounds)
- Test equals method

**Create `src/test/java/seedu/address/logic/parser/MarkCommandParserTest.java`:**
- Test valid input: `1 ps/Paid`
- Test missing index: `ps/Paid`
- Test missing payment status: `1`
- Test invalid payment status: `1 ps/Invalid`
- Test invalid index: `0 ps/Paid`, `-1 ps/Paid`

---

## Testing

1. Run `./gradlew test checkstyleMain checkstyleTest` -- must all pass
2. Manual tests:
   - `mark 1 ps/Paid` -- should update first student to Paid
   - `mark 1 ps/Due` -- should update to Due
   - `mark 1 ps/Overdue` -- should update to Overdue
   - `mark 0 ps/Paid` -- should show error
   - `mark 1 ps/Invalid` -- should show error
   - `mark 1` -- should show usage message

---

## UML

Add an activity diagram for the payment tracking workflow in `docs/diagrams/` (e.g., `MarkActivityDiagram.puml`).

---

## Do NOT touch

- `Person.java`, `JsonAdaptedPerson.java` -- assigned to Yi Zhong
- `FindCommand`, `FindCommandParser` -- assigned to Abhishek
- `UserGuide.md` -- assigned to Randall
- `DeveloperGuide.md` -- assigned to Dayne
