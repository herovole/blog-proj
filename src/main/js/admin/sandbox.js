import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {DateSelectingForm} from './fragment/atomic/dateSelectingForm';
import {TagSelectingForm} from './fragment/atomic/tagselectingform/tagSelectingForm';
import {TagUnitList} from './fragment/atomic/tagselectingform/tagUnitList';
import {ArticleEditingPageBody} from './fragment/articleeditingpage/articleEditingPageBody';
import {ElementId} from '../domain/elementId';
import {CommentUnitList} from '../domain/commentUnitList';
import {ImageSelectingModal} from './fragment/image/imageSelectingModal';
import {ArticleListBody} from './fragment/articlelist/articleListBody';
import {SourceCommentUnit} from "../domain/sourceCommentUnit";
import {Article} from "../domain/article";

console.log("sandbox.js");

const rootId = new ElementId("root");
const first = rootId.append("1stLevel");
const second = first.append("2ndLevel");
const another = rootId.append("another");

console.log(rootId.toStringKey());
console.log(first.toStringKey());
console.log(second.toStringKey());
console.log(another.toStringKey());

const comment1 = new SourceCommentUnit(
    1,
    "This is comment1.",
    "us",
    false,
    []
);
const comment2 = new SourceCommentUnit(
    2,
    "This is comment2.",
    "us",
    false,
    []
);
const comment3 = new SourceCommentUnit(
    3,
    "This is comment3.",
    "us",
    false,
    []
);
const comment4 = new SourceCommentUnit(
    4,
    "This is comment4.",
    "us",
    false,
    [1]
);
const comment5 = new SourceCommentUnit(
    5,
    "This is comment5.",
    "us",
    false,
    [4]
);
const comment6 = new SourceCommentUnit(
    6,
    "This is comment6.",
    "us",
    false,
    [2, 3]
);
const comment7 = new SourceCommentUnit(
    7,
    "This is comment7.",
    "us",
    false,
    []
);
const comment8 = new SourceCommentUnit(
    8,
    "This is comment8.",
    "us",
    false,
    [4]
);


const testCommentUnitList = new CommentUnitList(
    [comment1, comment2, comment3, comment4, comment5, comment6, comment7, comment8]
);
/*
const testCommentUnitList = new CommentUnitList(
    [comment1,comment2,comment3,comment4]
);
*/
const testArticle = new Article(
    14,
    "",
    "Title123",
    "Lorem Ipsum",
    "http://localhost",
    "source title123",
    "2024/11/06",
    true,
    [1, 2, 3],
    testCommentUnitList,
    new CommentUnitList()
);


export const Sandbox = () => {
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {

                const topicResponse = await axios.get("/api/v1/topicTags", {
                    params: {"page": 1, "itemsPerPage": 10000, "isDetailed": false},
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
                console.error("error : tagSelectingForm", error);
            }
        };
        fetchTagsOptions();
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
            test2: ArticleListBody
            <ArticleListBody formKey={new ElementId("articleList")}/>
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
                candidates={countryTagsOptions || new TagUnitList()}
                selectedTagIds={selectedTags}
            />
        </div>
        <div>
            test5: (none)

        </div>
        <div>
            test6:
            <ArticleEditingPageBody
                postKey={new ElementId("articleEditingPage")}
                content={testArticle}
                topicTagOptions={topicTagsOptions}
                countryTagOptions={countryTagsOptions}
            />
        </div>

    </div>

}