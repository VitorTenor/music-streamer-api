package com.music.musicStreamer.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
@SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    private int id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 500)
    private String password;
    @Column(nullable = false, length = 100)
    private Date created_at;
    @Column(nullable = false, length = 100)
    private Date updated_at;
}

