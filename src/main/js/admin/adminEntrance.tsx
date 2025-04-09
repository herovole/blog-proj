import React from 'react';
import {AdminHeader} from "./fragment/adminHeader";
import {AdminRss} from "./fragment/site/adminRss";

export const AdminEntrance = () => {
    return (
        <div>
            <h1>AdminEntrance</h1>
            <AdminHeader/>
            <AdminRss/>
        </div>
    )
}