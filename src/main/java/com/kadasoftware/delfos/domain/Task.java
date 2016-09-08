package com.kadasoftware.delfos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Task.
 */

@Document(collection = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 400)
    @Field("name")
    private String name;

    @NotNull
    @Field("phase")
    private String phase;

    @Field("assigned_to")
    private String assignedTo;

    @Size(max = 3000)
    @Field("notes")
    private String notes;

    @NotNull
    @Field("status")
    private String status;

    @Field("start_date")
    private LocalDate startDate;

    @Field("end_date")
    private LocalDate endDate;

    @NotNull
    @Field("estimated_time")
    private Float estimatedTime;

    @Field("calculated_end_date")
    private LocalDate calculatedEndDate;

    @NotNull
    @Field("activity")
    private String activity;

    @NotNull
    @Field("cration_date")
    private LocalDate crationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhase() {
        return phase;
    }

    public Task phase(String phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public Task assignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getNotes() {
        return notes;
    }

    public Task notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public Task status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Task startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Task endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Float getEstimatedTime() {
        return estimatedTime;
    }

    public Task estimatedTime(Float estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    public void setEstimatedTime(Float estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public LocalDate getCalculatedEndDate() {
        return calculatedEndDate;
    }

    public Task calculatedEndDate(LocalDate calculatedEndDate) {
        this.calculatedEndDate = calculatedEndDate;
        return this;
    }

    public void setCalculatedEndDate(LocalDate calculatedEndDate) {
        this.calculatedEndDate = calculatedEndDate;
    }

    public String getActivity() {
        return activity;
    }

    public Task activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDate getCrationDate() {
        return crationDate;
    }

    public Task crationDate(LocalDate crationDate) {
        this.crationDate = crationDate;
        return this;
    }

    public void setCrationDate(LocalDate crationDate) {
        this.crationDate = crationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if(task.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", phase='" + phase + "'" +
            ", assignedTo='" + assignedTo + "'" +
            ", notes='" + notes + "'" +
            ", status='" + status + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", estimatedTime='" + estimatedTime + "'" +
            ", calculatedEndDate='" + calculatedEndDate + "'" +
            ", activity='" + activity + "'" +
            ", crationDate='" + crationDate + "'" +
            '}';
    }
}
