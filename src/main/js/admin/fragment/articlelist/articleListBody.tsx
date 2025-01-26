import React, {useEffect} from 'react';
import DatePicker from 'react-datepicker';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesInput} from "../../../service/articles/searchArticlesInput";
import {SearchArticlesOutput} from "../../../service/articles/searchArticlesOutput";
import {HeadlinesMode, PublicArticleHeadlines} from "../../../public/fragment/articlelist/publicArticleHeadlines";
import {TagUnits} from "../atomic/tagselectingform/tagUnits";
import {ArticleService} from "../../../service/articles/articleService";
import {AppPagination} from "../appPagenation";

type ArticleListBodyProps = {
    hasSearchMenu: boolean;
    directoryToIndividualPage: string;
    topicTagsOptions: TagUnits;
    countryTagsOptions: TagUnits;
}


export const ArticleListBody: React.FC<ArticleListBodyProps> = ({
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
        if (output.isSuccessful()) {
            setOutput(output);
        } else {
            console.error(output.getMessage("article list retrieval"));
        }
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
            ? Math.min(output.getLength() / inputFixed.itemsPerPage, 1)
            : Math.floor(output.getLength() / inputFixed.itemsPerPage) + 1;
    }

    const htmlPagination = <AppPagination
        handlePageChanged={handlePageChanged}
        currentPage={inputCached.page}
        totalPages={totalPages()}
    />

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
                {htmlPagination}
                {htmlSearch}
            </>
        )
            ;
    } else {
        return (
            <>
                {htmlPagination}
            </>
        );
    }
};
