import React from 'react';
import {Route, Routes} from "react-router-dom";
import {NotFound} from "./notfound";

import {AdminEntrance} from './admin/adminEntrance';
import {Sandbox} from './admin/sandbox';
import {AdminPageArticleList} from './admin/adminPageArticleList';
import {AdminPageArticle} from './admin/adminPageArticle';
import {AdminPageTopicTagList} from './admin/adminPageTopicTagList';
import {GoogleReCaptchaProvider} from 'react-google-recaptcha-v3';
import {AdminProtectedRoute} from "./admin/adminProtectedRoute";
import {AdminPageLogin} from "./admin/adminPageLogin";
import {AdminPageImages} from "./admin/adminPageImages";
import {AdminPageUsers} from "./admin/adminPageUsers";
import {AdminPageUserComments} from "./admin/adminPageUserComments";
import {AdminPageArticleNew} from "./admin/adminPageArticleNew";
import {PublicPageHome} from "./public/publicPageHome";
import {PublicPageArticleView} from "./public/publicPageArticleView";
import {PublicPageArticleList} from "./public/publicPageArticleList";
import {PublicPageAbout} from "./public/publicPageAbout";
import {Footer} from "./footer";


console.log("App.js");
const googleReCaptchaSiteKey = "6Lf8kaIqAAAAAHFHNP-9SMGywt4klnqx3VBOTULt";


const App = () => {


    return (
        <div className="App">
            <Routes>
                <Route path="/*" element={<NotFound/>}/> {}

                <Route path="/" element={
                    <PublicPageHome/>
                }/> {}
                <Route path="/articles" element={
                    <PublicPageArticleList/>
                }/> {}
                <Route path="/articles/:articleId" element={
                    <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                        <PublicPageArticleView/>
                    </GoogleReCaptchaProvider>
                }/> {}
                <Route path="/about" element={
                    <PublicPageAbout/>
                }/> {}


                <Route path="/admin/login" element={
                    <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                        <AdminPageLogin/>
                    </GoogleReCaptchaProvider>
                }/> {}
                <Route path="/admin" element={
                    <AdminProtectedRoute>
                        <AdminEntrance/>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/images" element={
                    <AdminProtectedRoute>
                        <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                            <AdminPageImages/>
                        </GoogleReCaptchaProvider>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/users" element={
                    <AdminProtectedRoute>
                        <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                            <AdminPageUsers/>
                        </GoogleReCaptchaProvider>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/usercomments" element={
                    <AdminProtectedRoute>
                        <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                            <AdminPageUserComments/>
                        </GoogleReCaptchaProvider>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/sandbox" element={
                    <AdminProtectedRoute>
                        <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                            <Sandbox/>
                        </GoogleReCaptchaProvider>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/articles" element={
                    <AdminProtectedRoute>
                        <AdminPageArticleList/>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/articles/:articleId" element={
                    <AdminProtectedRoute>
                        <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                            <AdminPageArticle/>
                        </GoogleReCaptchaProvider>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/articles/new" element={
                    <AdminProtectedRoute>
                        <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                            <AdminPageArticleNew/>
                        </GoogleReCaptchaProvider>
                    </AdminProtectedRoute>
                }/> {}

                <Route path="/admin/topictags" element={
                    <AdminProtectedRoute>
                        <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                            <AdminPageTopicTagList/>
                        </GoogleReCaptchaProvider>
                    </AdminProtectedRoute>
                }/> {}


            </Routes>
            <Footer/>
        </div>
    );
}


export default App;