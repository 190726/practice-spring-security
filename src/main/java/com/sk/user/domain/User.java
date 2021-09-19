package com.sk.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "MY_USER")
@SequenceGenerator(name="USER_SEQ_GEN",
        sequenceName = "SEQ_MY_USER",
        initialValue = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "USER_SEQ_GEN")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String userId;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 30)
    private String password;

    @Builder
    public User(String userId, String name, String password){
        this.userId= userId;
        this.name = name;
        this.password=password;
    }

}
