package com.brainstation23.erp.controller.web;

import com.brainstation23.erp.model.domain.Organization;
import com.brainstation23.erp.model.domain.Role;
import com.brainstation23.erp.model.dto.request.CreateOrganizationRequest;
import com.brainstation23.erp.model.dto.request.CreateRoleRequest;
import com.brainstation23.erp.model.dto.request.UpdateOrganizationRequest;
import com.brainstation23.erp.model.dto.request.UpdateRoleRequest;
import com.brainstation23.erp.service.OrganizationService;
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
@RequestMapping(value = "organizations")
public class OrganizationController {
    private final RoleService roleService;
    private final UserService userService;

    private final OrganizationService organizationService;

    @GetMapping
    public ModelAndView getOrganization(@RequestParam(defaultValue = "0") int page, Principal principal) {
        var modelAndView = new ModelAndView("organization/list");
        Page<Organization> organizations = organizationService.getAll(PageRequest.of(page, DEFAULT_PAGE_SIZE));
        modelAndView.addObject("pageTitle", "Organization List");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("organization", organizations);
        modelAndView.addObject("pagesForPagination", organizations);
        modelAndView.addObject("url", "/organizations");
        return modelAndView;
    }


    @GetMapping("/{id}/")
    public ModelAndView getOrganization(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("organization/single");
        Organization organization = organizationService.getOne(id);
        modelAndView.addObject("pageTitle", "Organization Details");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("organization", organization);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createRolePage(Principal principal) {
        var modelAndView = new ModelAndView("organization/new-organization");
        var createOrganizationRequest = new CreateOrganizationRequest();
        modelAndView.addObject("pageTitle", "Add Organization");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("organization", createOrganizationRequest);
        return modelAndView;
    }

    @PostMapping
    public String createOrganization(@Valid @ModelAttribute("organization") CreateOrganizationRequest request, BindingResult bindingResult, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Add Organization");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("organization", request);
                return "organization/new-organization";
            }
            organizationService.createOne(request);
            return "redirect:/organizations";
        } catch (Exception e) {
            return "redirect:/organizations/create";
        }
    }

    @GetMapping("/{id}/update")
    public ModelAndView updateOrganizationPage(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("organization/update-organization");
        var organization = organizationService.getOne(id);
        modelAndView.addObject("pageTitle", "Update Organization");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("organization", organization);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String updateOrganization(@Valid @ModelAttribute("organization") UpdateOrganizationRequest request, @PathVariable UUID id, BindingResult bindingResult, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Update organization");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("organization", request);
                return "organization/update-organization";
            }
            organizationService.updateOne( id, request);
            return "redirect:/organizations";
        } catch (Exception e) {
            return "redirect:/organizations";
        }
    }

    @GetMapping("{id}/delete")
    public String deleteOrganization(@PathVariable UUID id) {
        organizationService.deleteOne(id);
        return "redirect:/organizations";
    }
}
