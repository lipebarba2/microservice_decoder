package com.ead.course.dtos;


import com.ead.course.enums.CourseStatus;
import com.ead.course.models.CourseLevel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private UUID userInstructor;

    @NotNull
    private CourseLevel courseLevel;

}
