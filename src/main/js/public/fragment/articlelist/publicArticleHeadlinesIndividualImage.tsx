import React, {useState} from "react";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {Link} from "react-router-dom";
import {ResourceManagement} from "../../../service/resourceManagement";

type PublicArticleHeadlinesIndividualImageProps = {
    article: ArticleSummary;
    directoryToIndividualPage: string;
}

export const PublicArticleHeadlinesIndividualImage: React.FC<PublicArticleHeadlinesIndividualImageProps> = ({
                                                                                                                article,
                                                                                                                directoryToIndividualPage,
                                                                                                            }
) => {
    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);

    React.useEffect(() => {
        ResourceManagement.getInstance().articlesImagePrefixWithSlash().then(setResourcePrefix);
    }, []);


    if (!resourcePrefix) {
        return <div>loading...</div>;
    } else {
        return (
            <div className="article-image-small-base">
                <Link className="headline-clickable-small" to={directoryToIndividualPage + "/" + article.articleId}>
                    <br/>
                    <img className="article-image-small" src={resourcePrefix + article.imageName}
                         alt={article.imageName}/>
                    <div className="article-image-title-base">
                        <div className="article-image-title">
                            {article.title ? article.title : ""}
                        </div>
                    </div>
                </Link>
            </div>
        );
    }
}