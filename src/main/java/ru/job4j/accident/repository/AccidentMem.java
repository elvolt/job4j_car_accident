package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class AccidentMem implements Store {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private final Map<Integer, Rule> rules = new HashMap<>();

    public AccidentMem() {
        types.put(types.size() + 1, AccidentType.of(types.size() + 1, "Две машины"));
        types.put(types.size() + 1, AccidentType.of(types.size() + 1, "Машина и человек"));
        types.put(types.size() + 1, AccidentType.of(types.size() + 1, "Машина и велосипед"));

        rules.put(rules.size() + 1, Rule.of(rules.size() + 1, "Статья 1"));
        rules.put(rules.size() + 1, Rule.of(rules.size() + 1, "Статья 2"));
        rules.put(rules.size() + 1, Rule.of(rules.size() + 1, "Статья 3"));

        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    int id = accidents.size() + 1;
                    Accident accident = new Accident();
                    accident.setId(id);
                    accident.setName("Name " + i);
                    accident.setAddress("Address " + i);
                    accident.setText("Text " + i);
                    accident.setType(types.get(2));
                    accident.setRules(Set.of(rules.get(1), rules.get(2)));
                    accidents.put(id, accident);
                });
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    @Override
    public void saveAccident(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(accidents.size() + 1);
        }
        accidents.put(accident.getId(), accident);
    }

    @Override
    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return types.values();
    }

    @Override
    public AccidentType findAccidentTypeById(int id) {
        return types.get(id);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return rules.values();
    }

    @Override
    public Rule findRuleById(int id) {
        return rules.get(id);
    }
}
