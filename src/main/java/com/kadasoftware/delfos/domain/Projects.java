package com.kadasoftware.delfos.domain;

import com.kadasoftware.delfos.service.dto.UserForProject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import com.kadasoftware.delfos.domain.enumeration.ProjectStatus;

/**
 * A Projects.
 */

@Document(collection = "projects")
public class Projects implements Serializable {

    private static final long serialVersionUID = 5707378224186481735L;

    @Id
    private String id;

    @NotNull
    @Size(max = 200)
    @Field("name")
    private String name;

    @NotNull
    @Field("start_date")
    private LocalDate startDate;

    @NotNull
    @Field("end_date")
    private LocalDate endDate;

    @NotNull
    @Field("status")
    private ProjectStatus status;

    @Field("users")
    private Set<UserForProject> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Projects name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Projects startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Projects endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Projects status(ProjectStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Set<UserForProject> getUsers() {
        return users;
    }

    public void setUsers(Set<UserForProject> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Projects other = (Projects) obj;
        return Objects.equals(this.id, other.id)
            && Objects.equals(this.name, other.name)
            && Objects.equals(this.startDate, other.startDate)
            && Objects.equals(this.endDate, other.endDate)
            && Objects.equals(this.status, other.status);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("name = " + name)
            .add("startDate = " + startDate)
            .add("endDate = " + endDate)
            .add("status = " + status).toString();
    }
}
