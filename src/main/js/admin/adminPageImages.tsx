import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminBasicLayout} from "./fragment/adminBasicLayout";
import {AdminImageManagement} from "./fragment/image/adminImageManagement";

export const AdminPageImages: React.FC = () => {

    return (
        <AdminBasicLayout>
            <AdminImageManagement/>
        </AdminBasicLayout>
    );
};
