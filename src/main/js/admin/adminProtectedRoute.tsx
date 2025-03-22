import React, {useEffect} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AuthService} from "../service/auth/authService";
import {BasicApiResult} from "../domain/basicApiResult";
import {Navigate} from "react-router-dom";

type AdminProtectedRouteProps = {
    children: React.ReactNode;
}

export const AdminProtectedRoute: React.FC<AdminProtectedRouteProps> = ({children}) => {
    const authService: AuthService = new AuthService();
    const [isValid, setIsValid] = React.useState<boolean | null>(null);

    const load = async (): Promise<void> => {
        const validateOutput: BasicApiResult = await authService.validateToken();
        if (!validateOutput.isSuccessful()) {
            console.error(validateOutput.getMessage("token verification"));
            setIsValid(false);
            return;
        }
        setIsValid(true);
    };
    useEffect(() => {
        load().then();
    }, []);

    if (isValid) {
        return (children);
    } else if (isValid === false) {
        return <Navigate to="/admin/login" replace/>
    } else {
        return <div>loading...</div>
    }
};
