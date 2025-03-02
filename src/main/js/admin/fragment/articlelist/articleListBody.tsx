import React, {useEffect} from 'react';
import DatePicker from 'react-datepicker';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesInput} from "../../../service/articles/searchArticlesInput";
import {SearchArticlesOutput} from "../../../service/articles/searchArticlesOutput";
import {HeadlinesMode, PublicArticleHeadlines} from "../../../public/fragment/articlelist/publicArticleHeadlines";
import {ArticleService} from "../../../service/articles/articleService";
import {AppPagination} from "../appPagenation";

type ArticleListBodyProps = {
    mode?: HeadlinesMode;
    hasSearchMenu: boolean;
    directoryToIndividualPage: string;
}


export const ArticleListBody: React.FC<ArticleListBodyProps> = ({
                                                                    mode = HeadlinesMode.SMALL,
                                                                    hasSearchMenu,
                                                                    directoryToIndividualPage,
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
        load().then();
    }, []);

    const handleItemsPerPage = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputCached(inputCached.appendItemsPerPage(parseInt(e.currentTarget.value)));
    }
    const handleKeywords = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputCached(inputCached.appendKeywords(e.currentTarget.value));
    }
    const handleDateFrom = (date: Date | null) => {
        setInputCached(inputCached.appendDateFrom(date));
    }
    const handleDateTo = (date: Date | null) => {
        setInputCached(inputCached.appendDateTo(date));
    }

    const loadArticles = async (input: SearchArticlesInput): Promise<void> => {
        const output: SearchArticlesOutput = await articleService.searchArticles(input);
        if (output.isSuccessful()) {
            setOutput(output);
        } else {
            console.error(output.getMessage("article list retrieval"));
        }
        setRefresh(r => !r);
    }

    const handlePageChanged = async (page: number) => {
        // Trigger the form submission manually
        const input: SearchArticlesInput = inputFixed.appendPage(page);
        setInputCached(input);
        setInputFixed(input);
        await loadArticles(input);
    }

    const handleSearch = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent page reload

        const input: SearchArticlesInput = inputCached.appendPage(1);
        setInputCached(input);
        setInputFixed(input);
        await loadArticles(input);
    }

    const totalPages = () => {
        return output.getLength() % inputFixed.itemsPerPage === 0
            ? Math.min(output.getLength() / inputFixed.itemsPerPage, 1)
            : Math.floor(output.getLength() / inputFixed.itemsPerPage) + 1;
    }

    const htmlPagination = <AppPagination
        handlePageChanged={handlePageChanged}
        currentPage={inputCached.page}
        totalPages={totalPages()}
    />

    const htmlSearch =
        <div className="comment-modal-exterior">
            <div className="comment-modal-interior">
                <form onSubmit={handleSearch}>
                    <button type="submit" value="action1">
                        検索
                    </button>
                    <p>ページ当たり表示数 :
                        <input
                            className="input-items-per-page"
                            type="number"
                            max="100"
                            min="10"
                            step="5"
                            placeholder="items per page"
                            onChange={handleItemsPerPage}
                            value={inputCached.itemsPerPage}
                        />
                    </p>

                    <br/>
                    <p>キーワード :
                        <input
                            className="input-keywords"
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

                </form>
            </div>
        </div>

    if (hasSearchMenu) {
        return (
            <>
                {htmlSearch}
                {htmlPagination}
                <PublicArticleHeadlines
                    mode={mode}
                    articles={output.getArticleSummaryList()}
                    directoryToIndividualPage={directoryToIndividualPage}
                    reRender={refresh}
                />
            </>
        )
            ;
    } else {
        return (
            <>
                {htmlPagination}
                <PublicArticleHeadlines
                    mode={mode}
                    articles={output.getArticleSummaryList()}
                    directoryToIndividualPage={directoryToIndividualPage}
                    reRender={refresh}
                />
            </>
        )
            ;
    }
};
