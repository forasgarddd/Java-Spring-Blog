package com.springapp.springapp.controllers;

import com.springapp.springapp.models.Masonry;
import com.springapp.springapp.models.Post;
import com.springapp.springapp.models.User;
import com.springapp.springapp.repos.MasonryRepository;
import com.springapp.springapp.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MasonryRepository masonryRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";

    }

    @GetMapping("/weblab")
    public String weblab(Model model) {
        Iterable<Masonry> masonries = masonryRepository.findAll();
        model.addAttribute("masonries", masonries);
        return "weblab";

    }

    @GetMapping("/weblab1")
    public String weblab1(Model model) {
        Iterable<Masonry> masonries = masonryRepository.findAll();
        model.addAttribute("masonries", masonries);
        return "weblab1";

    }

    @GetMapping("/weblab/add")
    public String addMasonry(Model model) {
        return "masonry-add";
    }

    @PostMapping("weblab/add")
    public String saveMasonry(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        Masonry masonry = new Masonry();
        if(file != null) {

            File uploadFolder = new File(uploadPath);
            if(!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            masonry.setFileName(resultFilename);
        }
        masonryRepository.save(masonry);
        return "redirect:/weblab";
    }

    @PostMapping("/weblab/{id}/remove")
    public String deleteMasonry(@PathVariable(value = "id") Long id) {
        Optional<Masonry> masonryOpt = masonryRepository.findById(id);
        Masonry masonry = masonryOpt.get();
        masonryRepository.delete(masonry);

        return "redirect:/weblab";
    }

    @GetMapping("/weblab/{id}/edit")
    public String masonryEdit(@PathVariable(value = "id") Long id, Model model) {
        if (!masonryRepository.existsById(id)){
            return "redirect:/weblab";
        }
        Optional<Masonry> masonry = masonryRepository.findById(id);
        ArrayList<Masonry> result = new ArrayList<>();
        masonry.ifPresent(result::add);
        model.addAttribute("masonry", result);
        return "masonry-edit";
    }

    @PostMapping("/weblab/{id}/edit")
    public String masonryUpdatePost(@PathVariable(value = "id") Long id, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        Optional<Masonry> masonryOpt = masonryRepository.findById(id);
        Masonry masonry = masonryOpt.get();

        if(file != null) {

            File uploadFolder = new File(uploadPath);
            if(!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            masonry.setFileName(resultFilename);
        }
        masonryRepository.save(masonry);
        return "redirect:/weblab";
    }

    @GetMapping("/blog/add")
    public String addPost(Model model) {
        return "post-add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@AuthenticationPrincipal User author, @RequestParam String title,
                              @RequestParam String announcement,
                              @RequestParam String fullText,
                              @RequestParam("file") MultipartFile file, Model model) throws IOException {
        Post post = new Post(title, announcement, fullText, author);

        if(file != null) {

            File uploadFolder = new File(uploadPath);
            if(!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            post.setFilename(resultFilename);
        }
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
    @PreAuthorize("#authorName == authentication.getName()")
    public String blogUpdatePost(@PathVariable(value = "id") Long id, @RequestParam String title,
                                 @RequestParam String announcement,
                                 @RequestParam String fullText, @RequestParam String authorName, @CurrentSecurityContext(expression="authentication")
                                         Authentication authentication, Model model) {
        Optional<Post> postOpt = postRepository.findById(id);
        Post post = postOpt.get();
        User author = (User) authentication.getPrincipal();
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
