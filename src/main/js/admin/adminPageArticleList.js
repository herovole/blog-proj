import React from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleListBody} from "./fragment/articlelist/adminArticleListBody"
import {ElementId} from "../domain/elementId"
import {AdminHeader} from "./adminHeader";

export const AdminPageArticleList = () => {

    const {articleId} = useParams();

    return (
        <div>
            <AdminHeader/>
            <AdminArticleListBody
                formKey={new ElementId("articleList")}
                directoryToIndividualPage={"/admin/articles"}
            />
        </div>
    );
};
