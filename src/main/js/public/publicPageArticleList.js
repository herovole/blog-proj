import React from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ElementId} from "../domain/elementId/elementId"
import {PublicArticleListBody} from "./fragment/articlelist/publicArticleListBody";
import {RootElementId} from "../domain/elementId/rootElementId";

export const PublicPageArticleList = () => {

    const {articleId} = useParams();

    return (
        <div>
            <PublicArticleListBody
                formKey={RootElementId.valueOf("articleList")}
                directoryToIndividualPage={"/articles"}
            />
        </div>
    );
};
