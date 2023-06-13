package org.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class StudentRepositoryTest {
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        studentRepository = new StudentRepository();
    }
    @Test
    public void testAddStudent_ValidData_Success() {
        Assertions.assertDoesNotThrow(() ->
                studentRepository.addStudent("John", "Doe", "2000-01-01", "Male", "1234567890"));
        Assertions.assertEquals(1, studentRepository.listStudentsByLastName().size());
    }
    @Test
    public void testAddStudent_InvalidFirstName_ExceptionThrown() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                studentRepository.addStudent("", "Doe", "2000-01-01", "Male", "1234567890"));
        Assertions.assertEquals(0, studentRepository.listStudentsByLastName().size());
    }
    @Test
    public void testDeleteStudent_ExistingStudent_Success() {
        studentRepository.addStudent("John", "Doe", "2000-01-01", "Male", "1234567890");

        Assertions.assertDoesNotThrow(() ->
                studentRepository.deleteStudent("1234567890"));
        Assertions.assertEquals(0, studentRepository.listStudentsByLastName().size());
    }
    @Test
    public void testDeleteStudent_NonExistingStudent_ExceptionThrown() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                studentRepository.deleteStudent("1234567890"));
    }
    @Test
    public void testListStudentsByBirthDate_ValidData_Success() {
        studentRepository.addStudent("John", "Doe", "2000-01-01", "Male", "1234567890");
        studentRepository.addStudent("Alice", "Johnson", "2002-09-15", "Female", "9876543210");

        List<StudentRepository.Student> students = studentRepository.listStudentsByBirthDate();

        Assertions.assertEquals(2, students.size());
        Assertions.assertEquals("2000-01-01", students.get(0).getDateOfBirth());
        Assertions.assertEquals("2002-09-15", students.get(1).getDateOfBirth());
    }
    @Test
    public void testRetrieveStudentsByAge_NegativeAge_ExceptionThrown() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                studentRepository.retrieveStudentsByAge(-1));
    }
    @Test
    public void testListStudentsByLastName_ValidData_Success() {
        studentRepository.addStudent("Jane", "Smith", "1998-05-10", "Female", "0987654321");
        studentRepository.addStudent("John", "Doe", "2000-01-01", "Male", "1234567890");
        studentRepository.addStudent("Alice", "Johnson", "2002-09-15", "Female", "9876543210");

        List<StudentRepository.Student> students = studentRepository.listStudentsByLastName();

        Assertions.assertEquals(3, students.size());
        Assertions.assertEquals("Doe", students.get(0).getLastName());
        Assertions.assertEquals("Johnson", students.get(1).getLastName());
        Assertions.assertEquals("Smith", students.get(2).getLastName());
    }
}
