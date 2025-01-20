import React, {useEffect} from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Article} from "../domain/article";
import {PublicArticleBody} from "./fragment/article/publicArticleBody";
import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";
import {RootElementId} from "../domain/elementId/rootElementId";
import {FindArticleOutput} from "../service/articles/findArticleOutput";
import {TagService} from "../service/tags/tagService";
import {SearchTagsInput} from "../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {FindArticleInput} from "../service/articles/findArticleInput";
import {ArticleService} from "../service/articles/articleService";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";

export const PublicPageArticleView: React.FC = () => {
    const {articleId} = useParams();
    const articleService: ArticleService = new ArticleService();
    const tagService: TagService = new TagService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [article, setArticle] = React.useState<Article>();
    const [refresh, setRefresh] = React.useState(false);
    const reRender = () => {
        console.log("reload the page, flag:" + refresh);
        setRefresh(r => !r);
    }
    const load = async (): Promise<void> => {
        try {

            const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const topicOutput: SearchTagsOutput = await tagService.searchTopicTags(topicInput);
            setTopicTagsOptions(topicOutput.getTagUnits());

            const countriesInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const countriesOutput: SearchTagsOutput = await tagService.searchCountries(countriesInput);
            setCountryTagsOptions(countriesOutput.getTagUnits());

            const articleInput: FindArticleInput = new FindArticleInput(articleId);
            const findArticleOutput: FindArticleOutput = await articleService.findArticle(articleInput);
            setArticle(findArticleOutput.getArticle());
        } catch (error) {
            console.error("error : ", error);
        }
    };
    useEffect(() => {
        load().then(r => {
            console.log(r);
        });
    }, [refresh]);

    if (article) {
        return <PublicBasicLayout>
            <PublicArticleBody
                postKey={RootElementId.valueOf("article")}
                article={article}
                topicTagOptions={topicTagsOptions}
                countryTagOptions={countryTagsOptions}
                reRender={reRender}
            /></PublicBasicLayout>
    } else {
        return <PublicBasicLayout>
            <div>Loading...</div>
        </PublicBasicLayout>
    }
};
