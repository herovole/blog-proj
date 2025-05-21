import React from 'react';
import DatePicker from 'react-datepicker';
import Select from 'react-select';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesInput} from "../../../service/articles/searchArticlesInput";
import {SearchArticlesOutput} from "../../../service/articles/searchArticlesOutput";
import {HeadlinesMode, PublicArticleHeadlines} from "../../../public/fragment/articlelist/publicArticleHeadlines";
import {ArticleService} from "../../../service/articles/articleService";
import {AppPagination} from "../appPagenation";
import {useSearchParams} from "react-router-dom";
import {ResourceManagement} from "../../../service/resourceManagement";
import {TagUnits} from "../atomic/tagselectingform/tagUnits";
import {OrderBy} from "../../../domain/articlelist/orderBy";

type ArticleListBodyProps = {
    isForAdmin?: boolean;
    mode?: HeadlinesMode;
    hasSearchMenu: boolean;
    directoryToIndividualPage: string;
}


export const ArticleListBody: React.FC<ArticleListBodyProps> = ({
                                                                    isForAdmin = false,
                                                                    mode = HeadlinesMode.LINE,
                                                                    hasSearchMenu,
                                                                    directoryToIndividualPage,
                                                                }) => {
    const [searchParams, setSearchParams] = useSearchParams();
    const [inputFixed, setInputFixed] = React.useState(SearchArticlesInput.byDefaultOrGetParams(searchParams, isForAdmin));
    const articleService: ArticleService = new ArticleService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

    const [itemsPerPage, setItemsPerPage] = React.useState<number>(inputFixed.itemsPerPage);
    const [page, setPage] = React.useState<number>(inputFixed.page);
    const [keywords, setKeywords] = React.useState<string>(inputFixed.keywords);
    const [topicTag, setTopicTag] = React.useState<string | null>(inputFixed.topicTag);
    const [countryTag, setCountryTag] = React.useState<string | null>(inputFixed.countryTag);
    const [dateFrom, setDateFrom] = React.useState<Date | null>(inputFixed.dateFrom);
    const [dateTo, setDateTo] = React.useState<Date | null>(inputFixed.dateTo);
    const [isPublished, setIsPublished] = React.useState<boolean>(inputFixed.isPublished);
    const MIN_DATE: Date = new Date("2025-01-01");
    const MAX_DATE: Date = new Date();

    const [orderBy, setOrderBy] = React.useState<OrderBy | null>(inputFixed.orderBy);
    const [output, setOutput] = React.useState(SearchArticlesOutput.empty());
    const [refresh, setRefresh] = React.useState(false);

    React.useEffect(() => {
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
        load().then();
        ResourceManagement.getInstance().clearReferredTopicTags();
        ResourceManagement.getInstance().clearReferredCountryTags();
    }, []);

    const load = async (): Promise<void> => {
        await loadArticles(inputFixed);
    };

    const handleItemsPerPage = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        setItemsPerPage(parseInt(e.currentTarget.value));
    }
    const handleKeywords = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        setKeywords(e.currentTarget.value);
    }
    const handleTopicTag = (theSelectedTag: { value: string, label: string }) => {
        setTopicTag(theSelectedTag.value);
    }
    const clearTopicTag = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setTopicTag(null);
    }
    const handleCountryTag = (theSelectedTag: { value: string, label: string }) => {
        setCountryTag(theSelectedTag.value);
    }
    const clearCountryTag = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        setCountryTag(null);
    }
    const handleDateFrom = (date: Date | null) => {
        setDateFrom(date);
    }
    const handleDateTo = (date: Date | null) => {
        setDateTo(date);
    }
    const handleIsPublished = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        setIsPublished(e.currentTarget.checked);
    }
    const handleOrderBy = (selectedOrder: { value: string, label: string }) => {
        setOrderBy(OrderBy.fromSignature(selectedOrder.value));
    }
    const handlePageChanged = async (page: number) => {
        // Trigger the form submission manually
        const input: SearchArticlesInput = inputFixed.appendPage(page);
        setPage(page);
        setInputFixed(input);
        await loadArticles(input);
    }

    const loadArticles = async (input: SearchArticlesInput): Promise<void> => {
        const output: SearchArticlesOutput = await articleService.searchArticles(input);
        if (output.isSuccessful()) {
            if (input.isDefault()) {
                setSearchParams({});
            } else {
                setSearchParams(input.toPayloadHash());
            }
            setOutput(output);
        } else {
            console.error(output.getMessage("article list retrieval"));
        }
        setRefresh(r => !r);
    }


    const handleSearch = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent page reload

        setPage(1);
        const input: SearchArticlesInput = new SearchArticlesInput(
            itemsPerPage,
            1,
            isPublished,
            dateFrom,
            dateTo,
            keywords,
            topicTag,
            countryTag,
            orderBy,
            isForAdmin
        );
        setInputFixed(input);
        await loadArticles(input);
    }

    const totalPages = () => {
        return output.getLength() % inputFixed.itemsPerPage === 0
            ? Math.max(output.getLength() / inputFixed.itemsPerPage, 1)
            : Math.floor(output.getLength() / inputFixed.itemsPerPage) + 1;
    }

    const htmlPagination = <AppPagination
        handlePageChanged={handlePageChanged}
        currentPage={page}
        totalPages={totalPages()}
    />

    const htmlIsPublished = isForAdmin ?
        <p>公開状態：
            <input
                className="admin-editable-text-activated"
                type="checkbox"
                checked={isPublished}
                onChange={handleIsPublished}
            />
        </p> : <></>

    const htmlOrderBy = isForAdmin ?
        <p>ソート順：
            <div style={{width: "240px"}}>
                <Select
                    isMulti={false}
                    options={OrderBy.getTagOptionsJapaneseAdmin()}
                    value={orderBy ? OrderBy.getTagOptionsJapaneseSelected(orderBy.getSignature()) : null}
                    onChange={handleOrderBy}
                    placeholder="Order By"
                />
            </div>
        </p> : <></>


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
                            value={itemsPerPage}
                        />
                    </p>
                    <p>キーワード :
                        <input
                            className="input-keywords"
                            placeholder="スペース区切り、最大3"
                            onChange={handleKeywords}
                            value={keywords}
                        />
                    </p>
                    <table>
                        <tbody>
                        <tr>
                            <td>
                                話題分類 :
                            </td>
                            <td style={{width: "240px"}}>
                                <Select
                                    isMulti={false}
                                    options={topicTagsOptions.getTagOptionsJapanese()}
                                    value={topicTag ? topicTagsOptions.getTagOptionsJapaneseSelected([topicTag]) : null}
                                    onChange={handleTopicTag}
                                    placeholder="topic"
                                />
                            </td>
                            <td>
                                <button type="button" onClick={clearTopicTag}>クリア</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                国 :
                            </td>
                            <td style={{width: "240px"}}>
                                <Select
                                    isMulti={false}
                                    options={countryTagsOptions.getTagOptionsJapanese()}
                                    value={countryTag ? countryTagsOptions.getTagOptionsJapaneseSelected([countryTag]) : null}
                                    onChange={handleCountryTag}
                                    placeholder="country"
                                />
                            </td>
                            <td>
                                <button type="button" onClick={clearCountryTag}>クリア</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <p>日付範囲 :
                        <DatePicker
                            dateFormat="yyyy/MM/dd"
                            placeholderText="date from"
                            onChange={handleDateFrom}
                            selected={dateFrom}
                            minDate={MIN_DATE}
                            maxDate={MAX_DATE}
                            showMonthYearDropdown/>
                        ～
                        <DatePicker
                            dateFormat="yyyy/MM/dd"
                            placeholderText="date to"
                            onChange={handleDateTo}
                            selected={dateTo}
                            minDate={MIN_DATE}
                            maxDate={MAX_DATE}
                            showMonthYearDropdown/>
                    </p>
                    <br/>
                    {htmlIsPublished}
                    {htmlOrderBy}
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
                />
            </>
        )
            ;
    }
};
