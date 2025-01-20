import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {PublicArticleListBody} from "./fragment/articlelist/publicArticleListBody";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";

export const PublicPageArticleList = () => {

    return (
        <PublicBasicLayout>
            <PublicArticleListBody directoryToIndividualPage={"/articles"}/>
        </PublicBasicLayout>
    );
};
