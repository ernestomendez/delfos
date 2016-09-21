package com.kadasoftware.delfos.service.dto;

import com.kadasoftware.delfos.domain.User;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Created by ernesto on 14/09/16.
 */
public class UserForProject {

    private String id = null;

    private String login = null;

    private String firstName = null;

    private String lastName = null;

    public UserForProject() {
        this.id = null;
    }

    public UserForProject(String id, String login, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserForProject(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, firstName, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserForProject other = (UserForProject) obj;
        return Objects.equals(this.id, other.id)
            && Objects.equals(this.login, other.login)
            && Objects.equals(this.firstName, other.firstName)
            && Objects.equals(this.lastName, other.lastName);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("login = " + login)
            .add("firstName = " + firstName)
            .add("lastName = " + lastName).toString();
    }
}
