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
                        <a href={"./rss.xml"}>
                            <img src={xmlPrefix + "Feed-icon.svg"} style={{cursor: 'pointer', width: 64, height: 64}}
                                 alt="rss"/>
                            <span>RSS 2.0</span>
                        </a>
                        <br/>
                        <a href={"./rss10.xml"}>
                            <img src={xmlPrefix + "Feed-icon.svg"} style={{cursor: 'pointer', width: 64, height: 64}}
                                 alt="rss"/>
                            <span>RSS 1.0</span>
                        </a>
                        <br/>
                        <h2>X(旧Twitter)</h2>
                        <a href={"https://x.com/at_archives_com"}>
                            <img src={xmlPrefix + "X_logo_2023.svg"} style={{cursor: 'pointer', width: 64, height: 64}}
                                 alt="x"/>
                        </a>
                    </div>
                    <div>
                        <h2>参加リンク</h2>
                        <a href="https://blog.with2.net/link/?id=2128848&cid=4281" title="海外の反応ランキング"
                           target="_blank"><img alt="海外の反応ランキング" width="110" height="31"
                                                src="https://blog.with2.net/img/banner/c/banner_1/br_c_4281_1.gif"/></a>
                        <a href="https://blog.with2.net/link/?id=2128848&follow" title="人気ブログランキングでフォロー"
                           target="_blank"><img alt="人気ブログランキングでフォロー" width="172" height="20"
                                                src="https://blog.with2.net/banner/follow/2128848?t=m"/></a>
                        <br/><span
                        style={{fontSize: "0.8em"}}>もし当ブログを気に入っていただけましたら、上記リンク(左側)よりランキング閲覧にお力添えいただけると大変励みになります。</span>
                    </div>
                    <div dangerouslySetInnerHTML={{__html: content}}/>
                </div>
            </PublicBasicLayout>
        </>
    } else {
        return <div>loading...</div>;
    }
};