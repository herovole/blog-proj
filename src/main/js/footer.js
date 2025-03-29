import React from "react";
import {Link} from "react-router-dom";
import {ResourceManagement} from "./service/resourceManagement"; // 追加

export const Footer = () => {
    return (<div className="footer">
            <ul>
                <li>
                    <Link to="/">ホーム</Link>
                </li>
                <li>
                    <Link to="/articles">検索</Link>
                </li>
                <li>
                    <Link to="/about">サイト情報</Link>
                </li>
                <li>
                    <Link to="/admin">管理者</Link>
                </li>
            </ul>
            <br/>
            <p style={{textAlign: "center"}}>© 2025 {ResourceManagement.getInstance().getSiteNameJp()}. </p>
        </div>
    )
}