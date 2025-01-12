import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleListBody} from "./fragment/articlelist/adminArticleListBody"
import {AdminHeader} from "./adminHeader";

export const AdminPageArticleList: React.FC = () => {

    return (
        <div>
            <AdminHeader/>
            <AdminArticleListBody
                directoryToIndividualPage={"/admin/articles"}
            />
        </div>
    );
};
