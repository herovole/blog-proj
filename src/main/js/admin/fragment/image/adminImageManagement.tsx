import React, {useEffect} from "react";
import {AppPagination} from "../appPagenation";
import {SearchImagesInput} from "../../../service/image/searchImagesInput";
import {ImageService} from "../../../service/image/imageService";
import {SearchImagesOutput} from "../../../service/image/searchImagesOutput";
import {ImageUploadingForm} from "./imageUploadingForm";

export const AdminImageManagement: React.FC = () => {

    const LOCAL_DIR = "c://home/git/blog-proj/app_utility/images/";
    const IMAGES_PER_PAGE: number = 50;
    const imageService: ImageService = new ImageService();
    const [page, setPage] = React.useState(1);
    const [output, setOutput] = React.useState(SearchImagesOutput.empty());
    const [refresh, setRefresh] = React.useState(false);

    useEffect(() => {
        handlePageChanged(1);
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
            setOutput(output);
        } else {
            console.error(output.getMessage("Image Search"))
        }
    }

    const totalPages = (itemsPerPage: number, totalItems: number): number => {
        return totalItems % itemsPerPage === 0
            ? Math.max(totalItems / itemsPerPage, 1)
            : Math.floor(totalItems / itemsPerPage) + 1;
    }
    const reload = () => {
        setRefresh(r => !r);
    }
    if (output.isEmpty()) {
        return <div>loading...</div>
    } else {
        return (
            <div className="admin-image-base">
                <h2>Image Management</h2>
                <AppPagination
                    handlePageChanged={handlePageChanged}
                    currentPage={page}
                    totalPages={totalPages(IMAGES_PER_PAGE, output.getTotal())}
                />
                <ImageUploadingForm reload={reload}/>
                <div className="flex-container">

                    {output.getFiles().map(image => (
                        <div key={image.fileName}>
                            <img className="article-image-thumbnail" src={LOCAL_DIR + image.fileName}
                                 alt={"thumbnail"}/>
                            <div>{image.fileName}</div>
                            <div>{image.registrationTimestamp}</div>
                            <p className="comment-modal-cancel">
                                <button className="comment-modal-submit-s">Delete</button>
                            </p>
                        </div>
                    ))}
                </div>
            </div>
        );
    }
}

