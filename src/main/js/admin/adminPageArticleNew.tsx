import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {RootElementId} from "../domain/elementId/rootElementId";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

export const AdminPageArticleNew: React.FC = () => {

        return (
            <AdminBasicLayout>
                <AdminArticleBody
                    postKey={RootElementId.valueOf("articleEditingPage")}
                />
            </AdminBasicLayout>
        );
    }
;
