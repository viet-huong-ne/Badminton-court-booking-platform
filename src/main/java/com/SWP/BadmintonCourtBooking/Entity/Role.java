package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Entity
@Table(name = "Role")
@Data
public class Role {
    @Id
    @Column(name = "RoleID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleID;

    @Column(name = "Role_Name")
    private String roleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> user;
}
