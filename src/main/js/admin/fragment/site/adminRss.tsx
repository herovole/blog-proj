import React from "react";
import {SiteService} from "../../../service/site/siteService";
import {GenerateRssInput} from "../../../service/site/generateRssInput";
import {BasicApiResult} from "../../../domain/basicApiResult";

export const AdminRss = () => {

    const siteService: SiteService = new SiteService();
    const [messageOrdinary20, setMessageOrdinary20] = React.useState<string>("");
    const [messageOrdinary10, setMessageOrdinary10] = React.useState<string>("");
    const [messageOrdinaryFeed, setMessageOrdinaryFeed] = React.useState<string>("");
    const [messageOrdinaryGerman20, setMessageOrdinaryGerman20] = React.useState<string>("");
    const [messageOrdinaryGerman10, setMessageOrdinaryGerman10] = React.useState<string>("");
    const [messageWarning, setMessageWarning] = React.useState<string>("");

    const RSS20 = "rss20";
    const RSS10 = "rss10";
    const RSS20Feed = "feed20";
    const RSS20German = "german20";
    const RSS10German = "german10";


    const generateRss = async () => {
        const input2: GenerateRssInput = new GenerateRssInput(RSS20);
        const output2: BasicApiResult = await siteService.generateRss(input2);
        if (output2.isSuccessful()) {
            setMessageOrdinary20(output2.getMessage("Rss2.0 Generation"));
        } else {
            setMessageWarning(output2.getMessage("Rss2.0 Generation"));
        }

        const inputFeed: GenerateRssInput = new GenerateRssInput(RSS20Feed);
        const outputFeed: BasicApiResult = await siteService.generateRss(inputFeed);
        if (outputFeed.isSuccessful()) {
            setMessageOrdinaryFeed(outputFeed.getMessage("Rss2.0 Generation (Feed)"));
        } else {
            setMessageWarning(outputFeed.getMessage("Rss2.0 Generation (Feed)"));
        }

        const input20German: GenerateRssInput = new GenerateRssInput(RSS20German);
        const output20German: BasicApiResult = await siteService.generateRss(input20German);
        if (output20German.isSuccessful()) {
            setMessageOrdinaryGerman20(output20German.getMessage("Rss2.0 Generation (German)"));
        } else {
            setMessageWarning(output20German.getMessage("Rss2.0 Generation (German)"));
        }

        const input1: GenerateRssInput = new GenerateRssInput(RSS10);
        const output1: BasicApiResult = await siteService.generateRss(input1);
        if (output1.isSuccessful()) {
            setMessageOrdinary10(output1.getMessage("Rss1.0 Generation"));
        } else {
            setMessageWarning(output1.getMessage("Rss1.0 Generation"));
        }

        const input10German: GenerateRssInput = new GenerateRssInput(RSS10German);
        const output10German: BasicApiResult = await siteService.generateRss(input10German);
        if (output10German.isSuccessful()) {
            setMessageOrdinaryGerman10(output10German.getMessage("Rss1.0 Generation (German)"));
        } else {
            setMessageWarning(output10German.getMessage("Rss1.0 Generation (German)"));
        }

    }


    return (
        <div>
            <button type="button" onClick={generateRss}>Generate rss.xml, rss1.0xml</button>
            <p className="comment-form-process">{messageOrdinary20}</p>
            <p className="comment-form-process">{messageOrdinaryFeed}</p>
            <p className="comment-form-process">{messageOrdinaryGerman20}</p>
            <p className="comment-form-process">{messageOrdinary10}</p>
            <p className="comment-form-process">{messageOrdinaryGerman10}</p>
            <span className="comment-form-err">{messageWarning}</span>
        </div>
    )
}
