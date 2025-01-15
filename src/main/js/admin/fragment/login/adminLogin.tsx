import React from "react";

export const AdminLogin: React.FC = () => {
    const [loginHandle, setLoginHandle] = React.useState<string>("");
    const [loginPassword, setLoginPassword] = React.useState<string>("");

    const handleHandleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginHandle(e.target.value);
    }

    const handlePasswordOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLoginPassword(e.target.value);
    }

    const handleSubmit = async (event: React.FormEvent<HTMLButtonElement>): Promise<void> => {
        event.preventDefault();
    }

    return (
        <div className="login-frame">
            <div className="login-body">
                <span className="login-label">User : </span>
                <input className="login-handle" type="text" onChange={handleHandleOnChange} value={loginHandle}/><br/>
                <span className="login-label">Password : </span>
                <input className="login-password" type="text" onChange={handlePasswordOnChange} value={loginPassword}/><br/>
                <button className="login-submit" type="button" onClick={handleSubmit}>Enter</button>
            </div>
        </div>
    )
}