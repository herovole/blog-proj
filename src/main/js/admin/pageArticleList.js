import React from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleListBody} from "./fragment/articleList/articleListBody"
import {ElementId} from "../domain/elementId"
import {AdminHeader} from "./adminHeader";

export const PageArticleList = () => {

    const {articleId} = useParams();

    return (
        <div>
            <AdminHeader/>
            <ArticleListBody
                formKey={new ElementId("articleList")}
                directoryToIndividualPage={"/admin/articles"}
            />
        </div>
    );
};
