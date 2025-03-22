import React, {useEffect} from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {Article} from "../domain/article";
import {RootElementId} from "../domain/elementId/rootElementId";
import {FindArticleInput} from "../service/articles/findArticleInput";
import {FindArticleOutput} from "../service/articles/findArticleOutput";
import {ArticleService} from "../service/articles/articleService";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

export const AdminPageArticle: React.FC = () => {
    const {articleId} = useParams();
    const articleService: ArticleService = new ArticleService();
    const [article, setArticle] = React.useState<Article>();
    const [refresh, setRefresh] = React.useState<boolean>(false);

    const load = async (): Promise<void> => {
        const articleInput: FindArticleInput = new FindArticleInput(articleId, true);
        const findArticleOutput: FindArticleOutput = await articleService.findArticle(articleInput);
        if (findArticleOutput.isSuccessful()) {
            setArticle(findArticleOutput.getArticle());
        } else {
            console.error("failed to fetch article record");
        }
    };

    const reload = (): void => {
        setRefresh(r => !r);
    }

    useEffect(() => {
        load().then();
    }, [refresh]);

    if (article) {
        return (
            <AdminBasicLayout>
                <AdminArticleBody
                    postKey={RootElementId.valueOf("articleEditingPage")}
                    content={article}
                    reload={reload}
                />
            </AdminBasicLayout>
        );
    } else {
        return (
            <AdminBasicLayout>
                <div>Loading...</div>
            </AdminBasicLayout>
        );
    }
};
