package com.sing3demons.springbootapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "`user`")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false, length = 120)
    @JsonIgnore
    private String password;
    
    @Column(nullable = false, length = 120)
    private String name;

}
