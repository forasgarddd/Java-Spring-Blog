package com.springapp.springapp.repos;

import com.springapp.springapp.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
