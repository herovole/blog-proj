import React, {useEffect} from 'react';
import DatePicker from 'react-datepicker';
import Pagination from 'react-bootstrap/Pagination';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesInput} from "../../../service/articles/searchArticlesInput";
import {SearchArticlesOutput} from "../../../service/articles/searchArticlesOutput";
import {HeadlinesMode, PublicArticleHeadlines} from "./publicArticleHeadlines";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ArticleService} from "../../../service/articles/articleService";

type PublicArticleListBodyProps = {
    hasSearchMenu: boolean;
    directoryToIndividualPage: string;
    topicTagsOptions: TagUnits;
    countryTagsOptions: TagUnits;
}


export const PublicArticleListBody: React.FC<PublicArticleListBodyProps> = ({
                                                                                hasSearchMenu,
                                                                                directoryToIndividualPage,
                                                                                topicTagsOptions,
                                                                                countryTagsOptions
                                                                            }) => {
    const articleService: ArticleService = new ArticleService();
    const MIN_DATE: Date = new Date("2025-01-01");
    const MAX_DATE: Date = new Date();
    const [inputCached, setInputCached] = React.useState(SearchArticlesInput.byDefault());
    const [inputFixed, setInputFixed] = React.useState(SearchArticlesInput.byDefault());
    const [output, setOutput] = React.useState(SearchArticlesOutput.empty());
    const [refresh, setRefresh] = React.useState(false);

    const load = async (): Promise<void> => {
        await loadArticles(inputFixed);
    };
    useEffect(() => {
        load().then(r => {
            console.log(r);
        });
    }, []);

    const handleItemsPerPage = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputCached(inputCached.appendItemsPerPage(parseInt(e.target.value)));
    }
    const handleKeywords = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputCached(inputCached.appendKeywords(e.target.value));
    }
    const handleDateFrom = (date: Date | null) => {
        setInputCached(inputCached.appendDateFrom(date));
    }
    const handleDateTo = (date: Date | null) => {
        setInputCached(inputCached.appendDateTo(date));
    }

    const loadArticles = async (input: SearchArticlesInput): Promise<void> => {
        console.log("input " + JSON.stringify(input.toPayloadHash()));
        const output: SearchArticlesOutput = await articleService.searchArticles(input);
        setOutput(output);
        console.log("total articles : " + output.getLength());
        console.log("articles : " + JSON.stringify(output.getArticleSummaryList()));
        setRefresh(r => !r);
    }

    const handlePageChanged = async (page: number) => {
        // Trigger the form submission manually
        const input: SearchArticlesInput = inputFixed.appendPage(page);
        setInputCached(input);
        setInputFixed(input);
        await loadArticles(input);
    }

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent page reload

        const input: SearchArticlesInput = inputCached.appendPage(1);
        setInputCached(input);
        setInputFixed(input);
        await loadArticles(input);
    }

    const totalPages = () => {
        return output.getLength() % inputFixed.itemsPerPage === 0
            ? output.getLength() / inputFixed.itemsPerPage
            : Math.floor(output.getLength() / inputFixed.itemsPerPage) + 1;
    }

    const htmlPagenation =
        <Pagination size="sm" className="pull-right">
            <Pagination.First onClick={() => handlePageChanged(1)}/>
            <Pagination.Prev
                onClick={() => handlePageChanged(inputCached.page - 1 > 0 ? inputCached.page - 1 : 1)}/>
            {Array.from({length: totalPages()}, (_, i) => (
                <Pagination.Item
                    key={i + 1}
                    active={i + 1 === inputCached.page}
                    onClick={() => handlePageChanged(i + 1)}
                >
                    {i + 1}
                </Pagination.Item>
            ))}
            <Pagination.Next
                onClick={() => handlePageChanged(inputCached.page < totalPages() ? inputCached.page + 1 : totalPages())}/>
            <Pagination.Last onClick={() => handlePageChanged(totalPages())}/>
        </Pagination>


    const htmlSearch =
        <form onSubmit={handleSubmit}>
            <button type="submit" value="action1">
                検索
            </button>
            <p>ページ当たり表示数 :
                <input
                    type="number"
                    max="100"
                    min="10"
                    step="5"
                    className="editable-text-activated scale-span"
                    placeholder="items per page"
                    onChange={handleItemsPerPage}
                    value={inputCached.itemsPerPage}
                />
            </p>

            <br/>
            <p>キーワード :
                <input
                    className="editable-text-activated scale-span"
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
                    minDate={MIN_DATE}
                    maxDate={MAX_DATE}
                    showMonthYearDropdown/>
                ～
                <DatePicker
                    dateFormat="yyyy/MM/dd"
                    placeholderText="date to"
                    onChange={handleDateTo}
                    selected={inputCached.dateTo}
                    minDate={MIN_DATE}
                    maxDate={MAX_DATE}
                    showMonthYearDropdown/>
            </p>
            <PublicArticleHeadlines
                mode={HeadlinesMode.SMALL}
                articles={output.getArticleSummaryList()}
                directoryToIndividualPage={directoryToIndividualPage}
                topicTagList={topicTagsOptions}
                countryTagList={countryTagsOptions}
                reRender={refresh}
            />

        </form>

    if (hasSearchMenu) {
        return (
            <>
                {htmlPagenation}
                {htmlSearch}
            </>
        )
            ;
    } else {
        return (
            <>
                {htmlPagenation}
            </>
        );
    }
};
