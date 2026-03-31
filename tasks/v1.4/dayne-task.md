# Dayne -- Developer Guide + Integration Testing

**Branch name:** `feature/update-dg-v1.4`
**PR target:** `master`
**Milestone:** `v1.4`
**Deadline:** Thursday March 26, before release
**Depends on:** All other PRs should be merged first

---

## Context

You are the last to merge. Your job is to update the Developer Guide with implementation details for the new v1.4 features, add UML diagrams, and then do a final integration test on master to make sure everything works together.

---

## What you need to do

### Part 1: Update Developer Guide

**File:** `docs/DeveloperGuide.md`

#### A. Add implementation details for Extended Find

Add a new subsection under the Implementation section:

```markdown
### Find by field feature

The extended find mechanism builds on the existing `find` command by supporting
prefix-based filtering. When prefixes are used (e.g., `find s/Math d/Monday`),
the parser creates a composite predicate using `Predicate.and()` to combine
individual field predicates.

The following predicate classes are used:
- `NameContainsKeywordsPredicate` -- matches by name
- `SubjectContainsKeywordsPredicate` -- matches by subject
- `DayMatchesPredicate` -- matches by lesson day
- `PaymentStatusMatchesPredicate` -- matches by payment status
- `TagContainsKeywordsPredicate` -- matches by tag

When no prefix is provided, the command falls back to name-only search
for backward compatibility.
```

Include a sequence diagram showing the flow: User input -> `FindCommandParser` -> creates predicates -> `FindCommand.execute()` -> `Model.updateFilteredPersonList()`.

#### B. Add implementation details for Mark Payment

Add another subsection:

```markdown
### Mark payment status feature

The `mark` command provides a shortcut to update a student's payment status
without using the full `edit` command. It creates a new `Person` object with
the updated `PaymentStatus` while preserving all other fields.
```

Include a sequence diagram for the mark command.

#### C. Update Model section

Update the Person model description to include the `Remark` field. The current field list should be:
- `Name`, `Email`, `Address`, `Set<Subject>`, `Set<Day>`, `Set<Time>`, `EmergencyContact`, `PaymentStatus`, `Remark`, `Set<Tag>`

#### D. Add/update use cases

Add these use cases if not already present:

**UC06 -- Find students by field:**
- Actor: Tutor
- Precondition: At least one student exists
- MSS: Actor enters find command with field prefix -> System filters and displays matching students
- Extensions: No matches found -> System shows "0 persons listed"

**UC07 -- Mark payment status:**
- Actor: Tutor
- Precondition: At least one student exists
- MSS: Actor enters mark command -> System updates payment status and confirms
- Extensions: Invalid index or status -> System shows error

**UC08 -- Add remark to student:**
- Actor: Tutor
- Precondition: At least one student exists
- MSS: Actor enters remark command -> System updates remark and confirms

#### E. Add user stories

Add to the user stories table:
- `* * *` | As a tutor | I can search students by subject, day, or payment status | so I can quickly find relevant students
- `* *` | As a tutor | I can quickly mark a student's payment as paid | so I can track payments efficiently
- `* *` | As a tutor | I can add remarks to a student | so I can remember important notes about them

#### F. Rebrand remaining AddressBook references

Scan for any remaining "AddressBook" references in the DG that should say "Tutor Central" (keep "AddressBook" only when referring to Java class names like `AddressBook.java`).

---

### Part 2: UML Diagrams

Add at least one new sequence diagram. Recommended:

**`docs/diagrams/FindSequenceDiagram.puml`** or **`docs/diagrams/MarkSequenceDiagram.puml`**

Follow the existing PlantUML style used in the project. Example structure:
```
@startuml
participant User
participant MarkCommandParser
participant MarkCommand
participant Model

User -> MarkCommandParser : parse("1 ps/Paid")
activate MarkCommandParser
MarkCommandParser -> MarkCommand : new MarkCommand(index, status)
deactivate MarkCommandParser

User -> MarkCommand : execute(model)
activate MarkCommand
MarkCommand -> Model : getFilteredPersonList()
MarkCommand -> Model : setPerson(target, marked)
MarkCommand -> Model : updateFilteredPersonList()
MarkCommand --> User : CommandResult
deactivate MarkCommand
@enduml
```

---

### Part 3: Integration Testing

After all PRs are merged into master, pull the latest and run:

```bash
git checkout master
git fetch upstream
git pull upstream master
./gradlew clean test checkstyleMain checkstyleTest
```

If anything fails:
1. Identify what broke and coordinate with the relevant person
2. Fix it on a new branch or coordinate a fix
3. Report to Randall so the release can proceed

Also do a manual smoke test:
- Add a student with all fields (name, email, address, subjects, days, times, emergency contact, payment status, tags)
- Close and reopen the app -- verify all data persists
- Run `remark 1 r/Test remark` -- verify it shows in UI
- Run `find s/Mathematics` -- verify it filters correctly
- Run `mark 1 ps/Paid` -- verify status updates
- Run `view 1` -- verify popup shows all fields including remark

---

## Testing your own changes

```bash
./gradlew checkstyleMain checkstyleTest
```

Preview the DeveloperGuide markdown to verify formatting. If you updated `.puml` diagrams, check they render correctly using the PlantUML plugin or plantuml.com.

---

## Do NOT touch

- Any Java source files (commands, parsers, model, UI, storage)
- `UserGuide.md` -- assigned to Randall
- FXML/CSS files
