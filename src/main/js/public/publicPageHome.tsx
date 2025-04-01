import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {ArticleListBody} from "../admin/fragment/articlelist/articleListBody";
import {HeadlinesMode} from "./fragment/articlelist/publicArticleHeadlines";
import {VisitArticleInput} from "../service/articles/visitArticleInput";
import {BasicApiResult} from "../domain/basicApiResult";
import {ArticleService} from "../service/articles/articleService";
import {MetaInfo} from "./fragment/metaInfo";

export const PublicPageHome: React.FC = () => {
    const ARTICLE_ID: string = "0";
    const articleService: ArticleService = new ArticleService();

    const load = async (): Promise<void> => {
        try {
            const visitArticleInput: VisitArticleInput = new VisitArticleInput(ARTICLE_ID);
            const visitArticleOutput: BasicApiResult = await articleService.visitArticle(visitArticleInput);
            if (!visitArticleOutput.isSuccessful()) {
                console.error("failed to visit");
                return;
            }
        } catch (error) {
            console.error("error : ", error);
        }
    };

    useEffect(() => {
        window.scrollTo({ top: 0, behavior: "auto" });
        load().then();
    }, []);

    return <>
        <MetaInfo
            tabTitle={"Home"}
            description={"まとめサイト"}
            keywords={"まとめ, まとめサイト, 世界"}
            image={"ogimage.jpg"}
            hasSystemImage={true}
        />
        <PublicBasicLayout>
            <ArticleListBody
                mode={HeadlinesMode.LARGE}
                hasSearchMenu={false}
                directoryToIndividualPage={"/articles"}
            />
        </PublicBasicLayout>
    </>
};
