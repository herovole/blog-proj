import React, {useState} from "react";
import {Helmet, HelmetProvider} from "react-helmet-async";
import {ResourceManagement} from "../../service/resourceManagement";

type MetaInfoType = {
    tabTitle: string;
    description?: string;
    keywords?: string;
    image?: string;
    hasSystemImage?: boolean;
}

export const MetaInfo: React.FC<MetaInfoType> = ({
                                                     tabTitle,
                                                     description = "",
                                                     keywords = "",
                                                     image = null,
                                                     hasSystemImage = false
                                                 }) => {

    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    React.useEffect(() => {
        const resourceManagement = ResourceManagement.getInstance();
        if (hasSystemImage) {
            resourceManagement.systemImagePrefixWithSlash().then(setResourcePrefix);
        } else {
            resourceManagement.articlesImagePrefixWithSlash().then(setResourcePrefix);
        }
    }, []);

    if (resourcePrefix) {
        const metaImageTag = image ? <meta property="og:image" content={resourcePrefix + image}/> : <></>
        return <HelmetProvider>
            <Helmet>
                <title>{tabTitle + " | " + ResourceManagement.getInstance().getSiteNameJp()}</title>
                <meta name="description" content={description}/>
                <meta name="keywords" content={keywords}/>

                <meta property="og:title" content={tabTitle}/>
                <meta property="og:description" content={description}/>
                {metaImageTag}
                <meta property="og:url" content={window.location.origin + window.location.pathname}/>
                <meta name="twitter:card" content="summary"/>
            </Helmet>
        </HelmetProvider>
    } else {
        return <div>loading...</div>
    }
}
