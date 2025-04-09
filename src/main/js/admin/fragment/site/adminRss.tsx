import React from "react";
import {SiteService} from "../../../service/site/siteService";
import {GenerateRssInput} from "../../../service/site/generateRssInput";
import {BasicApiResult} from "../../../domain/basicApiResult";

export const AdminRss = () => {

    const siteService: SiteService = new SiteService();
    const [messageOrdinary20, setMessageOrdinary20] = React.useState<string>("");
    const [messageOrdinary10, setMessageOrdinary10] = React.useState<string>("");
    const [messageWarning, setMessageWarning] = React.useState<string>("");


    const generateRss = async () => {
        const input2: GenerateRssInput = new GenerateRssInput(2);
        const output2: BasicApiResult = await siteService.generateRss(input2);
        if (output2.isSuccessful()) {
            setMessageOrdinary20(output2.getMessage("Rss2.0 Generation"));
        } else {
            setMessageWarning(output2.getMessage("Rss2.0 Generation"));
        }

        const input1: GenerateRssInput = new GenerateRssInput(1);
        const output1: BasicApiResult = await siteService.generateRss(input1);
        if (output1.isSuccessful()) {
            setMessageOrdinary10(output1.getMessage("Rss1.0 Generation"));
        } else {
            setMessageWarning(output1.getMessage("Rss1.0 Generation"));
        }
    }


    return (
        <div>
            <button type="button" onClick={generateRss}>Generate rss.xml, rss1.0xml</button>
            <br/>
            <span className="comment-form-process">{messageOrdinary20}</span>
            <br/>
            <span className="comment-form-process">{messageOrdinary10}</span>
            <br/>
            <span className="comment-form-err">{messageWarning}</span>
        </div>
    )
}
