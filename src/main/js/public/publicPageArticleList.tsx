import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {PublicArticleListBody} from "./fragment/articlelist/publicArticleListBody";

export const PublicPageArticleList = () => {

    return (
        <div>
            <PublicArticleListBody
                directoryToIndividualPage={"/articles"}
            />
        </div>
    );
};
