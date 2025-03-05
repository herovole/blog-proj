import React, {useEffect, useState} from "react";
import {marked} from "marked"; // Import the Markdown parser

export const PublicPageAbout = () => {
    const [content, setContent] = useState<string>("");

    useEffect(() => {
        fetch("/content/about.md")
            .then((res) => res.text())
            .then((text) => setContent(marked(text) as string))  // Convert Markdown to HTML
    }, []);

    return <div dangerouslySetInnerHTML={{__html: content}}/>;
};