import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CountrySelectBox } from './fragment/atomic/countrySelectBox';
import { BasicClass } from './basicclass';
import { DateSelectingForm } from './fragment/atomic/plain/dateSelectingForm';
import { TagSelectingForm } from './fragment/atomic/plain/tagSelectingForm/tagSelectingForm';
import { TagUnitList } from './fragment/atomic/plain/tagSelectingForm/tagUnitList';
import { ImageSelectingForm } from './fragment/atomic/plain/imageSelectingForm';
import { ArticleEditingPageBody } from './fragment/articleEditingPage/articleEditingPageBody';
import { ElementId } from '../domain/elementId';
import { CommentUnit } from './fragment/articleEditingPage/commentEditor/commentUnit';
import { CommentUnitList } from './fragment/articleEditingPage/commentEditor/commentUnitList';
import { Article } from './fragment/articleEditingPage/article';

console.log("sandbox.js");

var rootId = new ElementId("root");
var first = rootId.append("1stLevel");
var second = first.append("2ndLevel");
var another = rootId.append("another");

console.log(rootId.toStringKey());
console.log(first.toStringKey());
console.log(second.toStringKey());
console.log(another.toStringKey());

var comment1 = new CommentUnit(
    1,
    "This is comment1.",
    "United States",
    false,
    []
);
var comment2 = new CommentUnit(
    2,
    "This is comment2.",
    "United States",
    false,
    []
);
var comment3 = new CommentUnit(
    3,
    "This is comment3.",
    "United States",
    false,
    []
);
var comment4 = new CommentUnit(
    4,
    "This is comment4.",
    "United States",
    false,
    [1]
);
var comment5 = new CommentUnit(
    5,
    "This is comment5.",
    "United States",
    false,
    [4]
);
var comment6 = new CommentUnit(
    6,
    "This is comment6.",
    "United States",
    false,
    [2,3]
);
var comment7 = new CommentUnit(
    7,
    "This is comment6.",
    "United States",
    false,
    []
);
var comment8 = new CommentUnit(
    8,
    "This is comment8.",
    "United States",
    false,
    [4]
);


var testCommentUnitList = new CommentUnitList(
    [comment1,comment2,comment3,comment4,comment5,comment6,comment7,comment8]
);
/*
var testCommentUnitList = new CommentUnitList(
    [comment1,comment2,comment3,comment4]
);
*/
var testArticle = new Article(
    14,
    "",
    "Lorem Ipsum",
    "2024/11/09",
    true,
    [1,2,3],
    testCommentUnitList,
    new CommentUnitList()
);


export const Sandbox = () =>{
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);

    var postKey = new ElementId("sandbox");

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {
                const topicResponse = await axios.post("/b/api/tags", {});
                console.log("response data /" + topicResponse.data);
                const topicUnitList = TagUnitList.fromJsonStringList(topicResponse.data);
                console.log("topicOptions / " + topicUnitList);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.post("b/api/countries", {})
                console.log("response data /" + countryResponse.data);
                const countryUnitList = TagUnitList.fromJsonStringList(countryResponse.data);
                console.log("countryOptions / " + countryUnitList);
                setCountryTagsOptions(countryUnitList);

            } catch (error) {
                console.error("error : tagSelectingForm", error);
            }
        };
        fetchTagsOptions();
    }, []);

    var selectedTags = [1,4];

    return <div>
        <div>
            test1:
            <CountrySelectBox postKey={postKey.append("csb")}>United Kingdom</CountrySelectBox>
        </div>
        <div>
            test3:
            <DateSelectingForm
              postKey={postKey.append("dsf")}
            />
        </div>
        <div>
            test4:
            <TagSelectingForm
              postKey={postKey.append("tsf")}
              candidates={tagsOptions ? tagsOptions : new TagUnitList()}
              selectedTagIds={selectedTags}
            />
        </div>
        <div>
            test5:
            <ImageSelectingForm
              postKey={postKey.append("isf")}
            />
        </div>
        <div>
            test6:
            <ArticleEditingPageBody
                postKey={postKey.append("articleEditingPage")}
                content={testArticle}
                topicTagOptions={topicTagsOptions}
                countryTagOptions={countryTagsOptions}
                />
        </div>
    </div>

}