import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {TopicTagListBody} from "./fragment/tag/topicTagListBody"
import {ElementId} from "../domain/elementId"

export const PageTopicTagList = () => {

    return (
        <TopicTagListBody
            formKey={new ElementId("topicTags")}
         />
    );
};
