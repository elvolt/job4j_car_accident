package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

//@Repository
public class AccidentJdbcTemplate implements Store {
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNum) -> {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("type_id"));
        accident.setType(type);
        return accident;
    };

    private final RowMapper<AccidentType> typeRowMapper = (resultSet, rowNum) -> {
        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("id"));
        type.setName(resultSet.getString("name"));
        return type;
    };

    private final RowMapper<Rule> ruleRowMapper = (resultSet, rowNum) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private List<Rule> getRulesByAccidentId(int id) {
        return jdbc.query("select rule.id, rule.name from rule "
                        + "join accident_rule as ar on ar.rule_id = rule.id "
                        + "left join accident as a on a.id = ar.accident_id "
                        + "where ar.accident_id = ?",
                ruleRowMapper,
                id
        );
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        List<Accident> accidents = jdbc.query("select * from accident order by id",
                accidentRowMapper);
        accidents.forEach(accident -> {
            String typeName = jdbc.queryForObject("select name from type where id = ?",
                    String.class, accident.getType().getId());
            accident.getType().setName(typeName);
            accident.setRules(new HashSet<>(getRulesByAccidentId(accident.getId())));
        });
        return accidents;
    }

    @Override
    public void saveAccident(Accident accident) {
        if (accident.getId() == 0) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into accident (name, text, address, type_id) values (?, ?, ?, ?)",
                        new String[]{"id"}
                );
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                return ps;
            }, keyHolder);
            accident.setId((Integer) keyHolder.getKey());
        } else {
            jdbc.update(
                    "update accident set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                    accident.getName(), accident.getText(), accident.getAddress(),
                    accident.getType().getId(), accident.getId()
            );
            jdbc.update("delete from accident_rule where accident_id = ?", accident.getId());
        }
        accident.getRules().forEach(rule ->
                jdbc.update("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                        accident.getId(), rule.getId()));
    }

    @Override
    public Accident findAccidentById(int id) {
        Accident accident = jdbc.queryForObject("select * from accident where id = ?",
                accidentRowMapper, id);
        accident.setRules(new HashSet<>(getRulesByAccidentId(id)));
        return accident;
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return jdbc.query("select * from type", typeRowMapper);
    }

    @Override
    public AccidentType findAccidentTypeById(int id) {
        return jdbc.queryForObject("select * from type where id = ?", typeRowMapper, id);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return jdbc.query("select * from rule", ruleRowMapper);
    }

    @Override
    public Rule findRuleById(int id) {
        return jdbc.queryForObject("select * from rule where id = ?", ruleRowMapper, id);
    }
}
