package org.herovole.blogproj.infra.hibernate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AppSessionFactoryHibernate implements AppSessionFactory {
    private static AppSessionFactoryHibernate instance;

    public static AppSessionFactoryHibernate getInstance() {
        if (instance == null)
            instance = new AppSessionFactoryHibernate(new Configuration().configure().buildSessionFactory());
        return instance;
    }

    private final SessionFactory sessionFactory;


    @Override
    public AppSession createSession() {
        return AppSessionHibernate.of(sessionFactory.openSession());
    }

    @Override
    public void connect() throws IOException {
        if (sessionFactory == null) throw new IOException();
    }
}
