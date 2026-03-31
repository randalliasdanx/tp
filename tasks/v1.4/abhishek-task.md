# Abhishek -- Extended Find Command

**Branch name:** `feature/extended-find`
**PR target:** `master`
**Milestone:** `v1.4`
**Deadline:** Wednesday March 25, end of day
**Depends on:** Yi Zhong's persistence PR must be merged first

---

## Context

Currently `find` only searches by name (`find alice bob`). We need prefix-based search so tutors can filter by subject, day, payment status, and tags too.

**Target syntax:**
```
find n/alice                  -> find by name
find s/Mathematics            -> find by subject
find d/Monday                 -> find students with Monday lessons
find ps/Due                   -> find unpaid students
find t/priority               -> find by tag
find s/Math d/Monday          -> AND logic: Math students on Monday
find alice                    -> backward compatible: search by name
```

---

## What you need to do (6+ files)

### 1. Create new predicate classes in `src/main/java/seedu/address/model/person/`

Model each after `NameContainsKeywordsPredicate.java`. Each implements `Predicate<Person>`.

**a. `SubjectContainsKeywordsPredicate.java`**
```java
public class SubjectContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    // constructor, test(), equals(), toString()
}
```
`test()` should check if any of the person's subjects contain any keyword (case-insensitive, partial match is fine for subjects).

**b. `DayMatchesPredicate.java`**
```java
public class DayMatchesPredicate implements Predicate<Person> {
    private final List<String> days;
}
```
`test()` should check if any of the person's days match any of the given days (case-insensitive).

**c. `PaymentStatusMatchesPredicate.java`**
```java
public class PaymentStatusMatchesPredicate implements Predicate<Person> {
    private final String status;
}
```
`test()` should check if the person's payment status matches (case-insensitive).

**d. `TagContainsKeywordsPredicate.java`**
```java
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
}
```
`test()` should check if any of the person's tags match any keyword (case-insensitive).

### 2. Rewrite `FindCommand.java`

**File:** `src/main/java/seedu/address/logic/commands/FindCommand.java`

Change the field from `NameContainsKeywordsPredicate` to a generic `Predicate<Person>`:
```java
private final Predicate<Person> predicate;

public FindCommand(Predicate<Person> predicate) {
    this.predicate = predicate;
}
```

Update `MESSAGE_USAGE` to document the new prefix syntax.

Update `equals()` accordingly.

### 3. Rewrite `FindCommandParser.java`

**File:** `src/main/java/seedu/address/logic/parser/FindCommandParser.java`

Use `ArgumentTokenizer` with prefixes `n/`, `s/`, `d/`, `ps/`, `t/`:

```java
ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
        PREFIX_NAME, PREFIX_SUBJECT, PREFIX_DAY,
        PREFIX_PAYMENT_STATUS, PREFIX_TAG);
```

Logic:
1. If no prefixes are present (just bare keywords in the preamble), treat as name search (backward compatible)
2. If prefixes are present, create the appropriate predicate for each
3. Combine multiple predicates with AND logic using `Predicate.and()`

Example structure:
```java
Predicate<Person> combinedPredicate = person -> true;

if preamble is not empty and no prefixes used:
    // backward compatible name search
    combinedPredicate = new NameContainsKeywordsPredicate(keywords);
else:
    if n/ present:
        combinedPredicate = combinedPredicate.and(new NameContainsKeywordsPredicate(...));
    if s/ present:
        combinedPredicate = combinedPredicate.and(new SubjectContainsKeywordsPredicate(...));
    if d/ present:
        combinedPredicate = combinedPredicate.and(new DayMatchesPredicate(...));
    if ps/ present:
        combinedPredicate = combinedPredicate.and(new PaymentStatusMatchesPredicate(...));
    if t/ present:
        combinedPredicate = combinedPredicate.and(new TagContainsKeywordsPredicate(...));
```

Import the relevant prefixes from `CliSyntax`.

### 4. Tests

Create test files in `src/test/java/seedu/address/model/person/`:
- `SubjectContainsKeywordsPredicateTest.java`
- `DayMatchesPredicateTest.java`
- `PaymentStatusMatchesPredicateTest.java`
- `TagContainsKeywordsPredicateTest.java`

Update `src/test/java/seedu/address/logic/parser/FindCommandParserTest.java` with tests for the new prefix-based parsing.

Update `src/test/java/seedu/address/logic/commands/FindCommandTest.java` if needed (the predicate type changed).

---

## Testing

1. Run `./gradlew test checkstyleMain checkstyleTest` -- must all pass
2. Manual tests:
   - `find alice` still works (backward compatible)
   - `find s/Mathematics` returns students taking Mathematics
   - `find d/Monday` returns students with Monday lessons
   - `find ps/Due` returns unpaid students
   - `find s/Math d/Monday` returns Math students on Mondays only

---

## UML

Add a sequence diagram for the extended find command flow in `docs/diagrams/` (e.g., `FindSequenceDiagram.puml`).

---

## Do NOT touch

- `Person.java`, `JsonAdaptedPerson.java` -- assigned to Yi Zhong
- `MarkCommand` -- assigned to Ray
- `UserGuide.md` -- assigned to Randall
- `DeveloperGuide.md` -- assigned to Dayne
