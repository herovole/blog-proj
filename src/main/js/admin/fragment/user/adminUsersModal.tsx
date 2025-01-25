import React from "react";
import Modal from "react-modal/lib";
import {TextEditingForm} from "../atomic/textEditingForm";
import {UserFields} from "../../../domain/userFields";
import {TagSelectingForm} from "../atomic/tagselectingform/tagSelectingForm";
import {RootElementId} from "../../../domain/elementId/rootElementId";
import {TagUnits} from "../atomic/tagselectingform/tagUnits";
import {CreateAdminUserInput} from "../../../service/auth/createAdminUserInput";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {useGoogleReCaptcha} from "react-google-recaptcha-v3";
import {AuthService} from "../../../service/auth/authService";
import {Zurvan} from "../../../domain/zurvan";

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

type AdminUserModalProps = {
    user?: UserFields;
    roles: TagUnits;
    label: string;
    reload: () => void;
}

export const AdminUsersModal: React.FC<AdminUserModalProps> = ({user, roles, label, reload}) => {
    const [isOpen, setIsOpen] = React.useState(false);
    const [info, setInfo] = React.useState("");
    const [warn, setWarn] = React.useState("");

    const authService: AuthService = new AuthService();
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "editUser";


    const afterOpenModal = () => {
    }
    const openModal = async () => {
        setIsOpen(true);
    }
    const closeModal = async () => {
        setIsOpen(false);
    }
    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent page reload
        setInfo("");
        setWarn("");
        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            return;
        }

        const formData = new FormData(event.currentTarget as HTMLFormElement);
        const input: CreateAdminUserInput = new CreateAdminUserInput(
            formData.get("userName") as string,
            formData.get("role") as string,
            formData.get("password") as string,
            recaptchaToken
        );
        const output: BasicApiResult = await authService.createAdminUser(input);
        if (output.isSuccessful()) {
            setInfo(output.getMessage("Edit User"));
            await Zurvan.delay(2);
            reload();
        } else {
            setWarn(output.getMessage("Edit User"));
        }
    }

    return (
        <>
            <button type="button" onClick={openModal}>Open List</button>
            <Modal
                isOpen={isOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customStyles}
                contentLabel="Example Modal"
            >
                <div className="comment-modal-exterior">
                    <div className="comment-modal-interior">
                        <div className="comment-modal-cancel">
                            <button type="button">Close</button>
                        </div>
                        <div className="comment-modal-header">ユーザ情報更新</div>
                        <br/>
                        <form onSubmit={handleSubmit}>
                            <input type="button" className="comment-modal-submit-s" value={label}/>
                            <table>
                                <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>ID :</td>
                                    <td>{user ? user.id : ""}</td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Name :</td>
                                    <td><TextEditingForm
                                        postKey={RootElementId.valueOf("userName")}>{user ? user.name : ""}</TextEditingForm>
                                    </td>
                                    <td>
                                        <button className="comment-modal-submit-s" type="button">Update</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Role :</td>
                                    <td><TagSelectingForm
                                        postKey={RootElementId.valueOf("role")}
                                        candidates={roles}>{user ? user.role : ""}</TagSelectingForm></td>
                                    <td>
                                        <button className="comment-modal-submit-s" type="button">Update</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Password :</td>
                                    <td><TextEditingForm postKey={RootElementId.valueOf("password")}/></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>Password(Confirmation) :</td>
                                    <td><TextEditingForm postKey={RootElementId.valueOf("confirmation")}/></td>
                                    <td>
                                        <button className="comment-modal-submit-s" type="button">Update</button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <br/>

                            <span className="comment-form-process">{info}</span><span
                            className="comment-form-err">{warn}</span>
                        </form>
                    </div>
                </div>
            </Modal>
        </>
    );

}
