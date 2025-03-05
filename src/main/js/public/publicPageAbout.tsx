import { useEffect, useState } from "react";

export const PublicPageAbout = () => {
    const [content, setContent] = useState("");

    useEffect(() => {
        fetch("/content/about.md")
            .then((res) => res.text())
            .then((text) => setContent(text));
    }, []);

    return <div dangerouslySetInnerHTML={{ __html: content }} />;
};
