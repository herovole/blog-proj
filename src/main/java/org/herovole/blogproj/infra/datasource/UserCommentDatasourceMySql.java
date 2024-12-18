package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.comment.UserCommentDatasource;
import org.herovole.blogproj.infra.jpa.repository.EUserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userCommentDatasource")
public class UserCommentDatasourceMySql implements UserCommentDatasource {
    protected final EUserCommentRepository eUserCommentRepository;

    @Autowired
    public UserCommentDatasourceMySql(EUserCommentRepository eUserCommentRepository) {
        this.eUserCommentRepository = eUserCommentRepository;
    }
}
