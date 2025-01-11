import React from "react";
import {NavLink} from "react-router-dom"; // 追加

export const PublicHeader: React.FC = () => {
    return (
        <div className="header-frame">
            <div className="header-alignment">
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/">トップページ</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/">検索</NavLink>
                <NavLink className={({isActive}) => (isActive ? "header-unit-active" : "header-unit")}
                         to="/">サイト情報</NavLink>
            </div>
        </div>
    )
}