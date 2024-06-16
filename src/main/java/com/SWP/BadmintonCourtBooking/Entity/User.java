package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Fullname", nullable = false, columnDefinition = "nvarchar(255)")
    private String fullName;

    public User(String email, String phone, String username, String password, String fullName, Role role) {
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "RoleID", nullable = false)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> booking;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Court> court;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payment;
}
