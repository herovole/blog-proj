import React from "react";
import {NavLink} from "react-router-dom";
import {HeroBanner} from "./heroBanner";
import {LivedoorRss} from "./livedoorRss";

export const PublicHeader: React.FC = () => {

    return (
        <div className="header-frame">
            <HeroBanner/>
            <LivedoorRss/>
            <div className="header-alignment">
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/" end>トップページ</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/articles" end>検索</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/about" end>サイト情報</NavLink>
            </div>
        </div>
    )
}