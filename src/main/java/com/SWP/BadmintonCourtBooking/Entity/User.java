package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(name = "Username", nullable = false)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Phone", nullable = false)
    private String phone;

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
