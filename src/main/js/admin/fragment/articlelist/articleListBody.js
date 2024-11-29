import React, { useState, useRef } from 'react';
import axios from 'axios';
import DatePicker from 'react-datepicker';
import Pagination from 'react-bootstrap/Pagination';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesOutput} from "./searchArticlesOutput"

export const ArticleListBody = ({postKey}) => {
    const LOCAL_DIR = "c://home/git/blog-proj/app_utility/images/";
    const LETTERS_PICKUP = 50;
    const PAGES_VISIBLE = 15;
    const [output, setOutput] = useState(SearchArticlesOutput.empty());
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [currentPage, setCurrentPage] = useState(1);
    const [keywords, setKeywords] = useState("");
    const [dateFrom, setDateFrom] = useState('');
    const [dateTo, setDateTo] = useState('');

    const refItemsPerPage = useRef(null);
    const refPage = useRef(null);
    const refKeywords = useRef(null);
    const refDateFrom = useRef(null);
    const refDateTo = useRef(null);


    const handleItemsPerPage = (e) => {
        setItemsPerPage(e.target.value);

    }

    const handleKeywords = (e) => {
        setKeywords(e.target.value);
    }
    const handleDateFrom = (date) => {
        setDateFrom(date);
    }
    const handleDateTo = (date) => {
        setDateTo(date);
    }

    const handlePageChanged = (page) => {
        // Trigger the form submission manually
        refPage.current.value = page;
        const form = document.querySelector('form');
        if (form) form.dispatchEvent(new Event('submit', { cancelable: true }));
    }

    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevent page reload
        console.log(event);

        // submitter exists if normal submit button is invoked.
        // submitter is absent if pager is invoked.
        if(event.nativeEvent.submitter) {
            refItemsPerPage.current.value = itemsPerPage;
            refPage.current.value = 1;
            refKeywords.current.value = keywords;
            refDateFrom.current.value = dateFrom;
            refDateTo.current.value = dateTo;
        }

        const formData = new FormData(event.target);
        const getParams = new URLSearchParams(formData);

        console.log(getParams);

        try {
            const response = await axios.get("/api/v1/articles", {
                params: getParams,
                headers: { Accept: "application/json" },
            });

            console.log(response);
            //console.log(JSON.parse(response));

            //const parsedHash = JSON.parse(jsonString);
            //const searchArticlesOutput = await response.data;
            const searchArticlesOutput = SearchArticlesOutput.fromHash(response.data);
            setOutput(searchArticlesOutput);
            console.log("total articles : " + output.totalArticles);
            console.log("articles : " + JSON.stringify(output.articleSummaries));

        } catch (error) {
            console.error('Error submitting form:', error);
        }

    }

    const totalPages = () => {
        return output.totalArticles % itemsPerPage == 0
            ? output.totalArticles / itemsPerPage
            : Math.floor(output.totalArticles / itemsPerPage) + 1;
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <input type="hidden"
                    ref={refItemsPerPage}
                    name={postKey.append("itemsPerPage").toStringKey()}
                />
                <input type="hidden"
                    ref={refPage}
                    name={postKey.append("page").toStringKey()}
                />
                <input type="hidden"
                    ref={refKeywords}
                    name={postKey.append("keywords").toStringKey()}
                />
                <input type="hidden"
                    ref={refDateFrom}
                    name={postKey.append("dateFrom").toStringKey()}
                />
                <input type="hidden"
                    ref={refDateTo}
                    name={postKey.append("dateTo").toStringKey()}
                />
                <button type="submit" value="action1">
                    Submit Action 1
                </button>

                <Pagination size="sm" className="pull-right">
                    <Pagination.First onClick={() => handlePageChanged(1)} />
                    <Pagination.Prev onClick={() => handlePageChanged(currentPage - 1 > 0 ? currentPage - 1 : 1)} />
                    {(() => {
                        return Array.from({ length: totalPages() }, (_, i) => (
                            <Pagination.Item
                                key={i + 1}
                                active={i + 1 === currentPage}
                                onClick={() => handlePageChanged(i + 1)}
                            >
                                {i + 1}
                            </Pagination.Item>
                        ));
                    })()}
                    <Pagination.Next onClick={() => handlePageChanged(currentPage < totalPages() ? currentPage + 1 : totalPages())} />
                    <Pagination.Last onClick={() => handlePageChanged(totalPages())} />
                </Pagination>
            </form>
            <p>ページ当たり表示数 :
                <input
                  type="number"
                  max="100"
                  min="10"
                  step="5"
                  class="editable-text-activated scale-large-flexible"
                  placeholder="items per page"
                  onChange={handleItemsPerPage}
                  value={itemsPerPage}
                />
            </p>
            <p>キーワード :
                <input
                  class="editable-text-activated scale-large-flexible"
                  placeholder="space-separated search keywords"
                  onChange={handleKeywords}
                  value={keywords}
                />
            </p>
            <p>日付範囲 :
                <DatePicker
                  dateFormat="yyyy/MM/dd"
                  placeholderText="date from"
                  onChange={handleDateFrom}
                  selected={dateFrom}
                />
                ～
                <DatePicker
                  dateFormat="yyyy/MM/dd"
                  placeholderText="date to"
                  onChange={handleDateTo}
                  selected={dateTo}
                />
            </p>
            {output.articleSummaries.list.map((article) => (
                <div class="flex-container">
                    <img class="image-sample" src={LOCAL_DIR + article.image} />
                    <div>
                        <p>ID : {article.articleId}</p>
                        <p>Is Published : {article.isPublished}</p>
                        <p>Title : {article.title ? article.title.slice(0, LETTERS_PICKUP) : ""}</p>
                        <p>Text : {article.text ? article.text.slice(0, LETTERS_PICKUP) : ""}</p>
                        <p>Editors : {article.editors}</p>
                        <p>Source URL : {article.sourceUrl}</p>
                        <p>Source Title : {article.sourceTitle}</p>
                        <p>Source Date : {article.sourceDate}</p>
                        <p>Source Comments Number : {article.countOriginalComments}</p>
                        <p>User Comments Number : {article.countUserComments}</p>
                        <p>Insert Date : {article.registrationTimestamp}</p>
                        <p>Update Date : {article.latestEditTimestamp}</p>
                    </div>
                </div>
            ))}
        </>
    );
};
