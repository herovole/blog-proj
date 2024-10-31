import React from 'react';
import { CountrySelectBox } from './atomic/countrySelectBox';
import { EditOriginalPostUnit } from './editOriginalPostUnit';
import { BasicClass } from './basicclass';

console.log("newarticle.js");
export const NewArticle = () =>{
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
        </div>
    )
}