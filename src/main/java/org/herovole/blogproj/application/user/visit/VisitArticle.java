package org.herovole.blogproj.application.user.visit;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.publicuser.visit.Visit;
import org.herovole.blogproj.domain.publicuser.visit.VisitDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisitArticle {

    private static final Logger logger = LoggerFactory.getLogger(VisitArticle.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final VisitDatasource visitDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public VisitArticle(AppSessionFactory sessionFactory,
                        VisitDatasource visitDatasource,
                        GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.visitDatasource = visitDatasource;
        this.presenter = presenter;
    }

    public void process(VisitArticleInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        Visit visit = input.buildVisit();
        this.visitDatasource.report(visit);

        try (AppSession session = sessionFactory.createSession()) {
            this.visitDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
        logger.info("job successful.");

    }
}
