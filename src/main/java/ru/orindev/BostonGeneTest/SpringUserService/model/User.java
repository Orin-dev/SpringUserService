package ru.orindev.BostonGeneTest.SpringUserService.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table
public class User {

    @Id
    @NotNull
    private Long id;

    @Column
    @Size(min = 3, max = 20)
    @NotNull
    private String name;

    @Column
    @Size(min = 3, max = 20)
    @NotNull
    private String secondName;

    @Column
    @NotNull
    @Type(type="date")
    private Date birthDate;

    @Column(length = 64)
    @NotNull
    private String email;

    @Column(length = 256)
    @NotNull
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
