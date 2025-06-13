import React from "react";
import {LivedoorRss, RssMode} from "./livedoorRss";
import {ExtLinks} from "./extLinks";
import {GadgetBase} from "./gadgetBase";


export const GadgetsRight: React.FC = () => {
    return (
        <div>
            <GadgetBase subjectName="RSS" isOpenByDefault={true} width="320px">
                <LivedoorRss rssMode={RssMode.OVERSEAS}/>
            </GadgetBase>
            <GadgetBase subjectName="RSS" isOpenByDefault={true} width="320px">
                <LivedoorRss rssMode={RssMode.MISC}/>
            </GadgetBase>
            <div className="gadget-float-bottom-right">
                <GadgetBase subjectName="登録リンク" isOpenByDefault={true} width="256px">
                    <ExtLinks/>
                </GadgetBase>
            </div>
        </div>
    );
}