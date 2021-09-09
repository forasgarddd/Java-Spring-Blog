package com.springapp.springapp.repos;

import com.springapp.springapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

}
