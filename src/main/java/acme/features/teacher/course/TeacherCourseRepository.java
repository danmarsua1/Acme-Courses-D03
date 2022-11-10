/*
 * TeacherCourseRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.course;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.LabTutorial;
import acme.entities.TheoryTutorial;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Teacher;

@Repository
public interface TeacherCourseRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select t from Teacher t where t.userAccount.id = :id")
	Teacher findOneTeacherById(int id);

	@Query("select c from Course c where c.teacher.id = :id")
	Collection<Course> findManyCoursesByTeacher(int id);
	
	@Query("select c from Course c")
	Collection<Course> findAllCourses();
	
	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);
	
	@Query("select t.cost.amount, t.cost.currency from Course c, Register r, TheoryTutorial t where c.id = r.course.id and r.theoryTutorial.id = t.id and c.id = :id")
	List<Object[]> getCourseTheoryTutorialsPrice(int id);

	@Query("select l.cost.amount, l.cost.currency from Course c, Register r, LabTutorial l where c.id = r.course.id and r.labTutorial.id = l.id and c.id = :id")
	List<Object[]> getCourseLabTutorialsPrice(int id);
	
	@Query("select distinct t from Course c, Register r, TheoryTutorial t where c.id = r.course.id and r.theoryTutorial.id = t.id and c.id = :id")
	Collection<TheoryTutorial> findManyTheoryTutorialsByCourseId(int id);
	
	@Query("select distinct l from Course c, Register r, LabTutorial l where c.id = r.course.id and r.labTutorial.id = l.id and c.id = :id")
	Collection<LabTutorial> findManyLabTutorialsByCourseId(int id);

	@Query("select c.teacher.id from Course c where c.id = :id")
	Integer findTeacherByCourseId(int id);
	
}
