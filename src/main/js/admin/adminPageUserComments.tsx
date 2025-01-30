import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {AdminBasicLayout} from "./fragment/adminBasicLayout";
import {AdminComments} from "./fragment/comment/adminComments";

export const AdminPageUserComments: React.FC = () => {
    return (
        <AdminBasicLayout>
            <AdminComments directoryToIndividualPage={"/admin/articles"}/>
        </AdminBasicLayout>
    );
};
