import React from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleListBody} from "./fragment/articlelist/adminArticleListBody"
import {ElementId} from "../domain/elementId/elementId"
import {AdminHeader} from "./adminHeader";
import {RootElementId} from "../domain/elementId/rootElementId";

export const AdminPageArticleList = () => {

    const {articleId} = useParams();

    return (
        <div>
            <AdminHeader/>
            <AdminArticleListBody
                formKey={RootElementId.valueOf("articleList")}
                directoryToIndividualPage={"/admin/articles"}
            />
        </div>
    );
};
