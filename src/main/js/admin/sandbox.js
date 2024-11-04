import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CountrySelectBox } from './atomic/countrySelectBox';
import { EditOriginalPostUnit } from './editOriginalPostUnit';
import { BasicClass } from './basicclass';
import { DateSelectingForm } from './atomic/plain/dateSelectingForm';
import { TagSelectingForm } from './atomic/plain/tagSelectingForm';
import { TagUnitList } from '../domain/tagUnitList';
import { ImageSelectingForm } from './atomic/plain/imageSelectingForm';

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
            test2:
            <EditOriginalPostUnit
              country="United Kingdom"
              isHidden="false"
            >
              デフォルトコメント
            </EditOriginalPostUnit>
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
    </div>

}