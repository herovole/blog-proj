import React, {useEffect} from 'react';
import {useParams} from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Article} from "../domain/article";
import {PublicArticleBody} from "./fragment/article/publicArticleBody";
import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";
import {RootElementId} from "../domain/elementId/rootElementId";
import {ElementId} from "../domain/elementId/elementId";
import {FindArticleOutput} from "../domain/findArticleOutput";

export const PublicPageArticleView: React.FC = () => {
    const {articleId} = useParams();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [article, setArticle] = React.useState<Article>();
    const [refresh, setRefresh] = React.useState(false);
    const reRender = () => {
        console.log("reload the page, flag:" + refresh);
        setRefresh(r => !r);
    }

    useEffect(() => {
        const fetchTagsOptions = async (): Promise<void> => {
            try {
                const topicTagsKey: ElementId = RootElementId.valueOf("topicTags");
                const topicResponse = await axios.get("/api/v1/topicTags", {
                    params: {
                        [topicTagsKey.append("page").toStringKey()]: 1,
                        [topicTagsKey.append("itemsPerPage").toStringKey()]: 10000,
                        [topicTagsKey.append("isDetailed").toStringKey()]: false
                    },
                    headers: {Accept: "application/json"},
                });
                const topicUnitList: TagUnits = TagUnits.fromHash(topicResponse.data);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.get("/api/v1/countries", {
                    params: {"page": 1, "itemsPerPage": 10000, "isDetailed": false},
                    headers: {Accept: "application/json"},
                });
                const countryUnitList: TagUnits = TagUnits.fromHash(countryResponse.data);
                setCountryTagsOptions(countryUnitList);

                const articleResponse = await axios.get("/api/v1/articles/" + articleId, {
                    headers: {Accept: "application/json"},
                });
                const findArticleOutput: FindArticleOutput = articleResponse.data;
                setArticle(findArticleOutput.article);
                console.log(JSON.stringify(articleResponse.data));
                console.log(JSON.stringify(article));

            } catch (error) {
                console.error("error : ", error);
            }
        };
        console.log("pageArticle");
        fetchTagsOptions();
    }, [refresh]);

    if (article) {
        return <div className="main-area-frame">
            <div className="main-area">
                <PublicArticleBody
                    postKey={RootElementId.valueOf("article")}
                    article={article}
                    topicTagOptions={topicTagsOptions}
                    countryTagOptions={countryTagsOptions}
                    reRender={reRender}
                />
            </div>
        </div>
    } else {
        return <div>Loading...</div>
    }
};
