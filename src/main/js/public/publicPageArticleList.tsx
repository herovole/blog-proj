import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleListBody} from "../admin/fragment/articlelist/articleListBody";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {MetaInfo} from "./fragment/metaInfo";

export const PublicPageArticleList = () => {

    useEffect(() => {
        window.scrollTo({top: 0, behavior: "auto"});
    }, []);

    return (<>
            <MetaInfo
                tabTitle={"記事検索"}
            />
            <PublicBasicLayout>
                <ArticleListBody
                    directoryToIndividualPage={"/articles"}
                    hasSearchMenu={true}
                />
            </PublicBasicLayout>
        </>
    );
};
