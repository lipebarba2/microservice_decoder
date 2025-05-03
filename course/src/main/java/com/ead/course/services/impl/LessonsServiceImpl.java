package com.ead.course.services.impl;

import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import org.springframework.stereotype.Service;

@Service
public class LessonsServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    public LessonsServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

}
