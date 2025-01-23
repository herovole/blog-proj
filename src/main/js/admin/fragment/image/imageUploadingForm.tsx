import React from 'react';
import axios from 'axios';


export const ImageUploadingForm: React.FC = () => {
    const [image, setImage] = React.useState<File | null>(null);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (!e.target || !e.target.files) return;
        setImage(e.target.files[0]);
    };

    const handleSubmit = async (e: React.FormEvent<HTMLButtonElement>): Promise<void> => {
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

    return (
        <div>
            <img src={image ? URL.createObjectURL(image) : ""} alt={"Being registered"}/>
            <input type="file" accept="image/jpeg" onChange={handleFileChange}/>
            <button type="button" onClick={handleSubmit}>Upload</button>
        </div>
    );
}

