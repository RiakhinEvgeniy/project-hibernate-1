package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testForGetById() {
        long playerId = 1;
        Player player = session.get(Player.class, playerId);
        String name = player.getName();
        assertNotNull(player);
        assertEquals("Scholz", name);
    }

    @Test
    void testForUpdate() {
        long playerId = 1;
        Player player = session.get(Player.class, playerId);
        player.setName("Scholz");
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            session.update(player);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        Player newPlayer = session.get(Player.class, playerId);
        assertEquals("Scholz", newPlayer.getName());
    }

    @Test
    void testForDelete() {
        long playerId = 2;
        Player player = session.get(Player.class, playerId);
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            session.delete(player);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        Player newPlayer = session.get(Player.class, playerId);
        Assertions.assertNull(newPlayer);
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