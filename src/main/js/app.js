import React from 'react';
import {PublicPageArticleView} from "./public/publicPageArticleView";
import {Route, Routes} from "react-router-dom"; // 追加
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


console.log("app.js");
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
                <Route path="/admin" element={<AdminEntrance/>}/> {}
                <Route path="/admin/sandbox" element={<Sandbox/>}/> {}


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