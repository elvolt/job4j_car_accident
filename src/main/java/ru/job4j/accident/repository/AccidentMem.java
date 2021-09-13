package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Repository
public class AccidentMem implements Store {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private int size = 0;

    public AccidentMem() {
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    accidents.put(i,
                            new Accident(size, "Name " + i, "Text " + i, "Address " + i));
                    size++;
                });
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    @Override
    public void saveAccident(Accident accident) {
        accident.setId(size);
        accidents.put(size, accident);
    }
}
