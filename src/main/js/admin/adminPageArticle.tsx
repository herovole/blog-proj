import React, {useEffect} from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {Article} from "../domain/article";
import {RootElementId} from "../domain/elementId/rootElementId";
import {SearchTagsInput} from "../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {FindArticleInput} from "../service/articles/findArticleInput";
import {FindArticleOutput} from "../service/articles/findArticleOutput";
import {ArticleService} from "../service/articles/articleService";
import {TagService} from "../service/tags/tagService";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

export const AdminPageArticle: React.FC = () => {
    const {articleId} = useParams();
    const articleService: ArticleService = new ArticleService();
    const tagService: TagService = new TagService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [article, setArticle] = React.useState<Article>();

    const load = async (): Promise<void> => {

        const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
        const topicOutput: SearchTagsOutput = await tagService.searchTopicTags(topicInput);
        if (topicOutput.isSuccessful()) {
            setTopicTagsOptions(topicOutput.getTagUnits());
        } else {
            console.error("failed to fetch topic tags");
            return;
        }

        const countriesInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
        const countriesOutput: SearchTagsOutput = await tagService.searchCountries(countriesInput);
        if (countriesOutput.isSuccessful()) {
            setCountryTagsOptions(countriesOutput.getTagUnits());
        } else {
            console.error("failed to fetch country tags");
            return;
        }

        const articleInput: FindArticleInput = new FindArticleInput(articleId, true);
        const findArticleOutput: FindArticleOutput = await articleService.findArticle(articleInput);
        if (findArticleOutput.isSuccessful()) {
            setArticle(findArticleOutput.getArticle());
        } else {
            console.error("failed to fetch article record");
        }
    };
    useEffect(() => {
        load().then(r => {
            console.log(r);
        });
    }, []);

    if (article) {
        return (
            <AdminBasicLayout>
                <AdminArticleBody
                    postKey={RootElementId.valueOf("articleEditingPage")}
                    content={article}
                    topicTagsOptions={topicTagsOptions}
                    countryTagsOptions={countryTagsOptions}
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
