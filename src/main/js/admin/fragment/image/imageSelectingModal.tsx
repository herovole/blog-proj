import React from 'react';
import axios from 'axios';
import Modal from 'react-modal';

const customStyles = {
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)',
    },
};
//Modal.setAppElement('#yourAppElement');

type ImageSelectingModalProps = {
    imageName: string;
}

export const ImageSelectingModal: React.FC<ImageSelectingModalProps> = (
    {imageName}) => {

    const LOCAL_DIR = "c://home/git/blog-proj/app_utility/images/";
    const GET_PARAM_PAGE = "page";
    const GET_PARAM_NUMBER = "number";

    const [isOpen, setOpen] = React.useState(false);

    const [page, setPage] = React.useState(1);
    const [imagesInPage, setImagesInPage] = React.useState(25);

    const [fileNames, setFileNames] = React.useState<string[]>([]);
    const [selectedImage, setSelectedImage] = React.useState<string>("");

    const afterOpenModal = () => {
    }

    const openModal = () => {
        setOpen(true);
        reload();
    }
    const closeModal = () => {
        setOpen(false);
        reload();
    }

    const nextPage = () => {
        setPage(page => page + 1);
        reload();
    }

    const previousPage = () => {
        setPage(page => page - 1);
        reload();
    }

    const reload = async () => {
        const page = this.state.page;
        const imagesInPage = this.state.imagesInPage;

        const params = new URLSearchParams();
        params.append(ImageSelectingModal.GET_PARAM_PAGE, page);
        params.append(ImageSelectingModal.GET_PARAM_NUMBER, imagesInPage);
        console.log(ImageSelectingModal.GET_PARAM_PAGE + "/" + ImageSelectingModal.GET_PARAM_NUMBER);

        try {
            const response = await axios.get("/api/v1/images", {params: params});
            this.setState(prevState => ({
                fileNames: response.data
            }))
        } catch (error) {
            console.error('Error submitting form:', error);
            this.setState(prevState => ({
                fileNames: []
            }))
        }
    }

    const selectImage = (fileName: string) => {
        setSelectedImage(fileName);
        closeModal();
        reload();
    }

    return (
        <div>
            <img className="image-sample" src={LOCAL_DIR + selectedImage}/>
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
                            <img className="image-thumbnail" src={LOCAL_DIR + name}/>
                            <button type="button" onClick={() => selectImage(name)}>Select</button>
                        </div>
                    ))}
                </div>
            </Modal>
        </div>
    );
}

