import React from "react";
import {ResourceManagement} from "../../service/resourceManagement"; // 追加

export const HeroBanner: React.FC = () => {

    const [resourcePrefix, setResourcePrefix] = React.useState<string | null>(null);

    React.useEffect(() => {
        ResourceManagement.getInstance().systemImagePrefixWithSlash().then(setResourcePrefix);
    }, []);

    return (
        <div className="hero-banner-base">
            <img src={resourcePrefix + "hero_banner.jpg"}
                 alt={"banner"}/>
            <div className="hero-banner-title-base">
                <div className="hero-banner-title-jp">
                    変則電信アーカイブ
                </div>
                <div className="hero-banner-title-en">
                    Irregular Telegraph Archives
                </div>
            </div>
        </div>

    )
}
