package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.springdata.AccidentRepository;
import ru.job4j.accident.repository.springdata.AccidentTypesRepository;
import ru.job4j.accident.repository.springdata.RuleRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccidentServiceImpl implements AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypesRepository accidentTypesRepository;
    private final RuleRepository ruleRepository;

    public AccidentServiceImpl(AccidentRepository accidentRepository,
                               AccidentTypesRepository accidentTypesRepository,
                               RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.accidentTypesRepository = accidentTypesRepository;
        this.ruleRepository = ruleRepository;
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return (Collection<Accident>) accidentRepository.findAll();
    }

    @Override
    public void saveAccident(Accident accident, int typeId, List<Integer> ruleIds) {
        accident.setType(findAccidentTypeById(typeId));
        Set<Rule> rules = ruleIds.stream()
                .map(this::findRuleById)
                .collect(Collectors.toSet());
        accident.setRules(rules);
        accidentRepository.save(accident);
    }

    @Override
    public Accident findAccidentById(int id) {
        Optional<Accident> result =  accidentRepository.findById(id);
        if (result.isEmpty()) {
            throw new NoSuchElementException("Accident not found");
        }
        return result.get();
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return (Collection<AccidentType>) accidentTypesRepository.findAll();
    }

    @Override
    public AccidentType findAccidentTypeById(int id) {
        Optional<AccidentType> result =  accidentTypesRepository.findById(id);
        if (result.isEmpty()) {
            throw new NoSuchElementException("AccidentType not found");
        }
        return result.get();
    }

    @Override
    public Collection<Rule> getAllRules() {
        return (Collection<Rule>) ruleRepository.findAll();
    }

    @Override
    public Rule findRuleById(int id) {
        Optional<Rule> result =  ruleRepository.findById(id);
        if (result.isEmpty()) {
            throw new NoSuchElementException("Rule not found");
        }
        return result.get();
    }
}
