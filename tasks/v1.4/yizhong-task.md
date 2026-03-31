# Yi Zhong -- Fix Day/Time + Remark Persistence

**Branch name:** `feature/fix-persistence-remark`
**PR target:** `master`
**Milestone:** `v1.4`
**Deadline:** Tuesday March 25, end of day (others depend on this)

---

## Why this is critical

Day and Time data is **silently lost on every app restart**. `JsonAdaptedPerson.java` does not save days or times to JSON, and always loads them as empty sets. Additionally, the `remark` command exists but does nothing because `Person` has no `Remark` field.

---

## What you need to do

### Part A: Fix Day/Time persistence (3 changes in 1 file)

**File:** `src/main/java/seedu/address/storage/JsonAdaptedPerson.java`

**1. Add fields** (around line 33, after `paymentStatus`):
```java
private final List<String> days = new ArrayList<>();
private final List<String> times = new ArrayList<>();
```

**2. Update the JSON constructor** (the `@JsonCreator` one, line 40-58):

Add parameters:
```java
@JsonProperty("days") List<String> days,
@JsonProperty("times") List<String> times,
```

And in the body:
```java
if (days != null) {
    this.days.addAll(days);
}
if (times != null) {
    this.times.addAll(times);
}
```

**3. Update the `Person` source constructor** (line 64-76):

Add after the subjects block:
```java
days.addAll(source.getDays().stream()
        .map(d -> d.dayName)
        .collect(Collectors.toList()));
times.addAll(source.getTimes().stream()
        .map(t -> t.timeValue)
        .collect(Collectors.toList()));
```

**4. Update `toModelType()`** (line 83-155):

Add Day/Time deserialization before the final `return` (before line 151):
```java
final Set<Day> modelDays = new HashSet<>();
for (String dayName : days) {
    if (!Day.isValidDay(dayName)) {
        throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
    }
    modelDays.add(new Day(dayName));
}

final Set<Time> modelTimes = new HashSet<>();
for (String timeValue : times) {
    if (!Time.isValidTime(timeValue)) {
        throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
    }
    modelTimes.add(new Time(timeValue));
}
```

Then change the return statement (line 152-154) from:
```java
return new Person(modelName, modelEmail, modelAddress,
        modelSubjects, new HashSet<>(), new HashSet<>(),
        modelEmergencyContact, modelPaymentStatus, modelTags);
```
To:
```java
return new Person(modelName, modelEmail, modelAddress,
        modelSubjects, modelDays, modelTimes,
        modelEmergencyContact, modelPaymentStatus, modelTags);
```

Add these imports at the top:
```java
import seedu.address.model.person.Day;
import seedu.address.model.person.Time;
```

---

### Part B: Add Remark to Person model (1 file)

**File:** `src/main/java/seedu/address/model/person/Person.java`

**1. Add field** (after `paymentStatus`, before `tags`, around line 29):
```java
private final Remark remark;
```

**2. Update constructor** -- add `Remark remark` parameter between `paymentStatus` and `tags`:
```java
public Person(Name name, Email email, Address address,
              Set<Subject> subjects, Set<Day> days, Set<Time> times,
              EmergencyContact emergencyContact, PaymentStatus paymentStatus,
              Remark remark, Set<Tag> tags) {
```
Add `remark` to the `requireAllNonNull` call and add `this.remark = remark;`.

**3. Add getter:**
```java
public Remark getRemark() {
    return remark;
}
```

**4. Update `equals()`** -- add `&& remark.equals(otherPerson.remark)` to the chain.

**5. Update `hashCode()`** -- add `remark` to the `Objects.hash(...)` call.

**6. Update `toString()`** -- add `.add("remark", remark)` to the builder chain.

---

### Part C: Fix RemarkCommand (1 file)

**File:** `src/main/java/seedu/address/logic/commands/RemarkCommand.java`

In `execute()` (lines 59-64), the `editedPerson` currently copies all fields but **never includes the remark**. Change it to:
```java
Person editedPerson = new Person(
        personToEdit.getName(), personToEdit.getEmail(),
        personToEdit.getAddress(), personToEdit.getSubjects(),
        personToEdit.getDays(), personToEdit.getTimes(),
        personToEdit.getEmergencyContact(),
        personToEdit.getPaymentStatus(), remark,
        personToEdit.getTags());
```

