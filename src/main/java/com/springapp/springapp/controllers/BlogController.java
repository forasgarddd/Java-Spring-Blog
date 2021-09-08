package com.springapp.springapp.controllers;

import com.springapp.springapp.models.Post;
import com.springapp.springapp.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";

    }

    @GetMapping("/blog/add")
    public String addPost(Model model) {
        return "post-add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String title,
                              @RequestParam String announcement,
                              @RequestParam String fullText, Model model) {
        Post post = new Post(title, announcement, fullText);
        postRepository.save(post);
        return "redirect:/blog";
    }
}
