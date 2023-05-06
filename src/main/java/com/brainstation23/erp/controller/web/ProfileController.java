package com.brainstation23.erp.controller.web;

import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.request.CreateUserRequest;
import com.brainstation23.erp.model.dto.request.UpdateUserRequest;
import com.brainstation23.erp.model.dto.request.UpdateUserRequestFromProfile;
import com.brainstation23.erp.model.dto.response.ResponseMessage;
import com.brainstation23.erp.persistence.entity.RoleEntity;
import com.brainstation23.erp.service.RoleService;
import com.brainstation23.erp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public ModelAndView viewCreateUserPage(Principal principal) {
        var modelAndView = new ModelAndView("profile/view");
        modelAndView.addObject("pageTitle", "Profile");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        User user = userService.getUserByUsername(principal.getName());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UpdateUserRequestFromProfile request,
                             @PathVariable UUID id, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, Model model, Principal principal) {
        try {
            if (bindingResult.hasFieldErrors("id") || bindingResult.hasFieldErrors("firstName") || bindingResult.hasFieldErrors("lastName")) {
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("user", request);
                return "profile/view";
            }
            userService.updateUserName(request, id);
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger",   e.getMessage()));
            return "redirect:/profile/" + id + "/update";
        }
    }


//    @PostMapping("/save")
//    public String createUser(@Valid @ModelAttribute("user") CreateUserRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//        try {
//            if (bindingResult.hasErrors()) {
//                model.addAttribute("pageTitle", "Add User");
//                model.addAttribute("user", request);
//                model.addAttribute("roles", roleService.getRoles(Pageable.unpaged()));
//                return "register/new-user";
//            }
//
//            List<RoleEntity> roles = roleService.getRolesS();
//            Set<RoleEntity> userRoles = new HashSet<>();
//            for (RoleEntity role : roles) {
//                if (role.getName().equals("Employee")) {
//                    userRoles.add(role);
//                }
//            }
//            request.setRoles(userRoles);
//            request.setEnable(true);
//            userService.createUser(request);
//            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-success", "Registration Success"));
//            return "redirect:/register/create";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger", e.getMessage()));
//            return "redirect:/register/create";
//        }
//    }


}
