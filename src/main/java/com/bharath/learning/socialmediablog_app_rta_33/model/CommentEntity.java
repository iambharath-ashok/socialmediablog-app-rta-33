package com.bharath.learning.socialmediablog_app_rta_33.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String email;
    private String body;

    //Mutliple Comments Can belong to Single Post
    //Mapping b/w Comments and Post Entity/ or Tables
    // Comments table is managing the relationship b/w Posts and Comments Table
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

}
