import React from "react";
import {LivedoorRss} from "./livedoorRss";
import {ExtLinks} from "./extLinks";
import {GadgetBase} from "./gadgetBase";


export const GadgetsRight: React.FC = () => {
    return (
        <div>
            <GadgetBase subjectName="RSS" isOpenByDefault={true}>
                <LivedoorRss/>
            </GadgetBase>
            <div className="gadget-float-bottom-right">
                <GadgetBase subjectName="登録リンク" isOpenByDefault={true}>
                    <ExtLinks/>
                </GadgetBase>
            </div>
        </div>
    );
}