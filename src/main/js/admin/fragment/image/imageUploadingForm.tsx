import React from 'react';
import {ImageService} from "../../../service/image/imageService";
import {PostImageInput} from "../../../service/image/postImageInput";
import {BasicApiResult} from "../../../domain/basicApiResult";


type ImageUploadingFormProps = {
    reload: () => void;
}

export const ImageUploadingForm: React.FC<ImageUploadingFormProps> = ({reload}) => {

    const imageService: ImageService = new ImageService();
    const [image, setImage] = React.useState<File | null>(null);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (!e.target || !e.target.files) return;
        setImage(e.target.files[0]);
    };

    const handleSubmit = async (e: React.FormEvent<HTMLButtonElement>): Promise<void> => {
        e.preventDefault();
        if (!image) return;
        const input: PostImageInput = new PostImageInput(image);
        const output: BasicApiResult = await imageService.postImage(input);
        if (output.isSuccessful()) {
            console.info(output.getMessage("Image Upload"));
            reload();
        } else {
            console.error(output.getMessage("Image Upload"));
        }
    }

    return (
        <div>
            <img className="article-image-thumbnail" src={image ? URL.createObjectURL(image) : ""}
                 alt={"Being registered"}/>
            <input type="file" accept="image/jpeg" onChange={handleFileChange}/>
            <button className="comment-modal-submit-s" type="button" onClick={handleSubmit}>Upload</button>
        </div>
    );
}
