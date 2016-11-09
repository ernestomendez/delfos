package com.kadasoftware.delfos.service.dto;

import com.kadasoftware.delfos.domain.Activities;
import com.kadasoftware.delfos.domain.Task;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ernesto on 8/11/16.
 */
public class ActivitiesDTO implements Serializable {

    private Activities activity;

    private List<Task> newTasks = new ArrayList<>();
    private List<Task> workingTasks = new ArrayList<>();
    private List<Task> doneTasks = new ArrayList<>();

    public Activities getActivity() {
        return activity;
    }

    public void setActivity(Activities activity) {
        this.activity = activity;
    }

    public List<Task> getNewTasks() {
        return newTasks;
    }

    public void setNewTasks(List<Task> newTasks) {
        this.newTasks = newTasks;
    }

    public List<Task> getWorkingTasks() {
        return workingTasks;
    }

    public void setWorkingTasks(List<Task> workingTasks) {
        this.workingTasks = workingTasks;
    }

    public List<Task> getDoneTasks() {
        return doneTasks;
    }

    public void setDoneTasks(List<Task> doneTasks) {
        this.doneTasks = doneTasks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivitiesDTO that = (ActivitiesDTO) o;

        return Objects.equals(this.activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("activity", activity)
            .toString();
    }
}
