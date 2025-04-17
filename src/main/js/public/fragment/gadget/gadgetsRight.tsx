import React from "react";
import {LivedoorRss} from "../livedoorRss";
import {ExtLinks} from "./extLinks";


export const GadgetsRight: React.FC = () => {
    return (
        <div>
            <LivedoorRss/>
            <ExtLinks/>
        </div>
    );
}