package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tự tạo ra chuỗi
    private Integer id;

    @Column(name = "User_Name", nullable = false)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name" )
    private String lastName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Phone", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "Role_ID", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> booking;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RecurringBooking> recurringBooking;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Court> court;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Payment> payment;

}
