import React from "react";
import {GadgetBase} from "./gadgetBase";
import {ArticleListGadgetBody} from "../../../admin/fragment/articlelist/articleListGadgetBody";
import {OrderBy, OrderByEnum} from "../../../domain/articlelist/orderBy";
import {ResourceManagement} from "../../../service/resourceManagement";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";

type GadgetsLeftProps = {
    directoryToIndividualPage: string;
}

export const GadgetsLeft: React.FC<GadgetsLeftProps> = ({directoryToIndividualPage}) => {

    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [topicTag, setTopicTag] = React.useState<string | null>("");
    const [countryTag, setCountryTag] = React.useState<string | null>("");

    React.useEffect(() => {
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
        ResourceManagement.getInstance().getRandomReferredTopicTag().then(setTopicTag);
        ResourceManagement.getInstance().getRandomReferredCountryTag().then(setCountryTag);
    }, []);

    if (topicTag != "" && countryTag != "") {
        ResourceManagement.getInstance().undefineReferredTopicTags();
        ResourceManagement.getInstance().undefineReferredCountryTags();
        return (
            <div>
                <GadgetBase subjectName={topicTag ?
                    topicTagsOptions.getJapaneseNameByIdForDisplay(topicTag) + "カテゴリの記事" :
                    "注目の記事"}
                            isOpenByDefault={true} width="200px">
                    <ArticleListGadgetBody
                        directoryToIndividualPage={directoryToIndividualPage}
                        articleNumber={4}
                        topicTag={topicTag}
                        countryTag={null}
                        orderBy={new OrderBy(OrderByEnum.LATEST_COMMENT_TIMESTAMP)}
                    />
                </GadgetBase>
                <GadgetBase subjectName={countryTag ?
                    countryTagsOptions.getJapaneseNameByIdForDisplay(countryTag) + "の記事" :
                    "注目の記事"}
                            isOpenByDefault={true} width="200px">
                    <ArticleListGadgetBody
                        directoryToIndividualPage={directoryToIndividualPage}
                        articleNumber={4}
                        topicTag={null}
                        countryTag={countryTag}
                        orderBy={new OrderBy(OrderByEnum.LATEST_COMMENT_TIMESTAMP)}
                    />
                </GadgetBase>
            </div>
        );

    }
}