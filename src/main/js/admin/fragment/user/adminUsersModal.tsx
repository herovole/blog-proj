import React from "react";
import Modal from "react-modal/lib";
import {TextEditingForm} from "../atomic/textEditingForm";
import {UserFields} from "../../../domain/userFields";
import {TagSelectingForm} from "../atomic/tagselectingform/tagSelectingForm";

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
    user: UserFields;
}

export const AdminUsersModal: React.FC<AdminUserModalProps> = ({user}) => {
    const [isOpen, setIsOpen] = React.useState(false);

    const afterOpenModal = () => {
    }
    const openModal = async () => {
        setIsOpen(true);
    }
    const closeModal = async () => {
        setIsOpen(false);
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
                                <td>{user.id}</td>
                                <td>
                                    <button className="comment-modal-submit-s" type="button">Update</button>
                                </td>
                            </tr>
                            <tr>
                                <td>Name :</td>
                                <td><TextEditingForm>{user.name}</TextEditingForm></td>
                                <td>
                                    <button className="comment-modal-submit-s" type="button">Update</button>
                                </td>
                            </tr>
                            <tr>
                                <td>Role :</td>
                                <td><TagSelectingForm>{user.role}</TagSelectingForm></td>
                                <td>
                                    <button className="comment-modal-submit-s" type="button">Update</button>
                                </td>
                            </tr>
                            <tr>
                                <td>Password :</td>
                                <td><TextEditingForm/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>Password(Confirmation) :</td>
                                <td><TextEditingForm/></td>
                                <td>
                                    <button className="comment-modal-submit-s" type="button">Update</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <br/>

                        <span className="comment-form-process"> 投稿中です。しばらくお待ちください。</span><span
                        className="comment-form-err"> 投稿失敗:サイト管理上問題のある表現を含んでいます。</span>

                    </div>
                </div>
            </Modal>
        </>
    );

}
