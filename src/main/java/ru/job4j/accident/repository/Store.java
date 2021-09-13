package ru.job4j.accident.repository;

import ru.job4j.accident.model.Accident;

import java.util.Collection;

public interface Store {
    Collection<Accident> getAllAccidents();

    void saveAccident(Accident accident);
}
