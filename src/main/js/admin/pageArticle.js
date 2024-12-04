import React, { useState, useRef, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnitList} from "./fragment/atomic/tagselectingform/tagUnitList";
import {ArticleEditingPageBody} from "./fragment/articleeditingpage/articleEditingPageBody";
import {Article} from "./fragment/articleeditingpage/article";
import {ElementId} from "../domain/elementId";

export const PageArticle = () => {
    const { articleId } = useParams();
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);
    const [article, setArticle] = useState(null);

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {
                const topicResponse = await axios.get("/api/v1/topicTags", {});
                const topicUnitList = TagUnitList.fromJsonStringList(topicResponse.data);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.get("/api/v1/countries", {})
                const countryUnitList = TagUnitList.fromJsonStringList(countryResponse.data);
                setCountryTagsOptions(countryUnitList);

                const articleResponse = await axios.get("/api/v1/articles/" + articleId, {
                    headers: { Accept: "application/json" },
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

    if(article) {
        return <ArticleEditingPageBody
          postKey={new ElementId("articleEditingPage")}
          content={article}
          topicTagOptions={topicTagsOptions}
          countryTagOptions={countryTagsOptions}
        />
    } else {
        return <div>Loading...</div>
    }
};
