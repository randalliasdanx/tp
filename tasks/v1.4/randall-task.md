# Randall -- Update User Guide + Sample Data

**Branch name:** `feature/update-ug-v1.4`
**PR target:** `master`
**Milestone:** `v1.4`
**Deadline:** Wednesday March 25, end of day
**Depends on:** Yi Zhong's persistence PR must be merged first (for sample data with Remark)

---

## What you need to do

### Part A: Update User Guide

**File:** `docs/UserGuide.md`

#### 1. Fix the download link (line 21)
Change from `https://github.com/se-edu/tutorcentral-level3/releases` to the actual team repo releases URL:
```
https://github.com/AY2526S2-CS2103T-T09-2/tp/releases
```

#### 2. Document the `remark` command (new section, add after `view` and before `delete`)

```markdown
### Adding a remark to a student: `remark`

Adds or updates a free-text remark for the student at the given index. Useful for notes like "needs help with algebra" or "prefers morning lessons".

Format: `remark INDEX r/REMARK`

* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, ...
* The remark replaces any existing remark for that student.
* To remove a remark, use `remark INDEX r/` with nothing after `r/`.

Examples:
* `remark 1 r/Needs extra help with algebra` adds a remark to the 1st student.
* `remark 2 r/` removes the remark from the 2nd student.
```

#### 3. Update the `find` command section (lines 118-134)

Replace the current find section with the extended prefix-based syntax:

```markdown
### Locating students: `find`

Finds students matching the given criteria.

Format: `find [n/NAME_KEYWORDS] [s/SUBJECT] [d/DAY] [ps/PAYMENT_STATUS] [t/TAG]`

* **Backward compatible:** `find KEYWORD [MORE_KEYWORDS]` (without prefixes) still searches by name.
* When using prefixes, multiple criteria use AND logic.
* Name search is case-insensitive and matches full words only.
* Subject, day, and tag searches are case-insensitive.
* Payment status must be one of: `Paid`, `Due`, `Overdue`.

Examples:
* `find John` returns `john` and `John Doe` (backward compatible)
* `find s/Mathematics` returns all students taking Mathematics
* `find d/Monday` returns all students with Monday lessons
* `find ps/Due` returns all students with unpaid fees
* `find s/Math d/Monday` returns students taking Math on Mondays
* `find t/priority` returns students tagged as priority
```

#### 4. Document the `mark` command (new section, add after `remark` and before `delete`)

```markdown
### Updating payment status: `mark`

Quickly updates the payment status of a student.

Format: `mark INDEX ps/PAYMENT_STATUS`

* The index refers to the index number shown in the displayed student list.
* The index **must be a positive integer** 1, 2, 3, ...
* Payment status must be one of: `Paid`, `Due`, `Overdue`.

Examples:
* `mark 1 ps/Paid` marks the 1st student's payment as Paid.
* `mark 3 ps/Overdue` marks the 3rd student's payment as Overdue.
```

#### 5. Update the command summary table (lines 210-221)

Add rows for `remark`, `mark`, and update `find`:

```markdown
| Action     | Format, Examples |
|------------|------------------|
| **Add**    | `add n/NAME e/EMAIL a/ADDRESS ec/EMERGENCY_CONTACT [s/SUBJECT]... [d/DAY]... [ti/TIME]... [ps/PAYMENT_STATUS] [t/TAG]...` <br> e.g., `add n/John Doe e/johnd@example.com a/Clementi Ave 2 ec/91234567 s/Mathematics d/Monday ti/1400 ps/Due` |
| **Clear**  | `clear` |
| **Delete** | `delete INDEX` <br> e.g., `delete 3` |
| **Edit**   | `edit INDEX [n/NAME] [e/EMAIL] [a/ADDRESS] [ec/EMERGENCY_CONTACT] [s/SUBJECT]... [d/DAY]... [ti/TIME]... [ps/PAYMENT_STATUS] [t/TAG]...` <br> e.g., `edit 1 e/johndoe@example.com` |
| **Find**   | `find [n/NAME] [s/SUBJECT] [d/DAY] [ps/STATUS] [t/TAG]` <br> e.g., `find s/Mathematics d/Monday` |
| **Help**   | `help` |
| **List**   | `list` |
| **Mark**   | `mark INDEX ps/PAYMENT_STATUS` <br> e.g., `mark 1 ps/Paid` |
| **Remark** | `remark INDEX r/REMARK` <br> e.g., `remark 1 r/Needs help with algebra` |
| **View**   | `view INDEX` <br> e.g., `view 1` |
```

