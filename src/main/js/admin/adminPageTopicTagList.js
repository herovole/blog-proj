import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TopicTagListBody} from "./fragment/tag/topicTagListBody"
import {AdminHeader} from "./fragment/adminHeader";
import {RootElementId} from "../domain/elementId/rootElementId";

export const AdminPageTopicTagList = () => {

    return (
        <div>
            <AdminHeader/>
            <TopicTagListBody
                formKey={RootElementId.valueOf("topicTags")}
            />
        </div>
    );
};
