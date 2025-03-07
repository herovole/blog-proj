import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleListBody} from "../admin/fragment/articlelist/articleListBody";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";

export const PublicPageArticleList = () => {

    return (
        <PublicBasicLayout>
            <ArticleListBody
                directoryToIndividualPage={"/articles"}
                hasSearchMenu={true}
            />
        </PublicBasicLayout>
    );
};
