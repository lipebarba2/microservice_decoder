package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonModel, UUID> {
    @Query(value = "SELECT * FROM tb_lessons WHERE module_id = :moduleId", nativeQuery = true)
    List<LessonModel> findAllLessonsIntoModule(UUID moduleId); // <-- Nome incorreto
}
