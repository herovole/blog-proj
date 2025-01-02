import React from 'react';
import {useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleSummaryList} from "../../../domain/articlelist/articleSummaryList";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";

type PublicArticleHeadlinesProps = {
    articles: ArticleSummaryList;
    directoryToIndividualPage: string;
}


//articles : ArticleSummaryList
export const PublicArticleHeadlines: React.FC<PublicArticleHeadlinesProps> = ({articles, directoryToIndividualPage}) => {

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
                        <button className="article-title" onClick={() => goToIndividualPage(article.articleId)}>
                            {article.getSlicedTitle(LETTERS_PICKUP)}
                        </button>
                        <br/>
                        <span className="small-memo">
                            <span>{article.countUserComments} Comments</span>
                            {article.topicTags.map((topic) => (
                                <span key="" className="clickable-topic-tag">{topic}</span>))}
                            {article.countries.map((country) => (
                                <span key="" className="clickable-topic-tag">{country}</span>))}
                        </span>
                    </div>
                ))}
        </div>
    );
};
