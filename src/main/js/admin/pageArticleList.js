import React, { useState, useRef } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleListBody} from "./fragment/articleList/articleListBody"
import {ElementId} from "../domain/elementId"

export const PageArticleList = () => {

    const { articleId } = useParams();

    return (
        <ArticleListBody
            formKey={new ElementId("articleList")}
            directoryToIndividualPage={"/admin/articles"}
         />
    );
};
