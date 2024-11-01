import React from 'react';
import { CountrySelectBox } from './atomic/countrySelectBox';
import { EditOriginalPostUnit } from './editOriginalPostUnit';
import { BasicClass } from './basicclass';
import { DateSelectingForm } from './atomic/plain/dateSelectingForm';
import { TagSelectingForm } from './atomic/tagSelectingForm';


console.log("sandbox.js");
export const Sandbox = () =>{
    return (
        <div>
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
                  selectedTagIds="0,2,4"
                />
            </div>
        </div>
    )
}