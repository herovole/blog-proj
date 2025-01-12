import React, {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleSummaryList} from "../../../domain/articlelist/articleSummaryList";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";

type PublicArticleHeadlinesProps = {
    articles: ArticleSummaryList;
    directoryToIndividualPage: string;
    topicTagList: TagUnits;
    countryTagList: TagUnits;
    reRender:boolean;
}


//articles : ArticleSummaryList
export const PublicArticleHeadlines: React.FC<PublicArticleHeadlinesProps> = ({articles, directoryToIndividualPage, topicTagList, countryTagList, reRender}) => {

    const LETTERS_PICKUP = 30;
    const navigate = useNavigate();

    useEffect(() => {
        console.log("DAT " + JSON.stringify(articles));
        console.log("AAAAAA " + JSON.stringify(topicTagList));
        console.log("AAAAAA " + JSON.stringify(topicTagList.getTagUnit("1")));
        console.log("AAAAAA " + JSON.stringify(countryTagList));
        console.log("AAAAAA " + JSON.stringify(countryTagList.getTagUnit("ad")));
    }, [reRender]);


    const goToIndividualPage = (articleId: number) => {
        console.log("link invoked")
        navigate(directoryToIndividualPage + "/" + articleId);
    }

    return (
        <div className="headlines-section"><span>Headlines</span>
            {
                articles.getElements().map((article: ArticleSummary) => (
                    <div key="" className="headline-item">
                        <button className="headline-clickable" onClick={() => goToIndividualPage(article.articleId)}>
                            {article.title ? article.title.slice(0, LETTERS_PICKUP) : ""}
                        </button>
                        <br/>
                        <span className="small-memo">
                            <span>{article.countUserComments} Comments</span>
                            <TagButtons tagUnitList={topicTagList} tagIds={article.topicTags} searchBaseUrl={directoryToIndividualPage} />
                            <TagButtons tagUnitList={countryTagList} tagIds={article.countries} searchBaseUrl={directoryToIndividualPage} />
                        </span>
                    </div>
                ))}
            <input type="hidden" value={reRender ? 1 : 0}/>
        </div>
    );
};
