import React, {useEffect} from "react";
import {AppPagination} from "../appPagenation";
import {SearchImagesInput} from "../../../service/image/searchImagesInput";
import {ImageService} from "../../../service/image/imageService";
import {SearchImagesOutput} from "../../../service/image/searchImagesOutput";

export const AdminImageManagement: React.FC = () => {

    const IMAGES_PER_PAGE: number = 50;
    const imageService: ImageService = new ImageService();
    const [page, setPage] = React.useState(1);
    const [totalImages, setTotalImages] = React.useState(1);

    useEffect(() => {


    }, []);

    const handlePageChanged = async (requestedPage: number) => {
        const input: SearchImagesInput = new SearchImagesInput(
            requestedPage,
            IMAGES_PER_PAGE,
            true
        );
        const output: SearchImagesOutput = await imageService.searchImages(input);
        if (output.isSuccessful()) {
            setPage(requestedPage);
            setTotalImages(output.getTotal);
        } else {
            console.error(output.getMessage("Image Search"))
        }
    }

    const totalPages = (itemsPerPage: number, totalItems: number): number => {
        return totalItems % itemsPerPage === 0
            ? totalItems / itemsPerPage
            : Math.floor(totalItems / itemsPerPage) + 1;
    }

    return (
        <div className="admin-image-base">
            <h2>Image Management</h2>
            <AppPagination
                handlePageChanged={handlePageChanged}
                currentPage={page}
                totalPages={totalPages(IMAGES_PER_PAGE, totalImages)}
            />

            <input type="button" className="comment-modal-submit-s" value="New Image"/>

            <div className="flex-container">
                <div>
                    <div className="article-image-thumbnail">dummy image</div>
                    <div>article1.jpg</div>
                    <div>2025/02/12 09:08:52</div>
                    <p className="comment-modal-cancel">
                        <button className="comment-modal-submit-s">Delete</button>
                    </p>

                </div>

                <div>
                    <div className="article-image-thumbnail">dummy image</div>
                    <div>article1.jpg</div>
                    <div>2025/02/12 09:08:52</div>
                    <p className="comment-modal-cancel">
                        <button className="comment-modal-submit-s">Delete</button>
                    </p>
                </div>
            </div>
        </div>
    );
}

