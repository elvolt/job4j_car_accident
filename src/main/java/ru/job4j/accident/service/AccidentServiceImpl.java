package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.Store;

import java.util.Collection;

@Service
public class AccidentServiceImpl implements AccidentService {
    private final Store store;

    public AccidentServiceImpl(Store store) {
        this.store = store;
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return store.getAllAccidents();
    }

    @Override
    public void saveAccident(Accident accident) {
        store.saveAccident(accident);
    }

    @Override
    public Accident findAccidentById(int id) {
        return store.findAccidentById(id);
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return store.getAllAccidentTypes();
    }

    @Override
    public AccidentType findAccidentTypeById(int id) {
        return store.findAccidentTypeById(id);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return store.getAllRules();
    }

    @Override
    public Rule findRuleById(int id) {
        return store.findRuleById(id);
    }
}
