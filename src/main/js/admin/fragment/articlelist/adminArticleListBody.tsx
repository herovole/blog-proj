import React, {useEffect, useState} from 'react';
import DatePicker from 'react-datepicker';
import Pagination from 'react-bootstrap/Pagination';
import 'bootstrap/dist/css/bootstrap.min.css';
import {SearchArticlesInput} from "../../../service/articles/searchArticlesInput"
import {SearchArticlesOutput} from "../../../service/articles/searchArticlesOutput"
import {TagService} from "../../../service/tags/tagService";
import {ArticleService} from "../../../service/articles/articleService";
import {TagUnits} from "../atomic/tagselectingform/tagUnits";
import {SearchTagsInput} from "../../../service/tags/searchTagsInput";
import {SearchTagsOutput} from "../../../service/tags/searchTagsOutput";
import {PublicArticleHeadlines} from "../../../public/fragment/articlelist/publicArticleHeadlines";

type AdminArticleListBodyProps = {
    directoryToIndividualPage: string;
}

export const AdminArticleListBody: React.FC<AdminArticleListBodyProps> = ({directoryToIndividualPage}) => {
    const LOCAL_DIR = "c://home/git/blog-proj/app_utility/images/";
    const tagService: TagService = new TagService();
    const articleService: ArticleService = new ArticleService();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const MIN_DATE: Date = new Date("2025-01-01");
    const MAX_DATE: Date = new Date();
    const LETTERS_PICKUP = 50;
    //const PAGES_VISIBLE = 15;
    const [inputCached, setInputCached] = useState(SearchArticlesInput.byDefault());
    const [inputFixed, setInputFixed] = useState(SearchArticlesInput.byDefault());
    const [output, setOutput] = useState(SearchArticlesOutput.empty());
    const [refresh, setRefresh] = React.useState(false);

    const load = async (): Promise<void> => {
        try {

            const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const topicOutput: SearchTagsOutput = await tagService.searchTopicTags(topicInput);
            setTopicTagsOptions(topicOutput.getTagUnits());

            const countriesInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const countriesOutput: SearchTagsOutput = await tagService.searchCountries(countriesInput);
            setCountryTagsOptions(countriesOutput.getTagUnits());

            console.log("00000 " + JSON.stringify(topicTagsOptions));
            console.log("00000 " + JSON.stringify(countryTagsOptions));

        } catch (error) {
            console.error("error : ", error);
        }
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
        try {
            const output: SearchArticlesOutput = await articleService.searchArticles(input);
            setOutput(output);
            console.log("total articles : " + output.getLength());
            console.log("articles : " + JSON.stringify(output.getArticleSummaryList()));
            setRefresh(r => !r);
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    }

    const handlePageChanged = (page: number) => {
        // Trigger the form submission manually
        setInputCached(inputFixed);
        inputCached.appendPage(page);
        loadArticles(inputCached).then(r => {
        });
    }

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent page reload

        inputCached.appendPage(1);
        setInputFixed(inputCached);
        loadArticles(inputCached).then(r => {
        });
    }

    const totalPages = () => {
        return output.getLength() % inputFixed.itemsPerPage === 0
            ? output.getLength() / inputFixed.itemsPerPage
            : Math.floor(output.getLength() / inputFixed.itemsPerPage) + 1;
    }


    return (
        <>
            <form onSubmit={handleSubmit}>
                <button type="submit" value="action1">
                    Search
                </button>

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
            </form>
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
                articles={output.getArticleSummaryList()}
                directoryToIndividualPage={directoryToIndividualPage}
                topicTagList={topicTagsOptions}
                countryTagList={countryTagsOptions}
                reRender={refresh}
            />
        </>
    );
};
