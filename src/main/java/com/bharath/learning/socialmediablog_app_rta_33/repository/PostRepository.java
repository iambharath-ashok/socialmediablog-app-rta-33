package com.bharath.learning.socialmediablog_app_rta_33.repository;

import com.bharath.learning.socialmediablog_app_rta_33.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
