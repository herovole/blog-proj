package org.herovole.blogproj.infra.hibernate;

import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AppSessionFactoryHibernate implements AppSessionFactory {

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
