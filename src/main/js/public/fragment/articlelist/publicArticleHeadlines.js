import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
//articles : ArticleSummaryList
export const PublicArticleHeadlines = ({ articles, directoryToIndividualPage }) => {
    const LETTERS_PICKUP = 30;
    const navigate = useNavigate();
    const goToIndividualPage = (articleId) => {
        console.log("link invoked");
        navigate(directoryToIndividualPage + "/" + articleId);
    };
    return (_jsxs("div", { className: "headlines-section", children: [_jsx("span", { children: "Headlines" }), articles.list.map((article) => (_jsxs("div", { className: "headline-item", children: [_jsx("button", { className: "article-title", onClick: () => goToIndividualPage(article.articleId), children: article.getSlicedTitle(LETTERS_PICKUP) }), _jsx("br", {}), _jsxs("span", { className: "small-memo", children: [_jsxs("span", { children: [article.countUserComments, " Comments"] }), article.topicTags.map((topic) => (_jsx("span", { className: "clickable-topic-tag", children: topic }, ""))), article.countries.map((country) => (_jsx("span", { className: "clickable-topic-tag", children: country }, "")))] })] }, "")))] }));
};
