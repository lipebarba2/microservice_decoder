package com.ead.course.services;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LessonService {


    List<ModuleModel> findAllLessonIntoModules(@Param("moduleId") UUID moduleId);
}
