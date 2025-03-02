import React, {useEffect} from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Article} from "../domain/article";
import {PublicArticleBody} from "./fragment/article/publicArticleBody";
import {RootElementId} from "../domain/elementId/rootElementId";
import {FindArticleOutput} from "../service/articles/findArticleOutput";
import {FindArticleInput} from "../service/articles/findArticleInput";
import {ArticleService} from "../service/articles/articleService";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {SearchRatingHistoryInput} from "../service/user/searchRatingHistoryInput";
import {UserService} from "../service/user/userService";
import {SearchRatingHistoryOutput} from "../service/user/searchRatingHistoryOutput";

export const PublicPageArticleView: React.FC = () => {
    const {articleId} = useParams();
    const articleService: ArticleService = new ArticleService();
    const userService: UserService = new UserService();
    const [article, setArticle] = React.useState<Article>();
    const [ratingHistory, setRatingHistory] = React.useState<SearchRatingHistoryOutput>();
    const [refresh, setRefresh] = React.useState(false);
    const reRender = () => {
        setRefresh(r => !r);
    }
    const load = async (): Promise<void> => {
        try {
            const articleInput: FindArticleInput = new FindArticleInput(articleId, false);
            const findArticleOutput: FindArticleOutput = await articleService.findArticle(articleInput);
            if (findArticleOutput.isSuccessful()) {
                setArticle(findArticleOutput.getArticle());
            } else {
                console.error("failed to fetch article record");
                return;
            }

            const searchRatingHistoryInput: SearchRatingHistoryInput = new SearchRatingHistoryInput(articleId);
            const searchRatingHistoryOutput: SearchRatingHistoryOutput = await userService.searchRatingHistory(searchRatingHistoryInput);
            if (searchRatingHistoryOutput.isSuccessful()) {
                setRatingHistory(searchRatingHistoryOutput);
            } else {
                console.error("failed to reconstruct rating history");
                return;
            }

        } catch (error) {
            console.error("error : ", error);
        }
    };
    useEffect(() => {
        load().then();
    }, [refresh]);

    if (article && ratingHistory) {
        return <PublicBasicLayout>
            <PublicArticleBody
                postKey={RootElementId.valueOf("article")}
                article={article}
                ratingHistory={ratingHistory}
                reRender={reRender}
            /></PublicBasicLayout>
    } else {
        return <PublicBasicLayout>
            <div>Loading...</div>
        </PublicBasicLayout>
    }
};
