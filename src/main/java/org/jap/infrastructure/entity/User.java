package org.jap.infrastructure.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    private String ageRange;
    private String password;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(ageRange, user.ageRange) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, ageRange, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", ageRange='" + ageRange + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
