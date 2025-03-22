import React from "react";
import {SiteService} from "../../../service/site/siteService";
import {GenerateRss2Input} from "../../../service/site/generateRss2Input";
import {BasicApiResult} from "../../../domain/basicApiResult";

export const AdminRss2 = () => {

    const siteService: SiteService = new SiteService();
    const [messageOrdinary, setMessageOrdinary] = React.useState<string>("");
    const [messageWarning, setMessageWarning] = React.useState<string>("");


    const generateRss2 = async () => {
        const input: GenerateRss2Input = new GenerateRss2Input();
        const output: BasicApiResult = await siteService.generateRss2(input);
        if (output.isSuccessful()) {
            setMessageOrdinary(output.getMessage("Rss2 Generation"));
        } else {
            setMessageWarning(output.getMessage("Rss2 Generation"));
        }
    }


    return (
        <div>
            <button type="button" onClick={generateRss2}>Generate rss2.xml</button>
            <br/>
            <span className="comment-form-process">{messageOrdinary}</span>
            <span className="comment-form-err">{messageWarning}</span>
        </div>
    )
}
