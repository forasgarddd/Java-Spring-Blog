package com.springapp.springapp.controllers;

import com.springapp.springapp.models.Post;
import com.springapp.springapp.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/blog/{id}")
    public String postDetails(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "post-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String postEdit(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "post-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogUpdatePost(@PathVariable(value = "id") Long id, @RequestParam String title,
                              @RequestParam String announcement,
                              @RequestParam String fullText, Model model) {
        Optional<Post> postOpt = postRepository.findById(id);
        Post post = postOpt.get();
        post.setTitle(title);
        post.setAnnouncement(announcement);
        post.setFullText(fullText);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogDeletePost(@PathVariable(value = "id") Long id) {
        Optional<Post> postOpt = postRepository.findById(id);
        Post post = postOpt.get();
        postRepository.delete(post);

        return "redirect:/blog";
    }


}
