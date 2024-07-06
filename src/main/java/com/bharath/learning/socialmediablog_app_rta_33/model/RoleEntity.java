package com.bharath.learning.socialmediablog_app_rta_33.model;

import jakarta.persistence.*;
import lombok.Data;


@Data

@Entity
@Table(name = "roles" )
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
}
