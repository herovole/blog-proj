import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Article} from "../domain/article";
import {ElementId} from "../domain/elementId/elementId";
import {PublicArticleBody} from "./fragment/article/publicArticleBody";
import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";
import {RootElementId} from "../domain/elementId/rootElementId";

export const PublicPageArticleView = () => {
    const {articleId} = useParams();
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);
    const [article, setArticle] = useState(null);

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {
                const topicTagsKey = RootElementId.valueOf("topicTags");
                const topicResponse = await axios.get("/api/v1/topicTags", {
                    params: {
                        [topicTagsKey.append("page").toStringKey()]: 1,
                        [topicTagsKey.append("itemsPerPage").toStringKey()]: 10000,
                        [topicTagsKey.append("isDetailed").toStringKey()]: false
                    },
                    headers: {Accept: "application/json"},
                });
                const topicUnitList = TagUnits.fromHash(topicResponse.data);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.get("/api/v1/countries", {
                    params: {"page": 1, "itemsPerPage": 10000, "isDetailed": false},
                    headers: {Accept: "application/json"},
                });
                const countryUnitList = TagUnits.fromHash(countryResponse.data);
                setCountryTagsOptions(countryUnitList);

                const articleResponse = await axios.get("/api/v1/articles/" + articleId, {
                    headers: {Accept: "application/json"},
                });
                const article = Article.fromHash(articleResponse.data);
                setArticle(article);
                console.log(JSON.stringify(articleResponse.data));
                console.log(JSON.stringify(article));

            } catch (error) {
                console.error("error : ", error);
            }
        };
        console.log("pageArticle");
        fetchTagsOptions();
    }, []);

    if (article) {
        return <PublicArticleBody
            postKey={RootElementId.valueOf("article")}
            article={article}
            topicTagOptions={topicTagsOptions}
            countryTagOptions={countryTagsOptions}
        />
    } else {
        return <div>Loading...</div>
    }
};
