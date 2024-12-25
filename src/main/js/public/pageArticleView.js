import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Article} from "../domain/article";
import {ElementId} from "../domain/elementId";
import {ArticleViewBody} from "./fragment/article/articleViewBody";
import {TagUnitList} from "../admin/fragment/atomic/tagselectingform/tagUnitList";

export const PageArticleView = () => {
    const {articleId} = useParams();
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);
    const [article, setArticle] = useState(null);

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {
                const topicTagsKey = new ElementId("topicTags");
                const topicResponse = await axios.get("/api/v1/topicTags", {
                    params: {
                        [topicTagsKey.append("page").toStringKey()]: 1,
                        [topicTagsKey.append("itemsPerPage").toStringKey()]: 10000,
                        [topicTagsKey.append("isDetailed").toStringKey()]: false
                    },
                    headers: {Accept: "application/json"},
                });
                const topicUnitList = TagUnitList.fromHash(topicResponse.data);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.get("/api/v1/countries", {
                    params: {"page": 1, "itemsPerPage": 10000, "isDetailed": false},
                    headers: {Accept: "application/json"},
                });
                const countryUnitList = TagUnitList.fromHash(countryResponse.data);
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
        return <ArticleViewBody
            postKey={new ElementId("article")}
            article={article}
            topicTagOptions={topicTagsOptions}
            countryTagOptions={countryTagsOptions}
        />
    } else {
        return <div>Loading...</div>
    }
};
