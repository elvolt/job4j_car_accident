package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        List<String> strings = new ArrayList<>();
        IntStream.rangeClosed(0, 10)
                .forEach(i -> strings.add("String " + i));
        model.addAttribute("strings", strings);
        return "index";
    }
}