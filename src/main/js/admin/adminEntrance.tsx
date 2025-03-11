import React from 'react';
import {AdminHeader} from "./fragment/adminHeader";
import {AdminRss2} from "./fragment/site/adminRss2";

export const AdminEntrance = () => {
    return (
        <div>
            <h1>AdminEntrance</h1>
            <AdminHeader/>
            <AdminRss2/>
        </div>
    )
}