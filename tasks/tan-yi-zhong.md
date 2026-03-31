# v1.5 Task Brief — Tan Yi Zhong

**Role:** Alpha test coordination + Release v1.5  
**Deadline:** Thu Apr 2 23:59 (buffer: Apr 9)  
**PE-D freeze:** PE-D day at 10am (no new releases after this)

---

## What You Need To Do

### 1. Coordinate Alpha Testing

- Assign 2 non-authors to each feature (everyone tests features they didn't write)
- Set a testing deadline early (Day 2–3 of the iteration so there's time to fix bugs)
- Each person must submit **≥5 issues** labelled `alpha-bug` tagged to the **v1.5 milestone**

Suggested assignments (adjust as needed):
| Feature | Testers |
|---------|---------|
| add/edit/delete | Abhishek + Dayne |
| find/list | Ray + Randall |
| mark/remark | Dayne + Abhishek |
| view/help | Randall + Ray |

### 2. Build the JAR

Once all PRs for v1.5 are merged into `master`:

```bash
git checkout master
git pull
./gradlew clean shadowJar
```

The JAR will be at `build/libs/addressbook.jar`. Rename it to `TutorCentral.jar`.

### 3. Smoke Test the JAR

```bash
java -version   # must show Java 17
java -jar TutorCentral.jar
```

Verify:
- App launches with sample data
- A few commands work: `list`, `add n/Test e/t@t.com a/addr s/Math ec/91234567 ps/Paid`, `delete 1`
- App closes cleanly

If possible, smoke-test on at least one other OS (post in forum if you need help).

### 4. Create GitHub Release v1.5

1. Go to the repository → **Releases** → **Draft a new release**
2. Tag: `v1.5`
3. Target: `master`
4. Title: `v1.5`
5. Attach the following as assets (not embedded in the body):
   - `TutorCentral.jar`
   - `TutorCentral-UserGuide.pdf` (from Dayne)
   - `TutorCentral-DeveloperGuide.pdf` (from Abhishek)
6. Publish the release

> **Do NOT delete previous releases** — preserve release history.

### 5. Wrap Up the Milestone

1. Go to GitHub → **Milestones** → **v1.5**
2. Close or reschedule any remaining open issues (move non-critical ones to v1.6 or close as `not planned`)
3. Close the v1.5 milestone

### 6. Alpha Testing

Submit at least **5 issues** labelled `alpha-bug` tagged to the **v1.5 milestone**.

Suggested test focus (DevOps/release):
- Does `java -jar TutorCentral.jar` work after a fresh download to an empty folder?
- Does the app work on a different machine (different Java 17 distribution)?
- Is the JAR file size reasonable (< 100 MB)?
- Does the app create its data folder on first run?
- Does the window size/position persist after closing and reopening?
