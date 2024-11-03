
import React from 'react';
import axios from 'axios';

export class ImageSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.candidates, // :TagUnitList
            image : null,
            isBeingEdited : false
        };
        console.log("state / " + this.props.candidates);
    }

    handleFileChange = (e) => {
        this.setState(prevState => ({
            image : e.target.files[0]
        }));
    };

    handleSubmit = async (e) => {
        e.preventDefault();
        if (!image) return;

        const formData = new FormData();
        formData.append('image', image);

        try {
            const response = await axios.post('/api/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            console.log('Image uploaded:', response.data);
        } catch (error) {
            console.error('Error uploading image:', error);
        }
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <input type="file" accept="image/jpeg" onChange={this.handleFileChange} />
                <button type="submit">Upload Image</button>
            </form>
        );
    }
}

export default ImageSelectingForm;
