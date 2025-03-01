import React, {useEffect, useState} from 'react';
import Modal from 'react-modal';
import {SearchImagesInput} from "../../../service/image/searchImagesInput";
import {SearchImagesOutput} from "../../../service/image/searchImagesOutput";
import {ImageService} from "../../../service/image/imageService";
import {ResourcePrefix} from "../../../service/image/resourcePrefix";

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
    imageName: string;
};

export const ImageSelectingModal: React.FC<ImageSelectingModalProps> = (
    {imageName}) => {

    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    const imageService: ImageService = new ImageService();

    const [isOpen, setIsOpen] = React.useState(false);

    const [page, setPage] = React.useState(1);
    const [imagesInPage, setImagesInPage] = React.useState(25);

    const [fileNames, setFileNames] = React.useState<string[]>([]);
    const [selectedImage, setSelectedImage] = React.useState<string>(imageName);

    useEffect(() => {
        handlePageChanged(1).then();
        ResourcePrefix.getInstance().articlesWithSlash().then(setResourcePrefix);
    }, []);

    const afterOpenModal = () => {
    }

    const openModal = async () => {
        setIsOpen(true);
        await handlePageChanged(page);
    }
    const closeModal = async () => {
        setIsOpen(false);
        await handlePageChanged(page);
    }

    const nextPage = async () => {
        setPage(page => page + 1);
        await handlePageChanged(page);
    }

    const previousPage = async () => {
        setPage(page => page - 1);
        await handlePageChanged(page);
    }

    const handlePageChanged = async (requestedPage: number) => {
        const input: SearchImagesInput = new SearchImagesInput(
            requestedPage,
            imagesInPage,
            true
        );
        const output: SearchImagesOutput = await imageService.searchImages(input);
        if (output.isSuccessful()) {
            setPage(requestedPage);
        } else {
            console.error(output.getMessage("Image Search"))
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
                <div className="grid-container">
                    {fileNames.map((name, i) => (
                        <div key={"key" + i.toString()}>
                            <img className="image-thumbnail" src={resourcePrefix + name} alt={name}/>
                            <button type="button" onClick={() => selectImage(name)}>Select</button>
                        </div>
                    ))}
                </div>
            </Modal>
        </div>
    );
}
