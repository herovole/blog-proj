import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import DatePicker from 'react-datepicker';
import Pagination from 'react-bootstrap/Pagination';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesInput} from "./searchArticlesInput"
import {SearchArticlesOutput} from "./searchArticlesOutput"

export const TagListBody = ({formKey}) => {
    const ITEMS_PER_PAGE = 2;
    const [inputCached, setInputCached] = useState(SearchArticlesInput.byDefault(formKey));
    const [inputFixed, setInputFixed] = useState(SearchArticlesInput.byDefault(formKey));
    const [output, setOutput] = useState(SearchArticlesOutput.empty());

    const handlePageChanged = (page) => {
        // Trigger the form submission manually
        inputCached.appendPage(page);
        const form = document.querySelector('form');
        if (form) form.dispatchEvent(new Event('submit', { cancelable: true }));
    }

    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevent page reload

        // submitter exists if normal submit button is invoked.
        // submitter is absent if pager is invoked.
        var input;
        if(event.nativeEvent.submitter) {
            input = inputCached;
            setInputFixed({...inputCached});
        } else {
            input = inputFixed.appendPage(inputCached.page);
            setInputCached({...inputFixed});
        }
        console.log("after");
        console.log("input:", JSON.stringify(input));
        console.log("toUrlSearchParams:", input.toUrlSearchParams().toString());

        try {
            const response = await axios.get("/api/v1/articles", {
                params: input.toUrlSearchParams(),
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
        return output.totalArticles % inputFixed.itemsPerPage == 0
            ? output.totalArticles / inputFixed.itemsPerPage
            : Math.floor(output.totalArticles / inputFixed.itemsPerPage) + 1;
    }

    const navigate = useNavigate();
    const goToIndividualPage = (articleId) => {
        console.log("link invoked")
        navigate(directoryToIndividualPage + "/" + articleId);
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <button type="submit" value="action1">
                    Search
                </button>

                <Pagination size="sm" className="pull-right">
                    <Pagination.First onClick={() => handlePageChanged(1)} />
                    <Pagination.Prev onClick={() => handlePageChanged(inputCached.page - 1 > 0 ? inputCached.page - 1 : 1)} />
                    {Array.from({ length: totalPages() }, (_, i) => (
                        <Pagination.Item
                            key={i + 1}
                            active={i + 1 === inputCached.page}
                            onClick={() => handlePageChanged(i + 1)}
                        >
                            {i + 1}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next onClick={() => handlePageChanged(inputCached.page < totalPages() ? inputCached.page + 1 : totalPages())} />
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
                  value={inputCached.itemsPerPage}
                />
            </p>
            <p>キーワード :
                <input
                  class="editable-text-activated scale-large-flexible"
                  placeholder="space-separated search keywords"
                  onChange={handleKeywords}
                  value={inputCached.keywords}
                />
            </p>
            <p>日付範囲 :
                <DatePicker
                  dateFormat="yyyy/MM/dd"
                  placeholderText="date from"
                  onChange={handleDateFrom}
                  selected={inputCached.dateFrom}
                />
                ～
                <DatePicker
                  dateFormat="yyyy/MM/dd"
                  placeholderText="date to"
                  onChange={handleDateTo}
                  selected={inputCached.dateTo}
                />
            </p>
            {output.articleSummaries.list.map((article) => (
                <div class="flex-container">
                    <img class="image-sample" src={LOCAL_DIR + article.image} />
                    <div>
                        <button onClick={() => goToIndividualPage(article.articleId)}>Open</button>
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
