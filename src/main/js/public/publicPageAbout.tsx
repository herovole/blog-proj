import React, {useEffect, useState} from "react";
import {marked} from "marked";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {MetaInfo} from "./fragment/metaInfo";
import {ResourceManagement} from "../service/resourceManagement"; // Import the Markdown parser

export const PublicPageAbout = () => {
    const [content, setContent] = useState<string>("");
    const [xmlPrefix, setXmlPrefix] = useState<string | null>(null);

    useEffect(() => {
        window.scrollTo({top: 0, behavior: "auto"});
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
                        <a href={xmlPrefix + "rss.xml"}>
                            <img src={xmlPrefix + "Feed-icon.svg"} style={{cursor: 'pointer'}} alt="rss"/>
                            <span>RSS 2.0</span>
                        </a>
                        <br/>
                        <a href={xmlPrefix + "rss10.xml"}>
                            <img src={xmlPrefix + "Feed-icon.svg"} style={{cursor: 'pointer'}} alt="rss"/>
                            <span>RSS 1.0</span>
                        </a>
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