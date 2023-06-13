package org.example;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
class StudentRepository {
    private final List<Student> students;
    public StudentRepository() {
        students = new ArrayList<>();
    }
    public void addStudent(String firstName, String lastName, String dateOfBirth, String gender, String id)
            throws IllegalArgumentException {
        validateFirstName(firstName);
        validateLastName(lastName);
        validateDateOfBirth(dateOfBirth);
        validateGender(gender);
        validateId(id);

        students.add(new Student(firstName, lastName, dateOfBirth, gender, id));
    }
    public void deleteStudent(String id) throws IllegalArgumentException {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("ID is empty");
        }
        boolean removed = students.removeIf(student -> student.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Student does not exist");
        }
    }
    public List<Student> retrieveStudentsByAge(int age) throws IllegalArgumentException {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            int studentAge = calculateAge(student.getDateOfBirth());
            if (studentAge == age) {
                result.add(student);
            }
        }
        return result;
    }
    public List<Student> listStudentsByLastName() {
        students.sort(Comparator.comparing(Student::getLastName));
        return students;
    }

    public List<Student> listStudentsByBirthDate() {
        students.sort(Comparator.comparing(Student::getDateOfBirth));
        return students;
    }

    private int calculateAge(String dateOfBirth) {
        LocalDate birthDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    private void validateFirstName(String firstName) throws IllegalArgumentException {
        if (firstName.isEmpty()) {
            throw new IllegalArgumentException("First name should not be empty");
        }
    }
    private void validateLastName(String lastName) throws IllegalArgumentException {
        if (lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name should not be empty");
        }
    }

    private void validateDateOfBirth(String dateOfBirth) throws IllegalArgumentException {
        try {
            LocalDate birthDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int currentYear = LocalDate.now().getYear();

            if (birthDate.getYear() < 1900 || birthDate.getYear() > currentYear - 18) {
                throw new IllegalArgumentException("Invalid date of birth");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }
    private void validateGender(String gender) throws IllegalArgumentException {
        if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")
                && !gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")) {
            throw new IllegalArgumentException("Invalid gender");
        }
    }
    private void validateId(String id) throws IllegalArgumentException {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("ID is empty");
        }
    }
    static record Student(String firstName, String lastName, String dateOfBirth, String gender, String id) {
        public String getDateOfBirth() {
            return dateOfBirth;
        }
        public String getId() {
            return id;
        }
        public String getLastName() {
            return lastName;
        }
    }
}
