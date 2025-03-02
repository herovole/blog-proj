import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminBasicLayout} from "./fragment/adminBasicLayout";
import {ArticleListBody} from "./fragment/articlelist/articleListBody";

export const AdminPageArticleList: React.FC = () => {


    const load = async (): Promise<void> => {
    };
    useEffect(() => {
        load().then();
    }, []);

    return (
        <AdminBasicLayout>
            <ArticleListBody
                hasSearchMenu={true}
                directoryToIndividualPage={"/admin/articles"}
            />
        </AdminBasicLayout>
    );
};
