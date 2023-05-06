package com.brainstation23.erp.controller.web;

import com.brainstation23.erp.model.dto.request.CreateUserRequest;
import com.brainstation23.erp.model.dto.response.ResponseMessage;
import com.brainstation23.erp.persistence.entity.RoleEntity;
import com.brainstation23.erp.service.RoleService;
import com.brainstation23.erp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return modelAndView;
    }

    @PostMapping("/save")
    public String createUser(@Valid @ModelAttribute("user") CreateUserRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Add User");
                model.addAttribute("user", request);
                model.addAttribute("roles", roleService.getRoles(Pageable.unpaged()));
                return "register/new-user";
            }

            List<RoleEntity> roles = roleService.getRolesS();
            Set<RoleEntity> userRoles = new HashSet<>();
            for (RoleEntity role : roles) {
                if (role.getName().equals("Employee")) {
                    userRoles.add(role);
                }
            }
            request.setRoles(userRoles);
            request.setEnable(true);
            userService.createUser(request);
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-success", "Registration Success"));
            return "redirect:/register/create";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger", e.getMessage()));
            return "redirect:/register/create";
        }
    }


}
