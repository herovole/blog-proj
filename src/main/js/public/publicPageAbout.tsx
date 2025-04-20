import React, {useEffect, useState} from "react";
import {marked} from "marked";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {MetaInfo} from "./fragment/metaInfo";
import {ResourceManagement} from "../service/resourceManagement";
import {PublicAboutLinks} from "./fragment/publicAboutLinksProps"; // Import the Markdown parser

export const PublicPageAbout = () => {
    const [content, setContent] = useState<string>("");
    const [systemImgPrefix, setSystemImgPrefix] = useState<string | null>(null);

    useEffect(() => {
        window.scrollTo({top: 0, behavior: "auto"});
        fetch("/content/about.md")
            .then((res) => res.text())
            .then((text) => setContent(marked(text) as string))  // Convert Markdown to HTML
        ResourceManagement.getInstance().systemImagePrefixWithSlash().then(setSystemImgPrefix);
    }, []);

    if (content && systemImgPrefix) {
        return <>
            <MetaInfo
                tabTitle={"サイト情報"}
            />
            <PublicBasicLayout>
                <PublicAboutLinks iconPrefix={systemImgPrefix}/>
                <div dangerouslySetInnerHTML={{__html: content}}/>
            </PublicBasicLayout>
        </>
    } else {
        return <div>loading...</div>;
    }
};