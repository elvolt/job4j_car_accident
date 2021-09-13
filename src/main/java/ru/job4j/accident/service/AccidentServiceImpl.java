package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
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
}
