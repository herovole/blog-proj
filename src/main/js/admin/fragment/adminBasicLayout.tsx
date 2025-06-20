import React from "react";
import {AdminHeader} from "./adminHeader";

type AdminBasicLayoutProps = {
    children: React.ReactNode;
}

export const AdminBasicLayout: React.FC<AdminBasicLayoutProps> = ({children}) => {
    return (
        <div>
            <AdminHeader/>
            <div className="main-body">
                <div>gadgets here</div>
                <div className="main-area-frame">
                    <div className="main-area">
                        {children}
                    </div>
                </div>
                <div>gadgets here</div>
            </div>
        </div>
    );
}