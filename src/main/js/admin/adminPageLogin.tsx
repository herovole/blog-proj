import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminLogin} from "./fragment/login/adminLogin";
import {AdminBasicLayout} from "./fragment/adminBasicLayout";

export const AdminPageLogin: React.FC = () => {
    const [refresh, setRefresh] = React.useState(false);

    React.useEffect(() => { }, [refresh]);
    const reload = () => {
        setRefresh(r => !r);
    }

    return (
        <AdminBasicLayout>
            <AdminLogin
                refreshParent={reload}
            />
        </AdminBasicLayout>
    );
};
