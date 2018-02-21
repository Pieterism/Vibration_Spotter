package Vibrationspotter;

import model.Student;

public interface StudentManagementEJBLocal {
	public Student findFoto(int idStudent);

	void addStudent(Student student);


}
