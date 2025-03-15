import React, {useEffect, useState} from "react";
import {marked} from "marked";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {MetaInfo} from "./fragment/metaInfo";
import {ResourceManagement} from "../service/resourceManagement"; // Import the Markdown parser

export const PublicPageAbout = () => {
    const [content, setContent] = useState<string>("");
    const [xmlPrefix, setXmlPrefix] = useState<string | null>(null);

    useEffect(() => {
        fetch("/content/about.md")
            .then((res) => res.text())
            .then((text) => setContent(marked(text) as string))  // Convert Markdown to HTML
        ResourceManagement.getInstance().systemImagePrefixWithSlash().then(setXmlPrefix);
    }, []);

    if (content && xmlPrefix) {
        return <>
            <MetaInfo
                tabTitle={"サイト概要"}
            />
            <PublicBasicLayout>
                <div>
                    <div>
                        <h2>RSS</h2>
                        <a href={xmlPrefix + "rss.xml"}>RSS 2.0</a>
                        <br/>
                    </div>
                    <div dangerouslySetInnerHTML={{__html: content}}/>
                </div>
            </PublicBasicLayout>
        </>
    } else {
        return <div>loading...</div>;
    }
};