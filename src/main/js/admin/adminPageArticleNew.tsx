import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {RootElementId} from "../domain/elementId/rootElementId";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

type AdminPageArticleNewProps = {
    pageArticleList: string;
}
export const AdminPageArticleNew: React.FC<AdminPageArticleNewProps> = ({pageArticleList}) => {


    return (
        <AdminBasicLayout>
            <AdminArticleBody
                postKey={RootElementId.valueOf("articleEditingPage")}
                pageArticleList={pageArticleList}
            />
        </AdminBasicLayout>
    );
};
