package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String create() {
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam("typeId") int typeId,
                       @RequestParam("ruleIds") List<Integer> ruleIds) {
        service.saveAccident(accident, typeId, ruleIds);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", service.findAccidentById(id));
        return "accident/update";
    }

    @ModelAttribute
    public void addTypes(Model model) {
        Collection<AccidentType> types = service.getAllAccidentTypes();
        model.addAttribute("types", types);
    }

    @ModelAttribute
    public void addRules(Model model) {
        Collection<Rule> rules = service.getAllRules();
        model.addAttribute("rules", rules);
    }
}