Note: `remark` (not `personToEdit.getRemark()`) -- we use the new remark from the command.

---

### Part D: Update all `new Person(...)` calls to include Remark

Since you changed the `Person` constructor, every file that constructs a `Person` needs updating. Pass `new Remark("")` for default empty remarks.

Files to update:
- `src/main/java/seedu/address/logic/parser/AddCommandParser.java` (line 96) -- add `new Remark("")` between `paymentStatus` and `tagList`
- `src/main/java/seedu/address/logic/commands/EditCommand.java` (line 143, in `createEditedPerson`) -- add `personToEdit.getRemark()` to preserve existing remark
- `src/main/java/seedu/address/storage/JsonAdaptedPerson.java` (in `toModelType()`) -- add remark deserialization (see Part E below)
- `src/test/java/seedu/address/testutil/PersonBuilder.java` -- add `private Remark remark;` field, `withRemark()` method, pass `remark` in `build()`
- `src/test/java/seedu/address/logic/commands/RemarkCommandTest.java` -- update the `new Person(...)` calls to include `new Remark("")` or `new Remark(REMARK_STUB)`

Add this import wherever needed:
```java
import seedu.address.model.person.Remark;
```

---

### Part E: Persist Remark in JSON (back to JsonAdaptedPerson)

In `JsonAdaptedPerson.java`:

**1. Add field:**
```java
private final String remark;
```

**2. In the `@JsonCreator` constructor**, add parameter `@JsonProperty("remark") String remark` and set `this.remark = remark;`.

**3. In the `Person` source constructor**, add:
```java
remark = source.getRemark().value;
```

**4. In `toModelType()`**, add before the return:
```java
final Remark modelRemark = new Remark(remark != null ? remark : "");
```

Pass `modelRemark` in the `new Person(...)` call.

---

### Part F: Display Remark in UI (3 files)

**1. `src/main/java/seedu/address/ui/PersonCard.java`** -- add:
```java
@FXML
private Label remark;
```
In the constructor, after payment status:
```java
String remarkText = person.getRemark().value;
remark.setText(remarkText.isEmpty() ? "" : "Remark: " + remarkText);
remark.setVisible(!remarkText.isEmpty());
remark.setManaged(!remarkText.isEmpty());
```

**2. `src/main/resources/view/PersonListCard.fxml`** -- add in the right column VBox (after paymentStatus label):
```xml
<Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
```

**3. `src/main/java/seedu/address/ui/PersonViewDialog.java`** -- add `@FXML private Label remarkLabel;` and in `setPerson()`:
```java
remarkLabel.setText(person.getRemark().value.isEmpty() ? "None" : person.getRemark().value);
```

**4. `src/main/resources/view/PersonViewDialog.fxml`** -- add a new row (row index 8) in the GridPane:
```xml
<Label text="Remark:" GridPane.rowIndex="8" GridPane.columnIndex="0" />
<Label fx:id="remarkLabel" wrapText="true" GridPane.rowIndex="8" GridPane.columnIndex="1" />
```
Add another `<RowConstraints minHeight="25.0" />` to match.

---

### Part G: Update Messages.format()

**File:** `src/main/java/seedu/address/logic/Messages.java`

In the `format()` method (line 43-59), add Days, Times, and Remark:
```java
builder.append("; Days: ");
person.getDays().forEach(builder::append);
builder.append("; Times: ");
person.getTimes().forEach(builder::append);
builder.append("; Remark: ")
       .append(person.getRemark());
```

---

## Testing

1. Run `./gradlew test checkstyleMain checkstyleTest` -- must all pass
2. Launch the app, add a student with days/times, close and reopen -- days/times should persist
3. Run `remark 1 r/Needs help with algebra` -- remark should appear in the UI card and view dialog
4. Run `remark 1 r/` -- remark should be cleared

---

## UML

Update the Model class diagram (`docs/diagrams/ModelClassDiagram.puml`) to show the `Remark` field in `Person`.

---

## Do NOT touch

- `FindCommand`, `FindCommandParser` -- assigned to Abhishek
- `MarkCommand` -- assigned to Ray
- `UserGuide.md` -- assigned to Randall
- `DeveloperGuide.md` -- assigned to Dayne
