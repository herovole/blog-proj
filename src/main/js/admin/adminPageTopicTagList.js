import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TopicTagListBody} from "./fragment/tag/topicTagListBody"
import {ElementId} from "../domain/elementId"
import {AdminHeader} from "./adminHeader";

export const AdminPageTopicTagList = () => {

    return (
        <div>
            <AdminHeader/>
            <TopicTagListBody
                formKey={new ElementId("topicTags")}
            />
        </div>
    );
};
