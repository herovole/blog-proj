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

console.log("sandbox.js");

var rootId = new ElementId("root");
var first = rootId.append("1stLevel");
var second = first.append("2ndLevel");
var another = rootId.append("another");

console.log(rootId.toStringKey());
console.log(first.toStringKey());
console.log(second.toStringKey());
console.log(another.toStringKey());

const testArticleEditingPageBody;
Comment


export const Sandbox = () =>{
    const [tagsOptions, setTagsOptions] = useState(null);

    var postKey = new ElementId("sandbox");

    useEffect(() => {
        const fetchTagsOptions = async () => {
            try {
                var response = await axios.post("/b/api/tags", {});
                console.log("response data /" + response.data);
                var tagUnitList = TagUnitList.fromJsonStringList(response.data);
                console.log("tagsOptions / " + tagUnitList);
                setTagsOptions(tagUnitList);
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
            <ArticleEditingPageBody postKey={postKey.append("aep")}/>
        </div>
    </div>

}