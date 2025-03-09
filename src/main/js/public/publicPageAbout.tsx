import React, {useEffect, useState} from "react";
import {marked} from "marked";
import {PublicBasicLayout} from "./fragment/publicBasicLayout";
import {MetaInfo} from "./fragment/metaInfo"; // Import the Markdown parser

export const PublicPageAbout = () => {
    const [content, setContent] = useState<string>("");

    useEffect(() => {
        fetch("/content/about.md")
            .then((res) => res.text())
            .then((text) => setContent(marked(text) as string))  // Convert Markdown to HTML
    }, []);

    return <>
        <MetaInfo
            tabTitle={"サイト概要"}
        />
        <PublicBasicLayout>
            <div dangerouslySetInnerHTML={{__html: content}}/>
        </PublicBasicLayout>
    </>
};