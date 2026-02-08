package com.wego.seolstudybe.mentoring.dao;

import com.wego.seolstudybe.mentoring.dto.WorksheetFileResponse;
import com.wego.seolstudybe.mentoring.entity.enums.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorksheetFileMapper {
    List<WorksheetFileResponse> findByMenteeIdAndKeywordAndSubject(@Param("menteeId") final int menteeId,
                                                                   @Param("keyword") final String keyword,
                                                                   @Param("subject") final Subject subject);
}