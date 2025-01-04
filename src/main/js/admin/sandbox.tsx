import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {DateSelectingForm} from './fragment/atomic/dateSelectingForm';
import {TagSelectingForm} from './fragment/atomic/tagselectingform/tagSelectingForm';
import {ElementId} from '../domain/elementId';
import {ImageSelectingModal} from './fragment/image/imageSelectingModal';
import {TagButton} from "./fragment/atomic/tagselectingform/tagButton";
import {ArticleSummaryList} from "../domain/articlelist/articleSummaryList";
import {PublicArticleHeadlines} from "../public/fragment/articlelist/publicArticleHeadlines";
import {TagUnitList} from "./fragment/atomic/tagselectingform/tagUnitList";
import {SearchArticlesOutput} from "../domain/articlelist/searchArticlesOutput";

console.log("sandbox.js");

export const Sandbox: React.FC = () => {
    const [topicTagsOptions, setTopicTagsOptions] = useState(TagUnitList.empty());
    const [countryTagsOptions, setCountryTagsOptions] = useState(TagUnitList.empty());
    const [articles, setArticles] = useState(ArticleSummaryList.empty());

    useEffect(() => {
        const fetchTagsOptions = async () => {
            console.log("---------fetchTagsOptions---------");
            try {

                const topicResponse = await axios.get("/api/v1/topicTags", {
                    params: {"topicTags.page": 1, "topicTags.itemsPerPage": 10000, "topicTags.isDetailed": false},
                    headers: {Accept: "application/json"},
                });
                const topicUnitList: TagUnitList = TagUnitList.fromHash(topicResponse.data);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.get("/api/v1/countries", {
                    params: {"page": 1, "itemsPerPage": 10000, "isDetailed": false},
                    headers: {Accept: "application/json"},
                });
                const countryUnitList: TagUnitList = TagUnitList.fromHash(countryResponse.data);
                setCountryTagsOptions(countryUnitList);

                const articleSummariesResponse = await axios.get("/api/v1/articles", {
                    params: {"articleList.itemsPerPage": 10000, "articleList.page": 1,},
                    headers: {Accept: "application/json"},
                });
                const searchArticlesOutput: SearchArticlesOutput = SearchArticlesOutput.fromHash(articleSummariesResponse.data);
                setArticles(searchArticlesOutput.articleSummaries);

            } catch (error) {
                console.error("error : tagSelectingForm", error);
            }
        };
        fetchTagsOptions();
        console.log("TagUnit:", topicTagsOptions.getTagUnit("1"));
    }, []);

    const selectedTags = ["af"];

    return <div>
        <div>
            test1: imageSelectingModal
            <ImageSelectingModal
                postKey={new ElementId("test")}
            />
        </div>
        <div>
            test2: Headlines
            <PublicArticleHeadlines
                articles={articles}
                directoryToIndividualPage={""}
                topicTagList={topicTagsOptions}
                countryTagList={countryTagsOptions}
            />
        </div>
        <div>
            test3:
            <DateSelectingForm
                postKey={new ElementId("dsf")}
            />
        </div>
        <div>
            test4:
            <TagSelectingForm
                postKey={new ElementId("tsf")}
                candidates={countryTagsOptions || TagUnitList.empty()}
                selectedTagIds={selectedTags}
            />
        </div>
        <div>
            test5:
            <TagButton
                unit={topicTagsOptions.getTagUnit("1")}
                searchBaseUrl={"ccc"}
                searchKey={"aaa"}
            />

        </div>

    </div>

}