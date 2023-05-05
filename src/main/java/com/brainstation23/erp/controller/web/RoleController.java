package com.brainstation23.erp.controller.web;

import com.brainstation23.erp.model.domain.Role;
import com.brainstation23.erp.model.dto.request.CreateRoleRequest;
import com.brainstation23.erp.model.dto.request.UpdateRoleRequest;
import com.brainstation23.erp.service.RoleService;
import com.brainstation23.erp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @GetMapping
    public ModelAndView getRoles(@RequestParam(defaultValue = "0") int page, Principal principal) {
        var modelAndView = new ModelAndView("role/list");
        Page<Role> roles = roleService.getRoles(PageRequest.of(page, DEFAULT_PAGE_SIZE));
        modelAndView.addObject("pageTitle", "Role List");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("pagesForPagination", roles);
        modelAndView.addObject("url", "/roles");
        return modelAndView;
    }


    @GetMapping("/{id}/")
    public ModelAndView getRole(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("role/single");
        Role role = roleService.getRole(id);
        modelAndView.addObject("pageTitle", "Role Details");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createRolePage(Principal principal) {
        var modelAndView = new ModelAndView("role/new-role");
        var createRoleRequest = new CreateRoleRequest();
        modelAndView.addObject("pageTitle", "Add Role");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("role", createRoleRequest);
        return modelAndView;
    }

    @PostMapping
    public String createRole(@Valid @ModelAttribute("role") CreateRoleRequest request, BindingResult bindingResult, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Add Role");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("role", request);
                return "role/new-role";
            }
            roleService.createRole(request);
            return "redirect:/roles";
        } catch (Exception e) {
            return "redirect:/roles/create";
        }
    }

    @GetMapping("/{id}/update")
    public ModelAndView updateRolePage(@PathVariable UUID id, Principal principal) {
        System.out.println(id);
        var modelAndView = new ModelAndView("role/update-role");
        var role = roleService.getRole(id);
        modelAndView.addObject("pageTitle", "Update Role");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String updateRole(@Valid @ModelAttribute("role") UpdateRoleRequest request,@PathVariable UUID id, BindingResult bindingResult, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Update Role");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("role", request);
                return "role/update-role";
            }
            roleService.updateRole(request, id);
            return "redirect:/roles";
        } catch (Exception e) {
            return "redirect:/roles";
        }
    }

    @GetMapping("{id}/delete")
    public String deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return "redirect:/roles";
    }

}
