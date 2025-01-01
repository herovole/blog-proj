import React from 'react';
import {useNavigate} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

//articles : ArticleSummaryList
export const PublicArticleListBody = ({articles, directoryToIndividualPage}) => {

    const LETTERS_PICKUP = 30;
    const navigate = useNavigate();
    const goToIndividualPage = (articleId) => {
        console.log("link invoked")
        navigate(directoryToIndividualPage + "/" + articleId);
    }

    return (
        <div className="headlines-section"><span>Headlines</span>
            {
                articles.list.map((article) => (
                    <div key="" className="headline-item">
                        <span className="article-title" onClick={() => goToIndividualPage(article.articleId)}>
                            {article.getSlicedTitle(LETTERS_PICKUP)}
                        </span>
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
