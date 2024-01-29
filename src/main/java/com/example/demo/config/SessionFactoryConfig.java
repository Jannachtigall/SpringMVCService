package com.example.demo.config;

import com.example.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SessionFactoryConfig {

    private SessionFactory sessionFactory;

    @PostConstruct
    void init() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

}
