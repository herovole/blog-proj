import React from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ElementId} from "../domain/elementId"
import {PublicArticleListBody} from "./fragment/articlelist/publicArticleListBody";

export const PublicPageArticleList = () => {

    const {articleId} = useParams();

    return (
        <div>
            <PublicArticleListBody
                formKey={new ElementId("articleList")}
                directoryToIndividualPage={"/articles"}
            />
        </div>
    );
};
