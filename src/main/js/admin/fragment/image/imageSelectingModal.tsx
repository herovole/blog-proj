import React, {useEffect, useState} from 'react';
import Modal from 'react-modal';
import {SearchImagesInput} from "../../../service/image/searchImagesInput";
import {SearchImagesOutput} from "../../../service/image/searchImagesOutput";
import {ImageService} from "../../../service/image/imageService";
import {ResourceManagement} from "../../../service/resourceManagement";
import {ElementId} from "../../../domain/elementId/elementId";

const customStyles = {
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)',
    }
};

type ImageSelectingModalProps = {
    postKey: ElementId;
    imageName: string;
};

export const ImageSelectingModal: React.FC<ImageSelectingModalProps> = (
    {postKey, imageName}) => {

    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    const imageService: ImageService = new ImageService();

    const [isOpen, setIsOpen] = React.useState(false);

    const [page, setPage] = React.useState(1);
    const [imagesInPage, setImagesInPage] = React.useState(6);

    const [images, setImages] = React.useState<ReadonlyArray<{ fileName: string, registrationTimestamp: string }>>([]);
    const [selectedImage, setSelectedImage] = React.useState<string>(imageName);
    const [operationalMessage, setOperationalMessage] = React.useState("");

    useEffect(() => {
        handlePageChanged(1).then();
        ResourceManagement.getInstance().articlesImagePrefixWithSlash().then(setResourcePrefix);
    }, []);

    const afterOpenModal = () => {
    }

    const openModal = async () => {
        setIsOpen(true);
    }
    const closeModal = async () => {
        setIsOpen(false);
    }

    const nextPage = async () => {
        await handlePageChanged(page + 1);
    }

    const previousPage = async () => {
        await handlePageChanged(page - 1);
    }

    const handlePageChanged = async (requestedPage: number) => {
        setOperationalMessage("requesting page " + requestedPage);
        if(requestedPage < 1) {
            setOperationalMessage("");
            return;
        }
        const input: SearchImagesInput = new SearchImagesInput(
            requestedPage,
            imagesInPage
        );
        const output: SearchImagesOutput = await imageService.searchImages(input);
        if (output.isSuccessful()) {
            setPage(requestedPage);
            setImages(output.getFiles());
            setOperationalMessage("");
        } else {
            console.error(output.getMessage("Image Search"))
            setOperationalMessage(output.getMessage("Image Search"));
        }
    }

    const selectImage = async (fileName: string) => {
        setSelectedImage(fileName);
        await closeModal();
        await handlePageChanged(page);
    }


    return (
        <div>
            <img className="image-sample" src={resourcePrefix + selectedImage} alt={"sample"}/>
            <p>{selectedImage}</p>
            <input type="hidden"
                   name={postKey.toStringKey()}
                   value={selectedImage}/>
            <br/>
            <button type="button" onClick={openModal}>Open List</button>
            <p>{selectedImage}</p>
            <Modal
                isOpen={isOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customStyles}
                contentLabel="Example Modal"
            >
                <button type="button" onClick={previousPage}>Previous</button>
                <button type="button" onClick={nextPage}>Next</button>
                <button onClick={closeModal}>close</button>
                <div className="comment-form-process">{operationalMessage}</div>
                <div className="grid-container">
                    {images.map((image, i) => (
                        <div key={"key" + i.toString()}>
                            <img className="image-thumbnail" src={resourcePrefix + image.fileName}
                                 alt={image.fileName}/>
                            <button type="button" onClick={() => selectImage(image.fileName)}>Select</button>
                        </div>
                    ))}
                </div>
            </Modal>
        </div>
    );
}
