package com.springboot.hello_spring_boot.controller.admin;

import com.springboot.hello_spring_boot.domain.Product;
import com.springboot.hello_spring_boot.domain.User;
import com.springboot.hello_spring_boot.service.UploadService;
import com.springboot.hello_spring_boot.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          UploadService uploadService,
                          PasswordEncoder passwordEncoder
    ){
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

//    @RequestMapping("/")
//    public String getHomePage(Model model){
//        List<User> arrUsers = this.userService.getAllUserByEmail("taidoleviet12344321@gmail.com");
//        System.out.println(arrUsers);
//        model.addAttribute("eric" , "test");
//        model.addAttribute("doleviettai" , "Xin chào anh dev Tài");
//        return "hello";
//    }
    @RequestMapping("/admin/user")
    public String getUserPage(Model model , @RequestParam("page") Optional<String> pageOptional){
        int page = 1;
        try{
            if(pageOptional.isPresent()){
                page = Integer.parseInt(pageOptional.get());
            }
        }catch (Exception e){

        }
        Pageable pageable= PageRequest.of(page - 1 , 10);
        Page<User> user = this.userService.fetchUsers(pageable);
        List<User> users = user.getContent();
        model.addAttribute("users1" , users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", user.getTotalPages());
        return "admin/user/show";
    }

    @GetMapping(value = "/admin/user/create")
    public String getCreateUserPage(Model model){
        model.addAttribute("newUser" , new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserpage(Model model,
                                 @ModelAttribute("newUser") @Valid User hoidanit,
                                 BindingResult newUserBindingResult,
                                 @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors){
            System.out.println(">>>>>>>>>>>>>"+error.getField()+" - "+error.getDefaultMessage());
        }

        if(newUserBindingResult.hasErrors()){
            return "admin/user/create";
        }

        String avatar = this.uploadService.handleSaveUploadFile(file , "avatar");
        String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());

        hoidanit.setAvatar(avatar);
        hoidanit.setPassword(hashPassword);
        hoidanit.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));

        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

    @GetMapping(value = "admin/user/{id}")
    public String getUserDetailPage(@PathVariable long id , Model model){
        User user = this.userService.getUserById(id);
        model.addAttribute("id" , id);
        model.addAttribute("user" , user);
        return "admin/user/detail";
    }

    @RequestMapping(value = "/admin/user/update/{id}" , method = RequestMethod.GET)
    public String getUpdateUserPage(Model model , @PathVariable long id){
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser" , currentUser);
        return "/admin/user/update";
    }

    @PostMapping(value = "/admin/user/update")
    public String postUpdateUser(Model model,
                                 @ModelAttribute("newUser") User hoidanit,
                                 @RequestParam("hoidanitFile") MultipartFile file
                                 ){
        User currentUser = this.userService.getUserById(hoidanit.getId());
        String avatar = this.uploadService.handleSaveUploadFile(file , "avatar");
        if(currentUser != null){
            currentUser.setPhone(hoidanit.getPhone());
            currentUser.setFullname(hoidanit.getFullname());
            currentUser.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));
            currentUser.setAddress(hoidanit.getAddress());
            currentUser.setAvatar(avatar);
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model , @PathVariable long id){
        model.addAttribute("id" , id);
        model.addAttribute("newUser" , new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model , @ModelAttribute("newUser") User eric){
        this.userService.deleteUser(eric.getId());
        return "redirect:/admin/user";
    }

}
