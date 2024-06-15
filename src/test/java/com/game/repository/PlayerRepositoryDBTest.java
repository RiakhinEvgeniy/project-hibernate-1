package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerRepositoryDBTest {

    static SessionFactory sessionFactory;
    Session session;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @BeforeEach
    void setSession() {
        session = sessionFactory.openSession();
    }

    @Test
    void checkSessionFromXmlFile() {
        assertNotNull(session);
    }

    @Test
    void testForGetAll() {
        int pageNumber = 3;
        int pageSize = 5;
        NativeQuery<Player> nativeQuery = session.createNativeQuery("select * from player", Player.class);
        nativeQuery.setFirstResult(pageNumber * pageSize);
        nativeQuery.setMaxResults(pageSize);
        List<Player> players = nativeQuery.getResultList();
        assertNotNull(players);
        Assertions.assertEquals(5, players.size());
    }

    @AfterEach
    void closeSession() {
        session.close();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }
}