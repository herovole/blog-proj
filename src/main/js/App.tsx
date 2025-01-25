import React from 'react';
import {PublicPageArticleView} from "./public/publicPageArticleView";
import {Route, Routes} from "react-router-dom";
import {Home} from "./home";
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
import {AdminPageNewArticle} from "./admin/adminPageNewArticle";
import {PublicPageArticleList} from "./public/publicPageArticleList";
import {AdminProtectedRoute} from "./admin/adminProtectedRoute";
import {AdminPageLogin} from "./admin/adminPageLogin";
import {AdminPageImages} from "./admin/adminPageImages";
import {AdminPageUsers} from "./admin/adminPageUsers";


console.log("App.js");
const googleReCaptchaSiteKey = "6Lf8kaIqAAAAAHFHNP-9SMGywt4klnqx3VBOTULt";


const App = () => {


    return (
        <div className="App">
            <h1>これはRoutesの外側のエレメント</h1>

            <Routes>
                <Route path="/" element={<Home/>}/> {}
                <Route path="/about" element={<About/>}/> {}
                <Route path="/contact" element={<Contact message="Hello"/>}/> {}
                <Route path="/*" element={<NotFound/>}/> {}

                <Route path="/about" element={<About/>}/> {}

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
                        <AdminPageImages/>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/users" element={
                    <AdminProtectedRoute>
                        <AdminPageUsers/>
                    </AdminProtectedRoute>
                }/> {}
                <Route path="/admin/sandbox" element={<Sandbox/>}/> {}
                <Route path="/admin/sandboxes" element={<Sandbox/>}/> {}


                <Route path="/admin/articles" element={<AdminPageArticleList/>}/> {}
                <Route path="/admin/articles/:articleId" element={<AdminPageArticle/>}/> {}
                <Route path="/admin/articles/new" element={<AdminPageNewArticle/>}/> {}

                <Route path="/admin/topictags" element={<AdminPageTopicTagList/>}/> {}

                <Route path="/articles" element={<PublicPageArticleList/>}/> {}
                <Route path="/articles/:articleId" element={
                    <GoogleReCaptchaProvider reCaptchaKey={googleReCaptchaSiteKey}>
                        <PublicPageArticleView/>
                    </GoogleReCaptchaProvider>
                }/> {}

            </Routes>

            <h1>これもRoutesの外側のエレメント</h1>
            <Footer/>
        </div>
    );
}


export default App;