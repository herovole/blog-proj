import React, {useEffect} from 'react';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {Article} from "../domain/article";
import {AdminHeader} from "./adminHeader";
import {RootElementId} from "../domain/elementId/rootElementId";
import {SearchTagsInput} from "../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../service/tags/searchTagsOutput";
import {FindArticleInput} from "../service/articles/findArticleInput";
import {FindArticleOutput} from "../service/articles/findArticleOutput";
import {ArticleService} from "../service/articles/articleService";
import {TagService} from "../service/tags/tagService";

export const AdminPageArticle: React.FC = () => {
    const {articleId} = useParams();
    const articleService: ArticleService = new ArticleService();
    const tagService: TagService = new TagService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [article, setArticle] = React.useState<Article>();

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
    }, []);

    if (article) {
        return (
            <div>
                <AdminHeader/>
                <AdminArticleBody
                    postKey={RootElementId.valueOf("articleEditingPage")}
                    content={article}
                    topicTagOptions={topicTagsOptions}
                    countryTagOptions={countryTagsOptions}
                />
            </div>
        );
    } else {
        return <div>Loading...</div>
    }
};
