import React from "react";
import {CommentAndReport, SearchCommentsOutput} from "../../../service/user/searchCommentsOutput";
import {AppPagination} from "../appPagenation";
import DatePicker from "react-datepicker";
import {SearchCommentsInput} from "../../../service/user/searchCommentsInput";
import {UserService} from "../../../service/user/userService";
import {AdminCommentUnit} from "./adminCommentUnit";

type AdminCommentsProps = {
    directoryToIndividualPage: string;
}

export const AdminComments: React.FC<AdminCommentsProps> = ({directoryToIndividualPage}) => {
    const userService: UserService = new UserService();

    const [data, setData] = React.useState<SearchCommentsOutput>(SearchCommentsOutput.empty());
    const [inputFixed, setInputFixed] = React.useState<SearchCommentsInput>(SearchCommentsInput.byDefault());

    const [itemsPerPage, setItemsPerPage] = React.useState<number>(inputFixed.itemsPerPage);
    const [page, setPage] = React.useState<number>(inputFixed.page);
    const [keywords, setKeywords] = React.useState<string>(inputFixed.keywords);
    const [dateFrom, setDateFrom] = React.useState<Date | null>(inputFixed.dateFrom);
    const [dateTo, setDateTo] = React.useState<Date | null>(inputFixed.dateTo);
    const MIN_DATE: Date = new Date("2025-01-01");
    const MAX_DATE: Date = new Date();
    const [hasReports, setHasReports] = React.useState<boolean>(inputFixed.hasReports);
    const [hasUnhandledReports, setHasUnhandledReports] = React.useState<boolean>(inputFixed.hasUnhandledReports);
    const [refresh, setRefresh] = React.useState(false);

    React.useEffect(() => {
        loadComments(SearchCommentsInput.byDefault()).then();
        }, []);

    const handleItemsPerPage = (e: React.ChangeEvent<HTMLInputElement>) => {
        setItemsPerPage(parseInt(e.currentTarget.value));
    }
    const handleKeywords = (e: React.ChangeEvent<HTMLInputElement>) => {
        setKeywords(e.currentTarget.value);
    }
    const handleDateFrom = (date: Date | null) => {
        setDateFrom(date);
    }
    const handleDateTo = (date: Date | null) => {
        setDateTo(date);
    }
    const handleHasReports = (e: React.ChangeEvent<HTMLInputElement>) => {
        setHasReports(e.currentTarget.checked);
    }
    const handleHasUnhandledReports = (e: React.ChangeEvent<HTMLInputElement>) => {
        setHasUnhandledReports(e.currentTarget.checked);
    }

    const loadComments = async (input: SearchCommentsInput): Promise<void> => {
        const output: SearchCommentsOutput = await userService.searchComments(input);
        if (output.isSuccessful()) {
            setData(output);
        } else {
            console.error(output.getMessage("article list retrieval"));
        }
        setRefresh(r => !r);
    }

    const handleSearch = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent page reload
        setPage(1);
        const input: SearchCommentsInput = new SearchCommentsInput(
            itemsPerPage,
            1,
            dateFrom,
            dateTo,
            keywords,
            hasReports,
            hasUnhandledReports,
            true
        );
        setInputFixed(input);
        await loadComments(input);
    }

    const handlePageChanged = async (page: number) => {
        // Trigger the form submission manually
        const input: SearchCommentsInput = inputFixed.appendPage(page);
        setItemsPerPage(input.itemsPerPage);
        setPage(input.page);
        setKeywords(input.keywords);
        setDateFrom(input.dateFrom);
        setDateTo(input.dateTo);
        setHasReports(input.hasReports);
        setHasUnhandledReports(input.hasUnhandledReports);
        await loadComments(input);
    }
    const totalPages = () => {
        return data.getTotal() % inputFixed.itemsPerPage === 0
            ? Math.min(data.getTotal() / inputFixed.itemsPerPage, 1)
            : Math.floor(data.getTotal() / inputFixed.itemsPerPage) + 1;
    }

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

                    <br/>
                    <p>キーワード :
                        <input
                            className="input-keywords"
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
                    <p>レポート有り :
                        <input className="admin-editable-text-activated"
                               type="checkbox"
                               checked={hasReports}
                               onChange={handleHasReports}
                        />
                    </p>
                    <p>未対応レポート有り :
                        <input className="admin-editable-text-activated"
                               type="checkbox"
                               checked={hasUnhandledReports}
                               onChange={handleHasUnhandledReports}
                        />
                    </p>

                </form>
            </div>
        </div>

    return (<>
            <input type="hidden" name="reload" value={refresh.toString()}/>
            {htmlSearch}
            <AppPagination handlePageChanged={handlePageChanged} currentPage={page} totalPages={totalPages()}/>
            {data.getComments().map((each: CommentAndReport) => (
                <div key={each.commentUnit.body.commentSerialNumber}>
                    <AdminCommentUnit content={each} directoryToIndividualPage={directoryToIndividualPage}/>
                </div>
            ))}
        </>
    )
        ;
}