import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminLogin} from "./fragment/login/adminLogin";

export const AdminPageLogin: React.FC = () => {
    const [refresh, setRefresh] = React.useState(false);

    const reload = () => {
        setRefresh(r => !r);
    }

    return (
        <div>
            <AdminLogin
                refreshParent={reload}
            />
        </div>
    );
};
