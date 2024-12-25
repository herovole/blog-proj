import React, {useEffect, useState} from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnitList} from "./fragment/atomic/tagselectingform/tagUnitList";
import {ArticleEditingPageBody} from "./fragment/articleeditingpage/articleEditingPageBody";
import {ElementId} from "../domain/elementId";
import {AdminHeader} from "./adminHeader";

export const PageNewArticle = () => {
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);

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
            } catch (error) {
                console.error("error : ", error);
            }
        };
        fetchTagsOptions();

    }, []);

    return (
        <div>
            <AdminHeader/>
            <ArticleEditingPageBody
                postKey={new ElementId("articleEditingPage")}
                content={null}
                topicTagOptions={topicTagsOptions}
                countryTagOptions={countryTagsOptions}
            />
        </div>
    );
};
