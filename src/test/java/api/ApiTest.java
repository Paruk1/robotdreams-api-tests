package api;

import enums.Mark;
import enums.TargetType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import requestData.*;
import responseData.*;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ApiTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(ApiTest.class);

    @Test(description = "Create a Student")
    public void studentCreationTest() {
        try {
            log.info("TEST CASE = Create a Student");

            StudentData studentData = new StudentData("Andriy", "Parail");

            log.info("Send request for creating a Student with params: {}", studentData);
            Student studentFirst = RestAssured.given()
                    .body(studentData)
                    .post("/students")
                    .then().statusCode(200)
                    .extract().as(Student.class);
            log.info("Getting the information from response: {}", studentFirst);

            Assert.assertNotNull(studentFirst.id, "Student wasn't created");
            log.info("FINISHED SUCCESSFULLY - Create a Student");
        } catch (AssertionError error) {
            log.error("ERROR: {}", error.getMessage());
            throw error;
        }
    }

    @Test(description = "Find a Student by name")
    public void findStudentByName() {
        try {
            log.info("TEST CASE = Find a Student by name");

            StudentData studentData = new StudentData("Andriy", "Parail");
            log.info("Send request for creating a Student with params: {}", studentData);
            Student studentFirst = RestAssured.given()
                    .body(new StudentData("Andriy", "Parail"))
                    .post("/students")
                    .then().statusCode(200)
                    .extract().as(Student.class);
            Assert.assertNotNull(studentFirst.id);
            log.info("Getting the information from response: {}", studentFirst);

            log.info("Try to find student by name: {}", studentFirst.first_name);
            Student[] result = RestAssured.given()
                    .queryParams("name", studentFirst.first_name)
                    .get("/students")
                    .then().statusCode(200)
                    .extract().as(Student[].class);

            Assert.assertTrue(Arrays.asList(result).contains(studentFirst),
                    String.format("Can't find a student with id = %d and name = %s", studentFirst.id, studentFirst.first_name));

            log.info("FINISHED SUCCESSFULLY - Find a Student by name");
        } catch (AssertionError error) {
            log.error( "ERROR: {}", error.getMessage());
            throw error;
        }
    }

    @Test(description = "Find a Student by last_name")
    public void findStudentByLastNameTest() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Student[] result = RestAssured.given()
                .queryParams("last_name", studentFirst.last_name)
                .get("/students")
                .then().statusCode(200)
                .extract().as(Student[].class);

        Assert.assertTrue(Arrays.asList(result).contains(studentFirst),
                String.format("Can't find a student with id = %d and last_name = %s", studentFirst.id, studentFirst.last_name));
    }

    @Test(description = "Update parameters of Student")
    public void tryToUpdateStudentTest() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        StudentData studentChanged = new StudentData("AndriyChanged", "ParailChanged");

        StudentData result = RestAssured.given()
                .body(studentChanged)
                .put(String.format("/students/%d", studentFirst.id))
                .then().statusCode(200)
                .extract().as(StudentData.class);

        Assert.assertEquals(result, studentChanged,
                "After update the student, students aren't equals");
    }

    @Test(description = "Creating a group of students")
    public void tryToCreateGroupOfStudentsTest() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Student studentSecond = RestAssured.given()
                .body(new StudentData("Sashsa", "Pozenko"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentSecond.id);

        Student studentThird = RestAssured.given()
                .body(new StudentData("Masha", "Krivosheeva"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentThird.id);

        Integer[] studentIds = {
                studentFirst.id,
                studentSecond.id,
                studentThird.id
        };

        Group group = RestAssured.given()
                .body(new GroupData("Footballers", studentIds))
                .post("/groups")
                .then().statusCode(200)
                .extract().as(Group.class);

        List<Student> studentList = Arrays.asList(group.students);

        assertThat("Error after creating the Group of Students ",
                studentList, containsInAnyOrder(studentFirst, studentSecond, studentThird));
    }

    @Test(description = "Creating a Content")
    public void tryToCreateContentTest() {
        Content contentCreated = RestAssured.given()
                .body(new ContentData("Українська мова!"))
                .post("/content")
                .then().statusCode(200)
                .extract().as(Content.class);

        Content[] contentList = RestAssured.get("/content").as(Content[].class);

        Assert.assertTrue(Arrays.asList(contentList).contains(contentCreated),
                "Error with creating a Content");
    }

    @Test(description = "Assignments for Students")
    public void tryToCreateAssignmentForStudentTest() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Student studentSecond = RestAssured.given()
                .body(new StudentData("Sashsa", "Pozenko"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentSecond.id);

        Student studentThird = RestAssured.given()
                .body(new StudentData("Masha", "Krivosheeva"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentThird.id);

        Integer[] studentIds = {
                studentFirst.id,
                studentSecond.id,
                studentThird.id
        };

        Group group = RestAssured.given()
                .body(new GroupData("Footballers", studentIds))
                .post("/groups")
                .then().statusCode(200)
                .extract().as(Group.class);

        List<Student> studentList = Arrays.asList(group.students);

        assertThat("Error after creating the Group of Students ",
                studentList, containsInAnyOrder(studentFirst, studentSecond, studentThird));

        Content contentCreated = RestAssured.given()
                .body(new ContentData("Ukrainian language"))
                .post("/content")
                .then().statusCode(200)
                .extract().as(Content.class);

        Content[] contentList = RestAssured.get("/content").as(Content[].class);

        Assert.assertTrue(Arrays.asList(contentList).contains(contentCreated),
                "Error with creating a Content");

        Assignment[] assignmentForGroup = RestAssured.given()
                .body(new AssignmentForGroupData(TargetType.GROUP, String.valueOf(group.id), contentCreated.id))
                .post("/assignments").then().statusCode(200)
                .extract().as(Assignment[].class);

        for (Assignment assignment : assignmentForGroup) {
            Assert.assertEquals(String.valueOf(assignment.content_id), contentCreated.id,
                    String.format("Assignment with id = %d hasn't a content: id = %s", assignment.content_id, contentCreated.id));
        }

        for (Assignment assignment : assignmentForGroup) {
            StudentAssignment studentAssignmentResponse = RestAssured.given()
                    .body(new SolutionData("Do exercise 1 and 2"))
                    .post(String.format("/assignments/%d/solution", assignment.id))
                    .then().extract().as(StudentAssignment.class);

            Assert.assertEquals(studentAssignmentResponse.solution, "Do exercise 1 and 2",
                    String.format("The Student with id = %d, doesn't have a solution = %s", studentAssignmentResponse.student.id, "Do exercise 1 and 2"));
        }
    }

    @Test(description = "Mark an assignments")
    public void tryToMarkAnAssignmentTest() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Student studentSecond = RestAssured.given()
                .body(new StudentData("Sashsa", "Pozenko"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentSecond.id);

        Student studentThird = RestAssured.given()
                .body(new StudentData("Masha", "Krivosheeva"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentThird.id);

        Integer[] studentIds = {
                studentFirst.id,
                studentSecond.id,
                studentThird.id
        };

        Group group = RestAssured.given()
                .body(new GroupData("Footballers", studentIds))
                .post("/groups")
                .then().statusCode(200)
                .extract().as(Group.class);

        List<Student> studentList = Arrays.asList(group.students);

        assertThat("Error after creating the Group of Students ",
                studentList, containsInAnyOrder(studentFirst, studentSecond, studentThird));

        Content contentCreated = RestAssured.given()
                .body(new ContentData("Ukrainian language"))
                .post("/content")
                .then().statusCode(200)
                .extract().as(Content.class);

        Content[] contentList = RestAssured.get("/content").as(Content[].class);

        Assert.assertTrue(Arrays.asList(contentList).contains(contentCreated),
                "Error with creating a Content");

        Assignment[] assignmentForGroup = RestAssured.given()
                .body(new AssignmentForGroupData(TargetType.GROUP, String.valueOf(group.id), contentCreated.id))
                .post("/assignments").then().statusCode(200)
                .extract().as(Assignment[].class);

        for (Assignment assignment : assignmentForGroup) {
            Assert.assertEquals(String.valueOf(assignment.content_id), contentCreated.id,
                    String.format("Assignment with id = %d hasn't a content: id = %s", assignment.content_id, contentCreated.id));
        }

        for (Assignment assignment : assignmentForGroup) {
            StudentAssignment studentAssignmentResponse = RestAssured.given()
                    .body(new SolutionData("Do exercise 1 and 2"))
                    .post(String.format("/assignments/%d/solution", assignment.id))
                    .then().extract().as(StudentAssignment.class);

            Assert.assertEquals(studentAssignmentResponse.solution, "Do exercise 1 and 2",
                    String.format("The Student with id = %d, doesn't have a solution = %s", studentAssignmentResponse.student.id, "Do exercise 1 and 2"));
        }

        for (Assignment assignment : assignmentForGroup) {
            StudentAssignment studentAssignment = RestAssured.given()
                    .body(new MarkAssignmentData(Mark.FIVE))
                    .post(String.format("/assignments/%d/mark", assignment.id))
                    .then().extract().as(StudentAssignment.class);

            Assert.assertEquals(studentAssignment.mark, Mark.FIVE.getMarkNum(), String.format("ERROR: Assignment with id = %d and mark - %d is not equal to real mark - %d", assignment.id, studentAssignment.mark, Mark.FIVE.getMarkNum()));
        }
    }

    @Test(description = "Automate mark assignment with solution")
    public void tryToAutomateMarkAssignmentWithSolution() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Content contentCreated = RestAssured.given()
                .body(new ContentData("Ukrainian language"))
                .post("/content")
                .then().statusCode(200)
                .extract().as(Content.class);

        Content[] contentList = RestAssured.get("/content").as(Content[].class);

        Assert.assertTrue(Arrays.asList(contentList).contains(contentCreated),
                "Error with creating a Content");

        Assignment assignment = RestAssured.given()
                .body(new AssignmentForStudentData(TargetType.STUDENT, contentCreated.id, String.valueOf(studentFirst.id)))
                .post("/assignments")
                .then().extract().as(Assignment.class);

        Assert.assertEquals(String.valueOf(assignment.content_id), contentCreated.id,
                String.format("Assignment with id = %d hasn't a content: id = %s", assignment.content_id, contentCreated.id));

        StudentAssignment studentAssignmentResponse = RestAssured.given()
                .body(new SolutionData("Do exercise 1 and 2"))
                .post(String.format("/assignments/%d/solution", assignment.id))
                .then().extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignmentResponse.solution, "Do exercise 1 and 2",
                String.format("The Student with id = %d, doesn't have a solution = %s", studentAssignmentResponse.student.id, "Do exercise 1 and 2"));

        RestAssured.given()
                .post(String.format("/students/%d/year_end", studentFirst.id))
                .then().statusCode(201);

        StudentAssignment studentAssignment = RestAssured.get(String.format("/assignments/%d", assignment.id))
                .then().statusCode(200)
                .extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignment.mark, Mark.FIVE.getMarkNum(),
                String.format("StudentAssignment didn't get a mark=5, result=%d", studentAssignment.mark));
    }

    @Test(description = "Automate mark assignment without solution")
    public void tryToMarkAssignmentWithoutSolution() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Content contentCreated = RestAssured.given()
                .body(new ContentData("Ukrainian language"))
                .post("/content")
                .then().statusCode(200)
                .extract().as(Content.class);

        Content[] contentList = RestAssured.get("/content").as(Content[].class);

        Assert.assertTrue(Arrays.asList(contentList).contains(contentCreated),
                "Error with creating a Content");

        Assignment assignment = RestAssured.given()
                .body(new AssignmentForStudentData(TargetType.STUDENT, contentCreated.id, String.valueOf(studentFirst.id)))
                .post("/assignments")
                .then().extract().as(Assignment.class);

        Assert.assertEquals(String.valueOf(assignment.content_id), contentCreated.id,
                String.format("Assignment with id = %d hasn't a content: id = %s", assignment.content_id, contentCreated.id));

        RestAssured.given()
                .post(String.format("/students/%d/year_end", studentFirst.id))
                .then().statusCode(201);

        StudentAssignment studentAssignment = RestAssured.get(String.format("/assignments/%d", assignment.id))
                .then().statusCode(200)
                .extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignment.mark, Mark.ONE.getMarkNum(),
                String.format("StudentAssignment didn't get a mark=1, result=%d", studentAssignment.mark));
    }

    @Test(description = "Automate mark assignment second time with solution")
    public void checkThatAutomateMarkAssignmentNotChangeMark() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Content contentCreated = RestAssured.given()
                .body(new ContentData("Ukrainian language"))
                .post("/content")
                .then().statusCode(200)
                .extract().as(Content.class);

        Content[] contentList = RestAssured.get("/content").as(Content[].class);

        Assert.assertTrue(Arrays.asList(contentList).contains(contentCreated),
                "Error with creating a Content");

        Assignment assignment = RestAssured.given()
                .body(new AssignmentForStudentData(TargetType.STUDENT, contentCreated.id, String.valueOf(studentFirst.id)))
                .post("/assignments")
                .then().extract().as(Assignment.class);

        Assert.assertEquals(String.valueOf(assignment.content_id), contentCreated.id,
                String.format("Assignment with id = %d hasn't a content: id = %s", assignment.content_id, contentCreated.id));

        StudentAssignment studentAssignmentResponse = RestAssured.given()
                .body(new SolutionData("Do exercise 1 and 2"))
                .post(String.format("/assignments/%d/solution", assignment.id))
                .then().extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignmentResponse.solution, "Do exercise 1 and 2",
                String.format("The Student with id = %d, doesn't have a solution = %s", studentAssignmentResponse.student.id, "Do exercise 1 and 2"));

        RestAssured.given()
                .post(String.format("/students/%d/year_end", studentFirst.id))
                .then().statusCode(201);

        StudentAssignment studentAssignment = RestAssured.get(String.format("/assignments/%d", assignment.id))
                .then().statusCode(200)
                .extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignment.mark, Mark.FIVE.getMarkNum(),
                String.format("StudentAssignment didn't get a mark=5, result=%d", studentAssignment.mark));

        RestAssured.given()
                .post(String.format("/students/%d/year_end", studentFirst.id))
                .then().statusCode(201);

        StudentAssignment studentAssignment2 = RestAssured.get(String.format("/assignments/%d", assignment.id))
                .then().statusCode(200)
                .extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignment2.mark, Mark.FIVE.getMarkNum(),
                String.format("StudentAssignment didn't get a mark=5, result=%d", studentAssignment2.mark));
    }

    @Test(description = "Input a new solution for Assignment")
    public void checkThatReSetSolutionIsSettingMark0() {
        Student studentFirst = RestAssured.given()
                .body(new StudentData("Andriy", "Parail"))
                .post("/students")
                .then().statusCode(200)
                .extract().as(Student.class);
        Assert.assertNotNull(studentFirst.id);

        Content contentCreated = RestAssured.given()
                .body(new ContentData("Ukrainian language"))
                .post("/content")
                .then().statusCode(200)
                .extract().as(Content.class);

        Content[] contentList = RestAssured.get("/content").as(Content[].class);

        Assert.assertTrue(Arrays.asList(contentList).contains(contentCreated),
                "Error with creating a Content");

        Assignment assignment = RestAssured.given()
                .body(new AssignmentForStudentData(TargetType.STUDENT, contentCreated.id, String.valueOf(studentFirst.id)))
                .post("/assignments")
                .then().extract().as(Assignment.class);

        Assert.assertEquals(String.valueOf(assignment.content_id), contentCreated.id,
                String.format("Assignment with id = %d hasn't a content: id = %s", assignment.content_id, contentCreated.id));

        StudentAssignment studentAssignmentResponse = RestAssured.given()
                .body(new SolutionData("Do exercise 1 and 2"))
                .post(String.format("/assignments/%d/solution", assignment.id))
                .then().extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignmentResponse.solution, "Do exercise 1 and 2",
                String.format("The Student with id = %d, doesn't have a solution = %s", studentAssignmentResponse.student.id, "Do exercise 1 and 2"));

        RestAssured.given()
                .post(String.format("/students/%d/year_end", studentFirst.id))
                .then().statusCode(201);

        StudentAssignment studentAssignment = RestAssured.get(String.format("/assignments/%d", assignment.id))
                .then().statusCode(200)
                .extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignment.mark, Mark.FIVE.getMarkNum(),
                String.format("StudentAssignment didn't get a mark=5, result=%d", studentAssignment.mark));

        StudentAssignment studentAssignmentResponse2 = RestAssured.given()
                .body(new SolutionData("Do exercise 3 and 4"))
                .post(String.format("/assignments/%d/solution", assignment.id))
                .then().extract().as(StudentAssignment.class);

        Assert.assertEquals(studentAssignmentResponse2.solution, "Do exercise 3 and 4",
                String.format("The Student with id = %d, doesn't have a solution = %s", studentAssignmentResponse.student.id, "Do exercise 1 and 2"));

        Assert.assertNull(studentAssignmentResponse2.mark);
    }

}
