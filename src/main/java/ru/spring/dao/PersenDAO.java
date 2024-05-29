package ru.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersenDAO {

   /*
    private final JdbcTemplate jdbcTemplate;
  @Autowired
    public PersenDAO(JdbcTemplate jdbcTemplate) { //внедрили jdbcTemplate is SpringConfig
        this.jdbcTemplate = jdbcTemplate;
    }*/

    private  final SessionFactory sessionFactory;

    @Autowired
    public PersenDAO(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }

    //вернули весь список
    @Transactional(readOnly = true) // открыли транзакцию true- только читаем данные
    public List<Person> index() {
        //      return jdbcTemplate.query("SELECT*FROM Person", new BeanPropertyRowMapper<>(Person.class));
        Session session = sessionFactory.getCurrentSession();
        //Здесь обычный hibernate code
        List<Person> people = session.createQuery("select p from Person p", Person.class )
                .getResultList();
        return people;
    }

    //вернули из списка чела по id
    @Transactional(readOnly = true)
    public Person show(int id) {
     //   return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)) // равнозначто для перевода строк таблицы и переменных с одинаковым именем.stream().findAny().orElse(null); //если в списке есть обект  класса персон то он будет возрашен иначе null
 Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class,id);
    }

    //сохранить нового человека
    @Transactional
    public void save(Person person) {
  //      jdbcTemplate.update("INSERT INTO Person VALUES(1,?,?,?)", person.getName(), person.getAge(), person.getEmail());
Session session = sessionFactory.getCurrentSession();
session.save( person);
    }

    //обновляем человека
    @Transactional
    public void update(int id, Person updatePerson) {
 //       jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatePerson.getName(),updatePerson.getAge(), updatePerson.getEmail(), updatePerson.getId());
    Session session= sessionFactory.getCurrentSession();
     Person person = session.get(Person.class, id);

    person.setName(updatePerson.getName());
    person.setAge(updatePerson.getAge());
    person.setEmail(updatePerson.getEmail());
    }

    //удаляем человека
    @Transactional
    public void delete(int id) {
 //       jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }
}
