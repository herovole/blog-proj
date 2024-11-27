import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { BasicClass } from './basicclass';
import { DateSelectingForm } from './fragment/atomic/dateSelectingForm';
import { TagSelectingForm } from './fragment/atomic/tagselectingform/tagSelectingForm';
import { TagUnitList } from './fragment/atomic/tagselectingform/tagUnitList';
import { ArticleEditingPageBody } from './fragment/articleeditingpage/articleEditingPageBody';
import { ElementId } from '../domain/elementId';
import { CommentUnit } from './fragment/articleeditingpage/commenteditor/commentUnit';
import { CommentUnitList } from './fragment/articleeditingpage/commenteditor/commentUnitList';
import { Article } from './fragment/articleeditingpage/article';
import { ImageSelectingModal } from './fragment/image/imageSelectingModal';
import { ArticleListBody } from './fragment/articlelist/articleListBody';

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
    "us",
    false,
    []
);
var comment2 = new CommentUnit(
    2,
    "This is comment2.",
    "us",
    false,
    []
);
var comment3 = new CommentUnit(
    3,
    "This is comment3.",
    "us",
    false,
    []
);
var comment4 = new CommentUnit(
    4,
    "This is comment4.",
    "us",
    false,
    [1]
);
var comment5 = new CommentUnit(
    5,
    "This is comment5.",
    "us",
    false,
    [4]
);
var comment6 = new CommentUnit(
    6,
    "This is comment6.",
    "us",
    false,
    [2,3]
);
var comment7 = new CommentUnit(
    7,
    "This is comment7.",
    "us",
    false,
    []
);
var comment8 = new CommentUnit(
    8,
    "This is comment8.",
    "us",
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
    "Title123",
    "Lorem Ipsum",
    "http://localhost",
    "source title123",
    "2024/11/06",
    true,
    [1,2,3],
    testCommentUnitList,
    new CommentUnitList()
);


export const Sandbox = () =>{
    const [topicTagsOptions, setTopicTagsOptions] = useState(null);
    const [countryTagsOptions, setCountryTagsOptions] = useState(null);

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {
                const topicResponse = await axios.get("/api/v1/topicTags", {});
                console.log("response data /" + topicResponse.data);
                const topicUnitList = TagUnitList.fromJsonStringList(topicResponse.data);
                console.log("topicOptions / " + topicUnitList);
                setTopicTagsOptions(topicUnitList);

                const countryResponse = await axios.get("/api/v1/countries", {})
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

    var selectedTags = ["af"];

    return <div>
        <div>
            test1: imageSelectingModal
            <ImageSelectingModal
                postKey={new ElementId("test")}
            />
        </div>
        <div>
            test2: ArticleListBody
            <ArticleListBody postKey={new ElementId("articleList")}/>
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
              candidates={countryTagsOptions ? countryTagsOptions : new TagUnitList()}
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