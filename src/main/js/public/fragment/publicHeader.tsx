import React from "react";
import {NavLink} from "react-router-dom";
import {ResourceManagement} from "../../service/resourceManagement"; // 追加

export const PublicHeader: React.FC = () => {

    const [resourcePrefix, setResourcePrefix] = React.useState<string | null>(null);

    React.useEffect(() => {
        ResourceManagement.getInstance().systemImagePrefixWithSlash().then(setResourcePrefix);
    }, [resourcePrefix]);

    return (
        <div className="header-frame">
            <div className="hero-banner-base">
                <img src={resourcePrefix + "hero_banner.jpg"}
                     alt={"Being registered"}/>
            </div>
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