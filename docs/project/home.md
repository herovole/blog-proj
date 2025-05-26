# Project Description

## Prerequisites

- My Country  
  Japan

- Matome Blog(まとめブログ)  
  There's a conventional blog style called "Matome Blog(まとめブログ)" in my country, which literally means "summarization blog".  
  What it does is to copy posts from the public internet forum, reorganize them, and decorate them into the articles.

- Translated Matome Blog(翻訳まとめブログ)  
  A subgenre of Matome Blog. In this case, a blogger chooses non-Japanese public internet forums as their comment sources e.g. 4chan.org, reddit.com, then, gets them translated to Japanese language to build the articles.

- Matome Antenna(まとめアンテナ)  
  Another kind of webservices that holds a variety of RSS from various Matome Blogs to offer public users the links to their articles.  
  Matome Blogs basically need help from such Matome Antennas to acquire constant inflow of the public users.


## Overview

The project contains the software resources required to run my Translated Matome Blog.  

- Product URL : https://at-archives.com/
- Staging URL : https://stg.at-archives.com/ (It's often turned off to reduce my AWS expenditure.)  
- Service Name : 輸入雑貨 / Imported Leisure
- Types of Contents
  - For public users : Read, search, and add comments to the articles I've built.
  - For admin users : Edit articles, manage user comments, etc...
- Tech Stacks : React, Spring Boot, MySQL, AWS
- Progress : Already rolled out. System updates are still ongoing.
- Warning 
  - 1 : The service is targeted to Japanese users. Currently all the instructions are written in Japanese.  
  - 2 : Gadgets on the right side of the pages are led to external web sites. They are required to be placed there for the basic rules for application to the external Matome Antennas.  
- Contact : signat12345ure@gmail.com

## Details

- [Website Description (React)](./website.md)
- [Backend Description (Java, Spring Boot)](./backend.md)
- [Infrastructures (Amazon AWS)](./infrastructures.md)
