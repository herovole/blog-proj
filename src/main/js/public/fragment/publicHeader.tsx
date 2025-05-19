import React from "react";
import {Link, NavLink} from "react-router-dom";
import {HeroBanner} from "./heroBanner";

export const PublicHeader: React.FC = () => {

    return (
        <div className="header-frame">
            <Link to={"/"}><HeroBanner/></Link>
            <div className="centering-container">
                <div className="header-alignment">
                    <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                             to="/" end>ホーム</NavLink>
                    <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                             to="/articles" end>一覧</NavLink>
                    <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                             to="/about" end>サイト情報</NavLink>
                </div>
            </div>
        </div>
    )
}