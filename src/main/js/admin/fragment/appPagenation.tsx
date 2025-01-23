import Pagination from "react-bootstrap/Pagination";
import React from "react";

type AppPaginationType = {
    handlePageChanged: (page: number) => void;
    currentPage: number;
    totalPages: number;
}

export const AppPagination: React.FC<AppPaginationType> = ({
                                                               handlePageChanged,
                                                               currentPage,
                                                               totalPages,
                                                           }) => {

    return (
        <Pagination size="sm" className="pull-right">
            <Pagination.First onClick={() => handlePageChanged(1)}/>
            <Pagination.Prev
                onClick={() => handlePageChanged(currentPage - 1 > 0 ? currentPage - 1 : 1)}/>
            {Array.from({length: totalPages}, (_, i) => (
                <Pagination.Item
                    key={i + 1}
                    active={i + 1 === currentPage}
                    onClick={() => handlePageChanged(i + 1)}
                >
                    {i + 1}
                </Pagination.Item>
            ))}
            <Pagination.Next
                onClick={() => handlePageChanged(currentPage < totalPages ? currentPage + 1 : totalPages)}/>
            <Pagination.Last onClick={() => handlePageChanged(totalPages)}/>
        </Pagination>
    );

}