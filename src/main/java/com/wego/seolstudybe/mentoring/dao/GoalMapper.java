package com.wego.seolstudybe.mentoring.dao;

import com.wego.seolstudybe.mentoring.dto.GoalResponse;
import com.wego.seolstudybe.mentoring.entity.enums.GoalCreator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoalMapper {
    List<GoalResponse> findGoalsByCreatedBy(@Param("menteeId") final int menteeId,
                                            @Param("createdBy") final GoalCreator createdBy);
}