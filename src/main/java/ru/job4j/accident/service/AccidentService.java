package ru.job4j.accident.service;

import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;

public interface AccidentService {
    Collection<Accident> getAllAccidents();

    void saveAccident(Accident accident);

    Accident findAccidentById(int id);

    Collection<AccidentType> getAllAccidentTypes();

    AccidentType findAccidentTypeById(int id);

    Collection<Rule> getAllRules();

    Rule findRuleById(int id);
}
