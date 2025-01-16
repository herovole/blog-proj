import React from "react";
import {AuthService} from "./authService";
import {AdminLoginInput} from "./adminLoginInput";
import {useGoogleReCaptcha} from "react-google-recaptcha-v3";
import {AdminLoginOutput} from "./adminLoginOutput";

type AdminLoginInputProps = {
    refreshParent: () => void;
}

export const AdminLogin: React.FC<AdminLoginInputProps> = ({refreshParent}) => {
    const authService: AuthService = new AuthService();
    const [loginHandle, setLoginHandle] = React.useState<string>("");
    const [loginPassword, setLoginPassword] = React.useState<string>("");
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "login";

    const handleHandleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginHandle(e.target.value);
    }

    const handlePasswordOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginPassword(e.target.value);
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
        const input: AdminLoginInput = new AdminLoginInput(
            loginHandle,
            loginPassword,
            recaptchaToken
        )

        const output: AdminLoginOutput = await authService.loginAdmin(input);
        if (output.isSuccessful()) {
            console.log("Token Granted.");
        } else {
            console.error(output.getMessage("ログイン"));
        }
        refreshParent();
    }

    return (
        <div className="login-frame">
            <div className="login-body">
                <span className="login-label">User : </span>
                <input className="login-handle" type="text" onChange={handleHandleOnChange} value={loginHandle}/><br/>
                <span className="login-label">Password : </span>
                <input className="login-password" type="text" onChange={handlePasswordOnChange}
                       value={loginPassword}/><br/>
                <button className="login-submit" type="button" onClick={handleSubmit}>Enter</button>
            </div>
        </div>
    )
}