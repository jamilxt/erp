package com.brainstation23.erp.controller.web;

import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.request.CreateUserRequest;
import com.brainstation23.erp.model.dto.request.UpdateUserRequest;
import com.brainstation23.erp.model.dto.response.ResponseMessage;
import com.brainstation23.erp.service.RoleService;
import com.brainstation23.erp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;


@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public ModelAndView getUsers(@RequestParam(defaultValue = "0") int page, Principal principal) {
        var modelAndView = new ModelAndView("user/list");
        Page<User> users = userService.getUsers(PageRequest.of(page, DEFAULT_PAGE_SIZE));
        modelAndView.addObject("pageTitle", "View Users");
//        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("users", users);
        modelAndView.addObject("pagesForPagination", users);
        modelAndView.addObject("url", "/users");
        return modelAndView;
    }

    @GetMapping("/{id}/")
    public ModelAndView getUser(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("user/single");
        User user = userService.getUserById(id);
        modelAndView.addObject("pageTitle", "User Profile");
//        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView viewCreateUserPage(Principal principal) {
        var modelAndView = new ModelAndView("user/new-user");
        var createUserRequest = new CreateUserRequest();
        modelAndView.addObject("pageTitle", "Add User");
//        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("user", createUserRequest);
        modelAndView.addObject("roles", roleService.getRoles(Pageable.unpaged()));
        return modelAndView;
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") CreateUserRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        System.out.println(request.getPassword());
        System.out.println(request.getEmail());
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Add User");
//                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("user", request);
                model.addAttribute("roles", roleService.getRoles(Pageable.unpaged()));
                return "user/new-user";
            }

            if (request.getRoles().isEmpty()) {
                throw new Exception("Please select a role and submit again.");
            }

            request.setEnable(true);
            userService.createUser(request);
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger", e.getMessage()));
            return "redirect:/users/create";
        }
    }

    @GetMapping("/{id}/update")
    public ModelAndView viewUpdateUserPage(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("user/update-user");
        User user = userService.getUserById(id);
        modelAndView.addObject("pageTitle", "Update User");
//        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", roleService.getRoles(Pageable.unpaged()));
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UpdateUserRequest request, @PathVariable UUID id, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        try {
            if (bindingResult.hasFieldErrors("id") || bindingResult.hasFieldErrors("firstName") || bindingResult.hasFieldErrors("lastName") || bindingResult.hasFieldErrors("email")) {
                model.addAttribute("pageTitle", "Update User");
//                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("user", request);
                model.addAttribute("roles", roleService.getRoles(Pageable.unpaged()));
                return "user/update-user";
            }

            if (request.getRoles().isEmpty()) {
                throw new Exception("Please select a role and submit again.");
            }

            userService.updateUser(request, id);
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger",   e.getMessage()));
            return "redirect:/users/" + id + "/update";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
