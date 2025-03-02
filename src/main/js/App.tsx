import React from 'react';
import {PublicPageArticleView} from "./public/publicPageArticleView";
import {Route, Routes} from "react-router-dom";
import {About} from "./about";
import {Contact} from "./contact";
import {NotFound} from "./notfound";
import {Footer} from "./footer";

import {AdminEntrance} from './admin/adminEntrance';
import {Sandbox} from './admin/sandbox';
import {AdminPageArticleList} from './admin/adminPageArticleList';
import {AdminPageArticle} from './admin/adminPageArticle';
import {AdminPageTopicTagList} from './admin/adminPageTopicTagList';
import {GoogleReCaptchaProvider} from 'react-google-recaptcha-v3';
import {PublicPageArticleList} from "./public/publicPageArticleList";
import {AdminProtectedRoute} from "./admin/adminProtectedRoute";
import {AdminPageLogin} from "./admin/adminPageLogin";
import {AdminPageImages} from "./admin/adminPageImages";
import {AdminPageUsers} from "./admin/adminPageUsers";
import {AdminPageUserComments} from "./admin/adminPageUserComments";
import {PublicPageHome} from "./public/publicPageHome";
import {AdminPageArticleNew} from "./admin/adminPageArticleNew";


console.log("App.js");
const googleReCaptchaSiteKey = "6Lf8kaIqAAAAAHFHNP-9SMGywt4klnqx3VBOTULt";


const App = () => {


    return (
        <div className="App">
            <h1>これはRoutesの外側のエレメント</h1>

            <Routes>
                <Route path="/about" element={<About/>}/> {}
                <Route path="/contact" element={<Contact message="Hello"/>}/> {}
                <Route path="/*" element={<NotFound/>}/> {}

                <Route path="/about" element={<About/>}/> {}

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

            <h1>これもRoutesの外側のエレメント</h1>
            <Footer/>
        </div>
    );
}


export default App;