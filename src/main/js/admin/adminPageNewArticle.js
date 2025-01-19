import React, {useEffect, useState} from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TagUnits} from "./fragment/atomic/tagselectingform/tagUnits";
import {AdminArticleBody} from "./fragment/articleeditingpage/adminArticleBody";
import {ElementId} from "../domain/elementId/elementId";
import {AdminHeader} from "./fragment/adminHeader";
import {RootElementId} from "../domain/elementId/rootElementId";

export const AdminPageNewArticle = () => {
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);

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
            } catch (error) {
                console.error("error : ", error);
            }
        };
        fetchTagsOptions();

    }, []);

    return (
        <div>
            <AdminHeader/>
            <AdminArticleBody
                postKey={RootElementId.valueOf("articleEditingPage")}
                content={null}
                topicTagOptions={topicTagsOptions}
                countryTagOptions={countryTagsOptions}
            />
        </div>
    );
};
