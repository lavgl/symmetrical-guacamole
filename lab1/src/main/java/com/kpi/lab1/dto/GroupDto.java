package com.kpi.lab1.dto;

import com.kpi.lab1.dao.Identified;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto implements Identified<Integer> {
    private Integer id;
    private String name;
}
