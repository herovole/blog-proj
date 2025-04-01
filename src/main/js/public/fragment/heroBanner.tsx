import React from "react";
import {ResourceManagement} from "../../service/resourceManagement"; // 追加

export const HeroBanner: React.FC = () => {

    const [resourcePrefix, setResourcePrefix] = React.useState<string | null>(null);

    React.useEffect(() => {
        ResourceManagement.getInstance().systemImagePrefixWithSlash().then(setResourcePrefix);
    }, []);

    if (resourcePrefix) {
        return (
            <div className="hero-banner-base">
                <img className="hero-banner-image"
                     src={resourcePrefix + "hero_banner.jpg"}
                     alt={"banner"}/>
                <div className="hero-banner-title-base">
                    <div className="hero-banner-title-jp">
                        輸入雑暇
                    </div>
                    <div className="hero-banner-title-en">
                        Imported Leisure
                    </div>
                </div>
            </div>
        );
    } else {
        return <div>loading...</div>;
    }
}
