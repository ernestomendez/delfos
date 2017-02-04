package com.kadasoftware.delfos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Document(collection = "time_sheet")
public class TimeSheet implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("sprint")
    private String sprintName;

    @NotNull
    @Field("story")
    private String story;

    @NotNull
    @Field("task")
    private String task;

    @NotNull
    @Field("task_id")
    private String taskId;

    @NotNull
    @Field("time_records")
    private Set<TimeRecord> timeRecords;


}
