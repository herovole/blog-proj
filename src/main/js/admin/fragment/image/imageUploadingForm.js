
import React from 'react';
import axios from 'axios';
import {ElementId} from '../../../../domain/elementId'

export class ImageUploadingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            image : null,
            isBeingEdited : false
        };
    }

    handleFileChange = (e) => {
        if (!e.target || !e.target.files) return;
        console.log("image info : " + JSON.stringify(e.target.files));
        console.log("image info2 : " + JSON.stringify(e.target.value));
        console.log("image info : " + e.target.files);
        console.log("image info : " + e.target.files["0"]);
        console.log("image info : " + e.target.files[0]);
        this.state.image = e.target.files[0];
        this.setState(prevState => ({
            image : this.state.image
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
            <div>
                <img src={this.state.image ? URL.createObjectURL(this.state.image) : ""} />
                <input type="file" accept="image/jpeg" onChange={this.handleFileChange} />
            </div>
        );
    }
}

