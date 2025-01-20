import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleListBody} from "./fragment/articlelist/adminArticleListBody"
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

export const AdminPageArticleList: React.FC = () => {

    return (
        <AdminBasicLayout>
            <AdminArticleListBody
                directoryToIndividualPage={"/admin/articles"}
            />
        </AdminBasicLayout>
    );
};
