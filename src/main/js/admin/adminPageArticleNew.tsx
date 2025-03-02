import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {RootElementId} from "../domain/elementId/rootElementId";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

export const AdminPageArticleNew: React.FC = () => {

        const load = async (): Promise<void> => {
        };
        useEffect(() => {
            load().then();
        }, []);

        return (
            <AdminBasicLayout>
                <AdminArticleBody
                    postKey={RootElementId.valueOf("articleEditingPage")}
                />
            </AdminBasicLayout>
        );
    }
;
