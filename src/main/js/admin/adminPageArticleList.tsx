import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminBasicLayout} from "./fragment/adminBasicLayout";
import {ArticleListBody} from "./fragment/articlelist/articleListBody";

export const AdminPageArticleList: React.FC = () => {


    return (
        <AdminBasicLayout>
            <ArticleListBody
                isForAdmin={true}
                hasSearchMenu={true}
                directoryToIndividualPage={"/admin/articles"}
            />
        </AdminBasicLayout>
    );
};
