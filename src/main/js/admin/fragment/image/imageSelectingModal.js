
import React from 'react';
import axios from 'axios';
import ReactDOM from 'react-dom';
import Modal from 'react-modal';
import {ElementId} from '../../../domain/elementId'

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

export class ImageSelectingModal extends React.Component {

    static LOCAL_DIR = "c://home/git/blog-proj/app_utility/images/";
    static GET_PARAM_PAGE = "page";
    static GET_PARAM_NUMBER = "number";

    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            image : this.props.imageName ? this.props.imageName : null,
            isOpen : false,

            page : 1,
            imagesInPage : this.props.imagesInPage ? this.props.imagesInPage : 25,

            fileNames : [],
            selectedImage : "",
        };
        this.refDisplay = React.createRef();
        this.refHidden = React.createRef();
    }

    componentDidMount() { }
    afterOpenModal = () => { }

    openModal = () => {
        this.setState(prevState => ({
            isOpen : true
        }));
        this.reload();
    }
    closeModal = () => {
        this.setState(prevState => ({
            isOpen : false
        }));
    }

    nextPage = () => {
        this.setState(prevState => ({
            page : this.state.page + 1
        }))
        this.reload();
    }

    previousPage = () => {
        this.setState(prevState => ({
            page : this.state.page - 1 > 0 ? this.state.page - 1 : 1
        }))
        this.reload();
    }

    reload = async () => {
        const page = this.state.page;
        const imagesInPage = this.state.imagesInPage;

        const params = new URLSearchParams();
        params.append(ImageSelectingModal.GET_PARAM_PAGE, page);
        params.append(ImageSelectingModal.GET_PARAM_NUMBER, imagesInPage);
        console.log(ImageSelectingModal.GET_PARAM_PAGE + "/" + ImageSelectingModal.GET_PARAM_NUMBER);

        try {
            const response = await axios.get("/api/v1/images", {params : params});
            this.setState(prevState => ({
                fileNames : response.data
            }))
        } catch (error) {
            console.error('Error submitting form:', error);
            this.setState(prevState => ({
                fileNames : []
            }))
        }
    }

    selectImage = (fileName) => {
        this.setState(prevState => ({
            selectedImage : fileName
        }))
        this.refDisplay.current.text = this.state.selectedImage;
        this.refHidden.current.value = this.state.selectedImage;
        this.closeModal();
        this.reload();
    }

    render() {
        return (
            <div>
                <img class="image-sample" src={ImageSelectingModal.LOCAL_DIR + this.state.selectedImage} />
                <button type="button" onClick={this.openModal}>Open List</button>
                <p ref={this.refDisplay}>{this.state.selectedImage}</p>
                <input type="hidden"
                  ref={this.refHidden}
                  name={this.props.postKey.toStringKey()}
                  value={this.state.selectedImage} />
                <Modal
                  isOpen={this.state.isOpen}
                  onAfterOpen={this.afterOpenModal}
                  onRequestClose={this.closeModal}
                  style={customStyles}
                  contentLabel="Example Modal"
                >
                    <button type="button" onClick={this.previousPage}>Previous</button>
                    <button type="button" onClick={this.nextPage}>Next</button>
                    <button onClick={this.closeModal}>close</button>
                    <div class="grid-container">
                        {this.state.fileNames.map((name, i) => (
                            <div>
                                <img class="image-thumbnail" src={ImageSelectingModal.LOCAL_DIR + name} />
                                <button type="button" onClick={() => this.selectImage(name)}>Select</button>
                            </div>
                        ))}
                    </div>
                </Modal>
            </div>
        );
    }
}

