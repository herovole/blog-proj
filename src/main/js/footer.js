import React from "react";
import {Link} from "react-router-dom";
import {ResourceManagement} from "./service/resourceManagement"; // 追加

export const Footer = () => {
    return (<div>
            <ul>
                <li>
                    <Link to="/">ホーム</Link> {/*aタグじゃないよ*/}
                </li>
                <li>
                    <Link to="/articles">検索</Link>
                </li>
                <li>
                    <Link to="/about">サイト概要</Link>
                </li>
                <li>
                    <Link to="/admin">管理者</Link>
                </li>
            </ul>
            <br/>
            <p>© 2025 {ResourceManagement.getInstance().getSiteNameJp()}. All rights reserved, except for materials licensed under open-source or
                third-party licenses. See <Link to="About">this page</Link> for details..</p>
        </div>
    )
}