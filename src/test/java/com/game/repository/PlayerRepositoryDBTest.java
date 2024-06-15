package com.game.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerRepositoryDBTest {

    SessionFactory sessionFactory;

    @Test
    void checkSessionFromXmlFile() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        assertNotNull(session);
    }
}