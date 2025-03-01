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
import {SearchRatingHistoryInput} from "../service/user/searchRatingHistoryInput";
import {UserService} from "../service/user/userService";
import {SearchRatingHistoryOutput} from "../service/user/searchRatingHistoryOutput";

export const PublicPageArticleView: React.FC = () => {
    const {articleId} = useParams();
    const articleService: ArticleService = new ArticleService();
    const userService: UserService = new UserService();
    const tagService: TagService = new TagService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [article, setArticle] = React.useState<Article>();
    const [ratingHistory, setRatingHistory] = React.useState<SearchRatingHistoryOutput>();
    const [refresh, setRefresh] = React.useState(false);
    const reRender = () => {
        setRefresh(r => !r);
    }
    const load = async (): Promise<void> => {
        try {

            const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const topicOutput: SearchTagsOutput = await tagService.searchTopicTags(topicInput);
            if(topicOutput.isSuccessful()) {
                setTopicTagsOptions(topicOutput.getTagUnits());
            } else {
                console.error("failed to fetch topic tags");
                return;
            }

            const countriesInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const countriesOutput: SearchTagsOutput = await tagService.searchCountries(countriesInput);
            if(countriesOutput.isSuccessful()) {
                setCountryTagsOptions(countriesOutput.getTagUnits());
            } else {
                console.error("failed to fetch country tags");
                return;
            }

            const articleInput: FindArticleInput = new FindArticleInput(articleId, false);
            const findArticleOutput: FindArticleOutput = await articleService.findArticle(articleInput);
            if(findArticleOutput.isSuccessful()) {
                setArticle(findArticleOutput.getArticle());
            } else {
                console.error("failed to fetch article record");
                return;
            }

            const searchRatingHistoryInput: SearchRatingHistoryInput = new SearchRatingHistoryInput(articleId);
            const searchRatingHistoryOutput: SearchRatingHistoryOutput = await userService.searchRatingHistory(searchRatingHistoryInput);
            if(searchRatingHistoryOutput.isSuccessful()) {
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
                topicTagOptions={topicTagsOptions}
                countryTagOptions={countryTagsOptions}
                ratingHistory={ratingHistory}
                reRender={reRender}
            /></PublicBasicLayout>
    } else {
        return <PublicBasicLayout>
            <div>Loading...</div>
        </PublicBasicLayout>
    }
};
