import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CountrySelectBox } from './fragment/atomic/countrySelectBox';
import { BasicClass } from './basicclass';
import { DateSelectingForm } from './fragment/atomic/plain/dateSelectingForm';
import { TagSelectingForm } from './fragment/atomic/plain/tagSelectingForm/tagSelectingForm';
import { TagUnitList } from './fragment/atomic/plain/tagSelectingForm/tagUnitList';
import { ImageSelectingForm } from './fragment/atomic/plain/imageSelectingForm';
import { ArticleEditingPageBody } from './fragment/articleEditingPage/articleEditingPageBody';

console.log("sandbox.js");


export const Sandbox = () =>{
    const [tagsOptions, setTagsOptions] = useState(null);

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
            <CountrySelectBox>United Kingdom</CountrySelectBox>
        </div>
        <div>
            test3:
            <DateSelectingForm
              postKey="dsf"
            />
        </div>
        <div>
            test4:
            <TagSelectingForm
              postKey="tsf"
              candidates={tagsOptions ? tagsOptions : new TagUnitList()}
              selectedTagIds={selectedTags}
            />
        </div>
        <div>
            test5:
            <ImageSelectingForm
              postKey="isf"
            />
        </div>
        <div>
            test6:
            <ArticleEditingPageBody/>
        </div>
    </div>

}