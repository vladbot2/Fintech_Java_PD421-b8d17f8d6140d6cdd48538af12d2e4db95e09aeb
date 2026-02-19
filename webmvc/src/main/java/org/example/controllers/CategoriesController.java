package org.example.controllers;


import lombok.RequiredArgsConstructor;
import org.example.dtos.category.CategoryCreateDTO;
import org.example.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model){
        var items = categoryService.getAll();
        model.addAttribute("categories", items);
        return "categories/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("model", new CategoryCreateDTO());
        return "categories/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("model") CategoryCreateDTO model) {
        categoryService.create(model);
        return "redirect:/categories/list";
    }
}
