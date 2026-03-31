# v1.5 Task Brief — Ray Chua

**Role:** Code quality, test coverage, code authorship  
**Deadline:** Thu Apr 2 23:59 (buffer: Apr 9)  
**Code authorship freeze:** PE-D day at 10am

---

## What You Need To Do

### 1. Check test coverage

Run the coverage report:
```bash
./gradlew clean test coverage
```
Open `build/reports/jacoco/coverage/html/index.html` in a browser.

Look for classes/methods in `src/main/java/seedu/address/` (excluding `ui/`) with low line/branch coverage. Add tests for anything below ~70%.

Priority classes to check:
- `logic/commands/` — every command should have edge case tests
- `logic/parser/` — test invalid formats, boundary values, duplicate prefixes
- `model/person/` — test validation edge cases (e.g. empty strings, special characters)

### 2. Fix checkstyle violations

```bash
./gradlew checkstyleMain checkstyleTest
```

If violations are found, fix them. Common issues:
- Lines > 120 characters
- Missing Javadoc on public methods
- Wrong import order (STATIC → `java.*` → `javax.*` → `org.*` → `com.*` → project)
- Wildcard imports

### 3. Verify code authorship (RepoSense)

1. Go to the [tP Code Dashboard](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/)
2. Search for your GitHub username (`stinkray77`)
3. Click `</>` to see which lines are attributed to you
4. Green lines = attributed to you. Verify these actually reflect code you wrote.

If attribution is wrong (lines you wrote are attributed to someone else, or vice versa):
- Add `// @@author stinkray77` before your code blocks
- Add `// @@author` (empty) to end the attribution block
- See [RepoSense docs](https://reposense.org/ug/customizingReports.html) for details

**Do this before PE-D day at 10am** — changes after that are subject to feature freeze.

### 4. Alpha Testing

Submit at least **5 issues** labelled `alpha-bug` tagged to the **v1.5 milestone**.

Suggested test focus (data/model layer):
- Save corrupted JSON file → reopen app → does it start cleanly with empty data?
- Delete `tutorcentral.json` → reopen → does sample data load?
- Add 50+ students → does performance stay acceptable?
- Day/time pair validation: `d/Monday d/Tuesday ti/1400` (2 days, 1 time) → should error
- Emergency contact: 7 digits, 9 digits (only 8 digits valid) → should error

---

## Branch/PR workflow

Create a branch `fix/code-quality-ray` for your changes. Open a PR to `master` when done.
