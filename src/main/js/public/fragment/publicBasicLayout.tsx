import React from "react";
import {PublicHeader} from "./publicHeader";
import {GadgetsRight} from "./gadget/gadgetsRight";
import {GadgetsLeft} from "./gadget/gadgetsLeft";

type PublicBasicLayoutProps = {
    children: React.ReactNode;
}

export const PublicBasicLayout: React.FC<PublicBasicLayoutProps> = ({children}) => {
    return (
        <div>
            <PublicHeader/>
            <div className="main-body">
                <div>
                    <GadgetsLeft directoryToIndividualPage={"/articles"}/>
                </div>
                <div className="main-area-frame">
                    <div className="main-area">
                        {children}
                    </div>
                </div>
                <div>
                    <GadgetsRight/>
                </div>
            </div>
        </div>
    );
}