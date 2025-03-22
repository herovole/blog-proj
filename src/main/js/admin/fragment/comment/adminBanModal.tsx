import React from "react";
import {UserService} from "../../../service/user/userService";
import {BanUserInput} from "../../../service/user/banUserInput";
import {useGoogleReCaptcha} from "react-google-recaptcha-v3";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {BanIpInput} from "../../../service/user/banIpInput";
import Modal from "react-modal/lib";

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

type AdminBanModalProps = {
    label: string;
    userId: number;
    userBannedUntil: string;
    hasUserBanned: boolean;
    ip: string;
    ipBannedUntil: string;
    hasIpBanned: boolean;
}

export const AdminBanModal: React.FC<AdminBanModalProps> = ({
                                                                label,
                                                                userId,
                                                                userBannedUntil,
                                                                hasUserBanned,
                                                                ip,
                                                                ipBannedUntil,
                                                                hasIpBanned,
                                                            }) => {

    const userService: UserService = new UserService();
    const [days, setDays] = React.useState<number>(3);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "banUser";

    const [isOpen, setIsOpen] = React.useState<boolean>(false);
    const [refresh, setRefresh] = React.useState<boolean>(false);

    const handleDaysChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        setDays(parseInt(e.currentTarget.value));
    }
    const handleBan = async (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            return;
        }
        const banUserInput: BanUserInput = new BanUserInput(
            userId,
            days,
            true,
            recaptchaToken
        )
        const banUserOutput: BasicApiResult = await userService.banUser(banUserInput);
        if (banUserOutput.isSuccessful()) {
            console.info(banUserOutput.getMessage("Ban User"));
        } else {
            console.error(banUserOutput.getMessage("Ban User"));
        }

        const banIpInput: BanIpInput = new BanIpInput(
            ip,
            days,
            true,
            recaptchaToken
        )
        const banIpOutput: BasicApiResult = await userService.banIp(banIpInput);
        if (banIpOutput.isSuccessful()) {
            console.info(banUserOutput.getMessage("Ban Ip"));
        } else {
            console.error(banIpOutput.getMessage("Ban Ip"));
        }
        setRefresh(r => !r);
    }

    const openModal = () => {
        setIsOpen(true);
    }
    const closeModal = () => {
        setIsOpen(false);
    }
    const afterOpenModal = () => {
    }

    return (
        <>
            <button onClick={openModal}>{label}</button>
            <Modal
                isOpen={isOpen}
                onAfterOpen={afterOpenModal}
                onRequestClose={closeModal}
                style={customStyles}
                contentLabel="Example Modal"
            >
                <button onClick={closeModal}>close</button>
                <div className="report-form-exterior">
                    <div className="report-form-interior">
                        <div className="report-form-header">
                            Suspend User
                        </div>
                        <div className="admin-ban-modal">
                            <div>userID : {userId}</div>
                            <div>userIP : {ip}</div>
                            <div className={hasUserBanned ? "comment-form-process" : "comment-form-err"}>ID suspended
                                until
                                : {userBannedUntil}</div>
                            <div className={hasIpBanned ? "comment-form-process" : "comment-form-err"}>IP suspended
                                until
                                : {ipBannedUntil}</div>
                        </div>
                        <br/>
                        <div>
                            <input
                                className="input-items-per-page"
                                type="number"
                                max="100"
                                min="10"
                                step="5"
                                placeholder="items per page"
                                onChange={handleDaysChange}
                                value={days}
                            />
                            days
                        </div>
                        <div>
                            <button type="button" onClick={handleBan}>Suspend User</button>
                        </div>
                    </div>
                </div>
            </Modal>
        </>
    );
}