#### 6. Add parameter constraints section

Add after the "Notes about the command format" box (around line 68):

```markdown
</box>

**Parameter constraints:**

| Prefix | Field | Rules |
|--------|-------|-------|
| `n/` | Name | Alphanumeric characters and spaces, cannot be blank |
| `e/` | Email | Valid email format (e.g., `user@example.com`) |
| `a/` | Address | Any text, cannot be blank |
| `ec/` | Emergency Contact | Exactly 8 digits |
| `s/` | Subject | Alphanumeric characters, no spaces |
| `d/` | Day | Monday-Sunday (or Mon-Sun), case-insensitive |
| `ti/` | Time | 4-digit 24-hour format, 0000-2359 |
| `ps/` | Payment Status | One of: `Paid`, `Due`, `Overdue` |
| `t/` | Tag | Alphanumeric characters, no spaces |
| `r/` | Remark | Any text (free-form) |

**Important:** Days and times must be specified in matching pairs. If you provide 2 days, you must provide exactly 2 times.
```

---

### Part B: Restore Sample Data

**File:** `src/main/java/seedu/address/model/util/SampleDataUtil.java`

Restore `getSamplePersons()` with realistic tutor-relevant students. You need to add back imports for `Address`, `Email`, `EmergencyContact`, `Name`, `PaymentStatus`.

**Note:** If Ray's Remark PR is merged first, include `new Remark("")` in each Person constructor. If not merged yet, use the current 9-argument constructor and update after.

Sample data to add (using current constructor):
```java
public static Person[] getSamplePersons() {
    return new Person[] {
        new Person(new Name("Alex Tan"), new Email("alex@example.com"),
            new Address("Blk 30 Clementi Ave 2, #05-01"),
            getSubjectSet("Mathematics", "Physics"),
            getDaySet("Monday", "Wednesday"),
            getTimeSet("1400", "1600"),
            new EmergencyContact("91234567"),
            new PaymentStatus("Paid"),
            getTagSet("secondary")),
        new Person(new Name("Priya Sharma"), new Email("priya@example.com"),
            new Address("25 Bukit Timah Road"),
            getSubjectSet("English", "History"),
            getDaySet("Tuesday", "Thursday"),
            getTimeSet("1000", "1500"),
            new EmergencyContact("82345678"),
            new PaymentStatus("Due"),
            getTagSet("primary")),
        new Person(new Name("James Lee"), new Email("james@example.com"),
            new Address("Blk 123 Bishan St 12, #08-15"),
            getSubjectSet("Chemistry"),
            getDaySet("Friday"),
            getTimeSet("0900"),
            new EmergencyContact("93456789"),
            new PaymentStatus("Overdue"),
            getTagSet("jc")),
        new Person(new Name("Sarah Chen"), new Email("sarah@example.com"),
            new Address("10 Pasir Ris Drive 4"),
            getSubjectSet("Mathematics", "English"),
            getDaySet("Monday", "Saturday"),
            getTimeSet("1600", "1000"),
            new EmergencyContact("84567890"),
            new PaymentStatus("Paid"),
            getTagSet("secondary", "priority")),
        new Person(new Name("Ravi Kumar"), new Email("ravi@example.com"),
            new Address("Blk 456 Tampines St 42, #12-03"),
            getSubjectSet("Biology"),
            getDaySet("Wednesday"),
            getTimeSet("1100"),
            new EmergencyContact("95678901"),
            new PaymentStatus("Due"),
            getTagSet("jc"))
    };
}
```

Add back the required imports at the top of the file.

---

### Part C: UML

Update the UI class diagram (`docs/diagrams/UiClassDiagram.puml`) to reflect the current PersonCard layout (two-column grid, FlowPanes for subjects/days/times/tags).

---

## Testing

1. Run `./gradlew test checkstyleMain checkstyleTest` -- must all pass
2. Delete `data/addressbook.json` and launch the app -- sample students should appear
3. Preview the UserGuide markdown to verify formatting

---

## Do NOT touch

- `Person.java`, `JsonAdaptedPerson.java`, `RemarkCommand.java` -- assigned to Yi Zhong
- `FindCommand`, `FindCommandParser` -- assigned to Abhishek
- `MarkCommand` -- assigned to Ray
- `DeveloperGuide.md` -- assigned to Dayne
