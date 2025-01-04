import React from 'react';
import {useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleSummaryList} from "../../../domain/articlelist/articleSummaryList";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import {TagUnitList} from "../../../admin/fragment/atomic/tagselectingform/tagUnitList";

type PublicArticleHeadlinesProps = {
    articles: ArticleSummaryList;
    directoryToIndividualPage: string;
    topicTagList: TagUnitList;
    countryTagList: TagUnitList;
}


//articles : ArticleSummaryList
export const PublicArticleHeadlines: React.FC<PublicArticleHeadlinesProps> = ({articles, directoryToIndividualPage, topicTagList, countryTagList}) => {

    const LETTERS_PICKUP = 30;
    const navigate = useNavigate();
    const goToIndividualPage = (articleId: number) => {
        console.log("link invoked")
        navigate(directoryToIndividualPage + "/" + articleId);
    }

    return (
        <div className="headlines-section"><span>Headlines</span>
            {
                articles.list.map((article: ArticleSummary) => (
                    <div key="" className="headline-item">
                        <button className="headline-clickable" onClick={() => goToIndividualPage(article.articleId)}>
                            {article.getSlicedTitle(LETTERS_PICKUP)}
                        </button>
                        <br/>
                        <span className="small-memo">
                            <span>{article.countUserComments} Comments</span>
                            <TagButtons tagUnitList={topicTagList} tagIds={article.topicTags} searchBaseUrl={directoryToIndividualPage} />
                            <TagButtons tagUnitList={countryTagList} tagIds={article.countries} searchBaseUrl={directoryToIndividualPage} />
                        </span>
                    </div>
                ))}
        </div>
    );
};
