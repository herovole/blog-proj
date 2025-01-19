import React from 'react';
import {AdminFooter} from './adminFooter';
import {AdminHeader} from "./fragment/adminHeader";

export const AdminEntrance = () => {
    return (
        <div>
            <h1>AdminEntrance</h1>
            <AdminHeader/>
            <AdminFooter/>
        </div>
    )
}