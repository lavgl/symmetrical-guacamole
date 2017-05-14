package com.kpi.lab1.dto;

import com.kpi.lab1.dao.Identified;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto implements Identified<Integer> {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
}
