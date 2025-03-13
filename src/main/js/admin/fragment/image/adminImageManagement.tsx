import React, {useEffect, useState} from "react";
import {AppPagination} from "../appPagenation";
import {SearchImagesInput} from "../../../service/image/searchImagesInput";
import {ImageService} from "../../../service/image/imageService";
import {SearchImagesOutput} from "../../../service/image/searchImagesOutput";
import {ImageUploadingForm} from "./imageUploadingForm";
import {RemoveImageInput} from "../../../service/image/removeImageInput";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {ResourceManagement} from "../../../service/resourceManagement";

export const AdminImageManagement: React.FC = () => {

    const IMAGES_PER_PAGE: number = 50;
    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    const imageService: ImageService = new ImageService();
    const [page, setPage] = React.useState(1);
    const [data, setData] = React.useState(SearchImagesOutput.empty());
    const [refresh, setRefresh] = React.useState(false);


    const handleRemove = async (e: React.FormEvent<HTMLButtonElement>) => {
        e.preventDefault();
        const imageName: string = e.currentTarget.name;
        console.log("attempting to remove " + imageName);
        const input: RemoveImageInput = new RemoveImageInput(imageName);
        const output: BasicApiResult = await imageService.removeImage(input);
        if (output.isSuccessful()) {
            console.info(output.getMessage("Image Removal"));
            reload();
        } else {
            console.error(output.getMessage("Image Removal"));
        }
    }

    useEffect(() => {
        handlePageChanged(1).then();
        ResourceManagement.getInstance().articlesImagePrefixWithSlash().then(setResourcePrefix);
    }, [refresh]);

    const handlePageChanged = async (requestedPage: number) => {
        const input: SearchImagesInput = new SearchImagesInput(
            requestedPage,
            IMAGES_PER_PAGE,
        );
        const output: SearchImagesOutput = await imageService.searchImages(input);
        if (output.isSuccessful()) {
            setPage(requestedPage);
            setData(output);
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

    if (data.isEmpty()) {
        return <div>loading...</div>
    } else {
        return (
            <div className="admin-image-base">
                <input type="hidden" name="reload" value={refresh.toString()}/>
                <h2>Image Management</h2>
                <AppPagination
                    handlePageChanged={handlePageChanged}
                    currentPage={page}
                    totalPages={totalPages(IMAGES_PER_PAGE, data.getTotal())}
                />
                <ImageUploadingForm reload={reload}/>
                <div className="flex-container">

                    {data.getFiles().map(image => (
                        <div key={image.fileName}>
                            <img className="article-image-thumbnail" src={resourcePrefix + image.fileName}
                                 alt={"thumbnail"}/>
                            <div>{image.fileName}</div>
                            <div>{image.registrationTimestamp}</div>
                            <p className="comment-modal-cancel">
                                <button className="comment-modal-submit-s"
                                        name={image.fileName}
                                        onClick={handleRemove}>
                                    Remove
                                </button>
                            </p>
                        </div>
                    ))}
                </div>
            </div>
        );
    }
}

