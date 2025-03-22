import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminLogin} from "./fragment/login/adminLogin";
import {PublicBasicLayout} from "../public/fragment/publicBasicLayout";

export const AdminPageLogin: React.FC = () => {
    const [refresh, setRefresh] = React.useState(false);

    const reload = () => {
        setRefresh(r => !r);
    }

    return (
        <>
            <input type="hidden" name="reload" value={refresh.toString()}/>
            <PublicBasicLayout>
                <AdminLogin
                    refreshParent={reload}
                />
            </PublicBasicLayout>
        </>
    )
        ;
};
