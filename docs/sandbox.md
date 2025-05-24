

# Project Description

## Prerequisites

- My Country  
    Japan

- Matome Blog(まとめブログ)  
    There's a conventional blog style called "Matome Blog(まとめブログ)" in my country, which literally means "summarization blog".  
    What it does is to copy posts from the public internet forum, reorganize them, and decorate them into the articles.  

- Translated Matome Blog(翻訳まとめブログ)  
    Subgenre of Matome Blog. In this case, a blogger chooses non-Japanese public internet forums as their sources e.g. 4chan.org, reddit.com, then, gets them translated to Japanese language to build the articles.

- Matome Antenna(まとめアンテナ)
    Another kind of webservices that holds a variety of RSS from various Matome Blogs to offer links to their articles.  
    Matome Blogs basically need help from such Matome Antennas to acquire constant inflow of the public users.  


## Overview

The project contains the software resources required to run my Translated Matome Blog (https://at-archives.com/).  


## Site Description

(*) ... protected by Google ReCaptcha  

### Public User Services
  #### Home
  [publicPageHome.tsx](/src/main/js/public/publicPageHome.tsx)  
  The website home.  
  A public user can see a list of recently added articles.  

  #### Article  
  [publicPageArticleView.tsx](/src/main/js/public/publicPageArticleView.tsx)  
  An individual article page.  
  It's composed of the following sections.  
  - Cover  
    Contains an image, source URL, and the timestamp of publication.  
  - Source Comments  
    Contains translated comments from the source URL.  
  - User Comments  
    Contains comments from the public users. For each comment, a user can  
    - Rate the comment (*) (Good/Bad)
    - Report the comment (*) (opens a modal window)
  - User Comment Form (*)  
    A public user posts a comment.  (opens a modal window)

  #### List  
  [publicPageArticleList.tsx](/src/main/js/public/publicPageArticleList.tsx)  
  The page where a public user can search articles in the past.  
  It's composed of the following sections.  
  - Search Menu  
    A public user inputs  
    - number of items per page
    - search keywords
    - topic classification
    - country
    - date range  
    to obtain a list of articles as a result.  
  - Search Result  
    The list of the articles specified by the search menu.  

  #### About  
  [publicPageAbout.tsx](/src/main/js/public/publicPageAbout.tsx)  
  Website information. RSS, my mail address, etc...  

  #### (Common Components)
  Also, these pages have common components.  
  - Header  
    [publicHeader.tsx](/src/main/js/public/fragment/publicHeader.tsx)  
    A small menu for switching between Home, List, and About.  
  - Footer  
    [footer.js](/Users/devel/Documents/home/git/blog-proj/src/main/js/footer.js)  
    Almost the same to the Header. Has a link to the Admin Entrance.  
  - Left Gadgets  
    [gadgetsLeft.tsx](/src/main/js/public/fragment/gadget/gadgetsLeft.tsx)  
    Introduces relevant articles based on  
    - the timestamps of the latest user comments.
    - topic classification of the latest article visited by a user.
    - country of the latest article visited by a user.
  - Right Gadgets  
    [gadgetsRight.tsx](/src/main/js/public/fragment/gadget/gadgetsRight.tsx)  
    External links to other Translated/non-Translated Matome Blogs required by the terms of exchange with Matome Antennas.  

### Admin Services

  #### Login Form (*)  
  [adminPageLogin.tsx](/src/main/js/admin/adminPageLogin.tsx)  
  2-Factor-Auth. A user needs  
  - phase1 : user name, password  
  - phase2 : user name, password, 6-digit phrase sent by e-mail  

  #### Admin Home
  [adminEntrance.tsx](/src/main/js/admin/adminEntrance.tsx)  
  There's a button to regenerate RSS files.  

  #### Admin Article Editor (New Article) (Update)  
  [adminPageArticleNew.tsx](/src/main/js/admin/adminPageArticleNew.tsx)  
  [adminPageArticle.tsx](/src/main/js/admin/adminPageArticle.tsx)  
  An admin user builds an article here. The menu includes  
  - main image
  - title
  - main text
  - publish/not flag, publish timestamp
  - source info (URL, original title, date)
  - source comments 
    - comment number
    - nationality
    - comment text
    - referring comment number

  #### Admin Article List
  [adminPageArticleList.tsx](/src/main/js/admin/adminPageArticleList.tsx)  
  In addition to the Search options for the public users, it offers  
  - published/not ... an admin user can list non-published articles.  
  - sort options ... an admin user can sort the articles by ID and the timestamp of the latest user comments, in addition to the timestamp of article publication which is the default.  
  
  #### Admin Topic Tags Management
  [adminPageTopicTagList.js](/src/main/js/admin/adminPageTopicTagList.js)  
  Offers services to Create/Read/Update topic classifications  
  e.g. Society, IT-Digital, Life

  #### Admin Image Management
  [adminPageImages.tsx](/src/main/js/admin/adminPageImages.tsx)  
  Offers services to Create/Read/Delete cover images

  #### Admin AdminUser Management 
  [adminPageUsers.tsx](/src/main/js/admin/adminPageUsers.tsx)  
  Offers services to Create/Read/Update Admin users

  #### Admin Public Comments Management (*)
  [adminPageUserComments.tsx](/src/main/js/admin/adminPageUserComments.tsx)  
  Offers services to list the public user comments and the reports they receive.  
  An admin user can search comments by  
  - keywords
  - date range
  - whether it's reported by other users or not
  - whether a comment has reports not handled by the admin users
  
  and for each comment
  - hide/show
  - ban public user by cookie ID
  - ban public user by IP
  - check whether each report is handled or not

## 3rd Party Frontend Components
- Google ReCaptcha v3  
  placed on forms with Post/Put methods with public user interaction or some critical Admin features.   
- Livedoor 相互(mutual) RSS   
  My webservice has an account for it. It offers a portal to register RSS files of external Matome Antenna to build external links automatically.  
  

## Backend Security

Backend REST API Endpoints are protected by some security measures, which are mainly called by Filters (a Spring Boot concept).  


1. [WrapRequestOnFilter.java](/src/main/java/org/herovole/blogproj/presentation/filter/WrapRequestOnFilter.java)  
  Preparation. It just converts the request to a cached data type.  
  (Without this, the http request takes form of stream that evaporates upon first read.)  

2. [SystemThreateningPhraseFilter.java](/src/main/java/org/herovole/blogproj/presentation/filter/SystemThreateningPhraseFilter.java)  
  Protects the entire service.  
  Scans the strings in the request. Checks all phrases potentially harmful to the system. e.g. "update ~ set ~" used often for SQL Injection.  

3. [TrackPublicUserByFilter.java](/src/main/java/org/herovole/blogproj/presentation/filter/TrackPublicUserByFilter.java)
  Checks its cookie to see if the user has ever previously visited. Assign new ID if he/she is a new visitor.   

4. [BotFilter.java](/src/main/java/org/herovole/blogproj/presentation/filter/BotFilter.java)
  Protects some publicly exposed user interaction operations.  
  Checks Google ReCaptcha score to see whether the access is organic or not.  

5. [BanFilter.java](/src/main/java/org/herovole/blogproj/presentation/filter/BanFilter.java)
  Protects the public user comment functions.  
  Checks its public user ID and IP to see whether he/she is banned from the user comment functions.  

6. [AuthFilter.java](/src/main/java/org/herovole/blogproj/presentation/filter/AuthFilter.java)
  Protects the Admin only operations.  
  Checks its token to see if he/she is an admin user.  



