package org.herovole.blogproj;

import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.infra.hibernate.AppSessionFactoryHibernate;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIConfig {

    @Bean
    public AppSessionFactory appSessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        return new AppSessionFactoryHibernate(sessionFactory);
    }
}
