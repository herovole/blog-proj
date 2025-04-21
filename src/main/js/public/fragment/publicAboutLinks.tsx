import React from "react";
import {Link} from "react-router-dom";

type PublicAboutLinksProps = {
    iconPrefix: string;
}


export const PublicAboutLinks: React.FC<PublicAboutLinksProps> = ({iconPrefix}) => {

    return (
        <div>
            <h2>RSS</h2>
            <div className="indent">
                <a href={"./rss.xml"}>
                    <img src={iconPrefix + "Feed-icon.svg"} style={{cursor: 'pointer', width: 64, height: 64}}
                         alt="rss"/>
                    <span>RSS 2.0</span>
                </a>
                <br/>
                <a href={"./rss10.xml"}>
                    <img src={iconPrefix + "Feed-icon.svg"} style={{cursor: 'pointer', width: 64, height: 64}}
                         alt="rss"/>
                    <span>RSS 1.0</span>
                </a>
            </div>
            <br/>
            <h2>X(旧Twitter)</h2>
            <div className="indent">
                <a href={"https://x.com/at_archives_com"}>
                    <img src={iconPrefix + "X_logo_2023.svg"} style={{cursor: 'pointer', width: 64, height: 64}}
                         alt="x"/>
                </a>
            </div>
            <br/>
            <div>
                <h2>登録アンテナサイト</h2>
                <div className="indent">
                    <div>
                        <span>人気ブログランキング : </span>
                        <a href="https://blog.with2.net/link/?id=2128848&cid=4281" title="海外の反応ランキング"
                           target="_blank"><img alt="海外の反応ランキング" width="110" height="31"
                                                src="https://blog.with2.net/img/banner/c/banner_1/br_c_4281_1.gif"/></a>
                        <a href="https://blog.with2.net/link/?id=2128848&follow" title="人気ブログランキングでフォロー"
                           target="_blank"><img alt="人気ブログランキングでフォロー" width="172" height="20"
                                                src="https://blog.with2.net/banner/follow/2128848?t=m"/></a>
                    </div>
                </div>
                <br/>
                <h2>申請中アンテナサイト</h2>
                <div className="indent">
                    <p>ヤクテナ : <Link to="https://www.yakutena.com/">https://www.yakutena.com/</Link></p>
                    <p>海外の反応アンテナ.com : <Link to="https://kaihan-antenna.com/">https://kaihan-antenna.com/</Link></p>
                    <p>海外反応研究会 : <Link to="https://kaiken.atna.jp/">https://kaiken.atna.jp/</Link></p>
                </div>
            </div>
        </div>
    );
}