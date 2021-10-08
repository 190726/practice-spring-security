package com.sk.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sk.comm.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "T_USER")
@SequenceGenerator(name="USER_SEQ_GEN",
        sequenceName = "SEQ_T_USER",
        initialValue = 1)
public class User extends BaseTimeEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "USER_SEQ_GEN")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String userId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 30)
    private String auth;
    
    @Column(nullable = false, length = 1)
    private String enabled;

    @Builder
    public User(String userId, String name, String password, String auth, String enabled) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.auth = auth;
        this.enabled = enabled;
    }
    
}
