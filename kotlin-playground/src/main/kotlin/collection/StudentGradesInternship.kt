package collection

import jdk.internal.org.jline.utils.Colors.s
import java.util.Collections

fun List<StudentGrades>.getBestForScholarship(
    semester: String
): List<StudentGrades> = this
    .filter { s ->
        s.grades
            .filter { it.semester == semester && it.passing }
            .sumOf { it.ects } > 30
    }

private fun averageGradeFromSemester(
    student: StudentGrades,
    semester: String
): Double = student.grades
    .filter { it.semester == semester }
    .map { it.grade }
    .average()

data class Grade(
    val passing: Boolean,
    var ects: Int,
    var semester: String,
    var grade: Double
)

data class StudentGrades(
    val studentId: String,
    val grades: List<Grade>
)
