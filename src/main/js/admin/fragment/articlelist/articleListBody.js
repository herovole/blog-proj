import React, { useState, useRef } from 'react';
import axios from 'axios';
import DatePicker from 'react-datepicker';
import Pagination from 'react-bootstrap/Pagination';
import 'bootstrap/dist/css/bootstrap.min.css';

export const ArticleListBody = ({postKey}) => {
    const LETTERS_PICKUP = 50;
    const PAGES_VISIBLE = 15;
    const [articles, setArticles] = useState([]);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [currentPage, setCurrentPage] = useState(1);
    const [keywords, setKeywords] = useState("");
    const [dateFrom, setDateFrom] = useState('');
    const [dateTo, setDateTo] = useState('');
    const [totalRecordNumber, setTotalRecordNumber] = useState(100);

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

            const searchArticlesOutput = await response.data;
            setTotalRecordNumber(searchArticlesOutput.totalArticles);
            console.log("total articles : " + searchArticlesOutput.totalArticles);

        } catch (error) {
            console.error('Error submitting form:', error);
        }

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
                    <Pagination.Prev onClick={() => handlePageChanged(currentPage - 1)} />
                    {(() => {
                        const totalPages =
                            totalRecordNumber % itemsPerPage === 0
                            ? totalRecordNumber / itemsPerPage
                            : Math.floor(totalRecordNumber / itemsPerPage) + 1;
                        return Array.from({ length: totalPages }, (_, i) => (
                            <Pagination.Item
                                key={i + 1}
                                active={i + 1 === currentPage}
                                onClick={() => handlePageChanged(i + 1)}
                            >
                                {i + 1}
                            </Pagination.Item>
                        ));
                    })()}
                    <Pagination.Next onClick={() => handlePageChanged(currentPage + 1)} />
                    <Pagination.Last onClick={() => handlePageChanged(totalPages)} />
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
            {articles.map((article) => (
                <div key={article.articleId}>
                    <p>ID : {article.articleId}</p>
                    <p>Is Published : {article.isPublished}</p>
                    <p>Title : {article.title.slice(0, LETTERS_PICKUP)}</p>
                    <p>Text : {article.text.slice(0, LETTERS_PICKUP)}</p>
                    <p>Editors : {article.editors}</p>
                    <p>Source URL : {article.sourceUrl}</p>
                    <p>Source Title : {article.sourceTitle}</p>
                    <p>Source Date : {article.sourceDate}</p>
                    <p>Source Comments Number : {article.originalComments.length}</p>
                    <p>User Comments Number : {article.userComments.length}</p>
                    <p>Insert Date : </p>
                    <p>Update Date : </p>
                </div>
            ))}
        </>
    );
};
