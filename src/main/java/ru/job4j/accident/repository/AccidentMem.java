package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Repository
public class AccidentMem implements Store {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger size = new AtomicInteger(0);

    public AccidentMem() {
        IntStream.rangeClosed(1, 10)
                .forEach(i -> accidents.put(i, new Accident(
                                size.incrementAndGet(),
                                "Name " + i,
                                "Text " + i,
                                "Address " + i)
                        )
                );
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    @Override
    public void saveAccident(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(size.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }

    @Override
    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }
}
