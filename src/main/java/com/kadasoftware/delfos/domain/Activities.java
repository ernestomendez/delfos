package com.kadasoftware.delfos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.kadasoftware.delfos.domain.enumeration.ActivityStatus;

import com.kadasoftware.delfos.domain.enumeration.ActivityType;

/**
 * A Activities.
 */

@Document(collection = "activities")
public class Activities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 400)
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("acceptance_criteria")
    private String acceptanceCriteria;

    @Field("assigned_to")
    private String assignedTo;

    @NotNull
    @Field("project")
    private String project;

    @Field("story_points")
    private Integer storyPoints;

    @NotNull
    @Field("sprint_week")
    private String sprintWeek;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @NotNull
    @Field("estimated_time")
    private Integer estimatedTime;

    @Field("woking_time")
    private Float wokingTime;

    @Field("remaining_time")
    private Float remainingTime;

    @Field("start_date")
    private LocalDate startDate;

    @Field("end_date")
    private LocalDate endDate;

    @Field("calculated_end_date")
    private LocalDate calculatedEndDate;

    @NotNull
    @Field("status")
    private ActivityStatus status;

    @NotNull
    @Field("type")
    private ActivityType type;

    @Field("notes")
    private String notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Activities name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Activities description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public Activities acceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
        return this;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public Activities assignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getProject() {
        return project;
    }

    public Activities project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public Activities storyPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
        return this;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public String getSprintWeek() {
        return sprintWeek;
    }

    public Activities sprint_week(String sprint_week) {
        this.sprintWeek = sprint_week;
        return this;
    }

    public void setSprintWeek(String sprintWeek) {
        this.sprintWeek = sprintWeek;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Activities creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public Activities estimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Float getWokingTime() {
        return wokingTime;
    }

    public Activities wokingTime(Float wokingTime) {
        this.wokingTime = wokingTime;
        return this;
    }

    public void setWokingTime(Float wokingTime) {
        this.wokingTime = wokingTime;
    }

    public Float getRemainingTime() {
        return remainingTime;
    }

    public Activities remainingTime(Float remainingTime) {
        this.remainingTime = remainingTime;
        return this;
    }

    public void setRemainingTime(Float remainingTime) {
        this.remainingTime = remainingTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Activities startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Activities endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCalculatedEndDate() {
        return calculatedEndDate;
    }

    public Activities calculatedEndDate(LocalDate calculatedEndDate) {
        this.calculatedEndDate = calculatedEndDate;
        return this;
    }

    public void setCalculatedEndDate(LocalDate calculatedEndDate) {
        this.calculatedEndDate = calculatedEndDate;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public Activities status(ActivityStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public ActivityType getType() {
        return type;
    }

    public Activities type(ActivityType type) {
        this.type = type;
        return this;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public Activities notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activities activities = (Activities) o;
        if(activities.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, activities.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Activities{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", acceptanceCriteria='" + acceptanceCriteria + "'" +
            ", assignedTo='" + assignedTo + "'" +
            ", project='" + project + "'" +
            ", storyPoints='" + storyPoints + "'" +
            ", sprintWeek='" + sprintWeek + "'" +
            ", creationDate='" + creationDate + "'" +
            ", estimatedTime='" + estimatedTime + "'" +
            ", wokingTime='" + wokingTime + "'" +
            ", remainingTime='" + remainingTime + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", calculatedEndDate='" + calculatedEndDate + "'" +
            ", status='" + status + "'" +
            ", type='" + type + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
