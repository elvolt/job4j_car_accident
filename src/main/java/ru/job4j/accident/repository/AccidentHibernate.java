package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.function.Function;

@Repository
public class AccidentHibernate implements Store {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Accident a join fetch a.rules order by a.id",
                        Accident.class
                ).list()
        );
    }

    @Override
    public void saveAccident(Accident accident) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            if (accident.getId() == 0) {
                session.save(accident);
            } else {
                session.update(accident);
            }
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Accident findAccidentById(int id) {
        return tx(session ->
                session.createQuery(
                        "select a from Accident a join fetch a.rules where a.id =: paramId",
                        Accident.class)
                        .setParameter("paramId", id)
                        .uniqueResult());
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return tx(session -> session.createQuery("from AccidentType", AccidentType.class).list());
    }

    @Override
    public AccidentType findAccidentTypeById(int id) {
        return tx(session -> session.get(AccidentType.class, id));
    }

    @Override
    public Collection<Rule> getAllRules() {
        return tx(session -> session.createQuery("from Rule", Rule.class).list());
    }

    @Override
    public Rule findRuleById(int id) {
        return tx(session -> session.get(Rule.class, id));
    }
}
