package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

//@Repository
public class AccidentMem implements Store {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private final Map<Integer, Rule> rules = new HashMap<>();
    private final AtomicInteger currentAccidentId = new AtomicInteger(0);
    private final AtomicInteger currentTypeId = new AtomicInteger(0);
    private final AtomicInteger currentRuleId = new AtomicInteger(0);

    public AccidentMem() {
        int typeId1 = currentTypeId.incrementAndGet();
        types.put(typeId1, AccidentType.of(typeId1, "Две машины"));
        int typeId2 = currentTypeId.incrementAndGet();
        types.put(typeId2, AccidentType.of(types.size() + 1, "Машина и человек"));
        int typeId3 = currentTypeId.incrementAndGet();
        types.put(typeId3, AccidentType.of(types.size() + 1, "Машина и велосипед"));

        int ruleId1 = currentRuleId.incrementAndGet();
        rules.put(ruleId1, Rule.of(ruleId1, "Статья 1"));
        int ruleId2 = currentRuleId.incrementAndGet();
        rules.put(ruleId2, Rule.of(ruleId2, "Статья 2"));
        int ruleId3 = currentRuleId.incrementAndGet();
        rules.put(ruleId3, Rule.of(ruleId3, "Статья 3"));

        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    int id = currentAccidentId.incrementAndGet();
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
            accident.setId(currentAccidentId.incrementAndGet());
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
