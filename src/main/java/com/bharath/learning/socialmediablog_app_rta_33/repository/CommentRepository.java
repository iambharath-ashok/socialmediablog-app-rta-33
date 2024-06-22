package com.bharath.learning.socialmediablog_app_rta_33.repository;

import com.bharath.learning.socialmediablog_app_rta_33.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
