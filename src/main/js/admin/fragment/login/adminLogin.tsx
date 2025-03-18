import React from "react";
import {AuthService} from "../../../service/auth/authService";
import {LoginAdminPhase1Input} from "../../../service/auth/loginAdminPhase1Input";
import {useGoogleReCaptcha} from "react-google-recaptcha-v3";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {useNavigate} from "react-router-dom";
import {LoginAdminPhase2Input} from "../../../service/auth/loginAdminPhase2Input";

type AdminLoginInputProps = {
    refreshParent: () => void;
}

export const AdminLogin: React.FC<AdminLoginInputProps> = ({refreshParent}) => {
    const authService: AuthService = new AuthService();
    const [loginHandle, setLoginHandle] = React.useState<string>("");
    const [loginPassword, setLoginPassword] = React.useState<string>("");
    const [verificationCode, setVerificationCode] = React.useState<string>("");
    const [phase, setPhase] = React.useState<number>(1);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "login";
    const navigate = useNavigate();

    const handleHandleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginHandle(e.target.value);
    }

    const handlePasswordOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginPassword(e.target.value);
    }

    const handleVerificationCodeOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setVerificationCode(e.target.value);
    }

    const handleSubmit = async (event: React.FormEvent<HTMLButtonElement>): Promise<void> => {
        event.preventDefault();
        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            return;
        }

        if (phase == 1) {
            await handleSubmitPhase1(recaptchaToken);
        } else if (phase == 2) {
            await handleSubmitPhase2(recaptchaToken);
        }

    }

    const handleSubmitPhase1 = async (recaptchaToken: string): Promise<void> => {
        const input: LoginAdminPhase1Input = new LoginAdminPhase1Input(
            loginHandle,
            loginPassword,
            recaptchaToken
        )

        const output: BasicApiResult = await authService.loginAdminPhase1(input);
        if (output.isSuccessful()) {
            console.log(output.getMessage("ログイン - 1段階目"));
            setPhase(2);
        } else {
            console.error(output.getMessage("ログイン - 1段階目"));
            refreshParent();
        }
    }

    const handleSubmitPhase2 = async (recaptchaToken: string): Promise<void> => {
        const input: LoginAdminPhase2Input = new LoginAdminPhase2Input(
            loginHandle,
            loginPassword,
            verificationCode,
            recaptchaToken
        )

        const output: BasicApiResult = await authService.loginAdminPhase2(input);
        if (output.isSuccessful()) {
            console.log(output.getMessage("ログイン - 2段階目"));
            navigate("/admin");
        } else {
            console.error(output.getMessage("ログイン - 2段階目"));
            refreshParent();
        }
    }

    const phase2Option = phase == 2 ? <>
        <span className="login-label">Verification Code : </span>
        <input className="login-verification-code" type="text" onChange={handleVerificationCodeOnChange}
               value={verificationCode}/><br/>
    </> : <></>

    const phase2Modification = phase == 2;


    return (
        <div className="login-frame">
            <div className="login-body">
                <span className="login-label">User : </span>
                <input className="login-handle" type="text"
                       onChange={handleHandleOnChange}
                       value={loginHandle}
                       disabled={phase2Modification}/><br/>
                <span className="login-label">Password : </span>
                <input className="login-password" type="password"
                       onChange={handlePasswordOnChange}
                       value={loginPassword}
                       disabled={phase2Modification}
                /><br/>
                <button className="login-submit" type="button"
                        onClick={handleSubmit}>Enter</button>
                {phase2Option}
            </div>
        </div>
    )
}