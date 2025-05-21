# TODO

## Issues

- i001  
  The image selecting modal is too huge.   
  An editor can't control the whole feature without shrinking the browser.  
  branch : feature/i001_20250406  
  version : 1.0.1-beta  
- i002  
  Transactions that haven't been executed remain in the cache and affect the next operation.  
  branch : i002_20250406  
  version : 1.0.1-beta  
- i003  
  No defense on domain level when an image name is too long.  
  results in the SQL error.  
  branch : feature/i003_20250406  
  version : 1.0.1-beta  
- i004  
  There was a case that an image removing operation failed, saying that the target image wasn't found.  
  branch : i004_20250406  
  version : 1.0.1-beta  
- i005  
  Syntax Error on SQL to delete a country tag.  
  branch : feature/i005_20250406  
  version : 1.0.1-beta  
- i006  
  Home Title has redundant declaration of the website  
  branch : feature/i006_20250411  
  version : 1.2.1-beta
- i007  
  Google search result picks up the Site Information page.  
  branch : none  
  version : 1.3.0-beta  
- i008  
  isHidden attribute of Source Comment editing fragment has a possibility that it doesn't reflect the actual value.  
  branch : feature/i008_20250417  
  version : 1.3.1-beta
- i009  
  failure in calculating method of pagination : there can be cases that Math.max is miscoded Math.min.  
  branch : feature/i009_20250421  
  version : 1.4.6-beta  
- i010  
  RSS links in iframe do not send users with the referrer set to my domain.  
  branch : feature/i010_20250421  
  version : 1.4.7-beta  
- i011  
  Let an article editing button fail, when the article title is empty.  
  branch : feature/i011_20250423  
  version : 1.4.9-beta  
- i012  
  (Let an article editing button fail, when a source comment refers to a comment with an older number.)  
  Changed the plan : just ignore the wrong referred ID.  
  branch : feature/i012_20250423  
  version : 1.4.9-beta  
- i013  
  Alter format of the article publishing timestamp  
  branch : feature/i013_20250430  
  version : 1.5.3-beta  
- i014  
  Fix : a long title gets abbreviated on the top page  
  branch : feature/i014_20250425  
  version : 1.4.9-beta  
- i015  
  Fix : a long title gets abbreviated without ... on the list page  
  branch : feature/i015_20250425  
  version : 1.4.9-beta  
- i016  
  Needs automatic reloading after getting an editing page updated.  
- i017  
  Get each method converting a domain object to a JPA entity have concrete class instead of an abstract one for its argument.  
- i018  
  Get a custom exception "EmptyRecordException" and "IncompatibleUpdateException" abolished or standardized.  
- i019  
  Get every UnSupportedOperationException have argument String. 
- i020  
  Fix : The pager doesn't display pages from 2 ahead.  
  Reported : 20250428  
  branch : feature/i020_20250428  
  version : 1.5.1-beta  
- i021  
  Fix : Server error when updating an article without a country tag.  
  Reported : 20250428  
  branch : feature/i021_20250428  
  version : 1.5.1-beta  
- i022  
  To have RSS description surrounded by <![CDATA[ ]]>  
  Reported : 20250428  
  branch : feature/i022_20250428  
  version : 1.5.2-beta  
- i023  
  Space separated search keywords do not handle 全角 space  
  Reported : 20250428  
  branch : feature/i023_20250430  
  version : 1.5.3-beta  
- i024  
  SearchArticlesPresenter losing @RequestScope causing the background UseCaseErrorType retained
  through multiple requests. Needs to recover the annotation and needs another way to handle the initial RSS generation.  
  Reported : 20250501
  branch : feature/i024_20250502  
  version : 1.6.1-beta
- i025  
  There's a case that manual RSS generation doesn't update the RSS files, while it's performed by redeployment of the project.  
  Reported : 20250506  
- i026  
  Background positions are strange when opened by smartphones.  
  Reported : 20250507  
  branch : feature/i026_20250520  
  version : 1.6.4-beta  
- i027  
  Delete description of the webpack server.  
  Reported : 20250507  
  branch : feature/i027_20250520  
  version : 1.6.4-beta  
- i028  
  sort articles order by publish_timestamp instead of by id.  
  Reported : 20250508  
- i029  
  shrink the max size of the comment width, to the same degree to that in editing page.  
  Reported : 20250509  
  branch : feature/i029_20250521  
  version : 1.6.4-beta
- i030  
  More relaxing messages for the usage guides of the comment function.  
  Reported : 20250511  
  branch : feature/i030_20250521  
  version : 1.6.4-beta

## System Updates

- u001  
  Include country tags and topic tags in search options.  
  branch : feature/u001_20250425  
  version : 1.5.0-beta  
- u002  
  Make country tags and topic tags clickable for instant search.  
  branch : feature/u002_20250426  
  version : 1.5.0-beta  
- u003  
  Highlight comment anchors
- u006  
  Prepare Country icons  
  branch : feature/u006_20250418  
  version : 1.4.1-beta  
- u007  
  Prepare RSS 1.0  
  branch : feature/u007_20250409  
  version : 1.1.0-beta  
- u008  
  Retain search/page conditions of the article list fragments when page reloading operation is performed.  
  (resolved by u011)  
- u009  
  Place RSS files right next to index.html  
  branch : feature/u009_20250413  
  version : 1.3.0-beta  
- u010  
  Stop showing source comments with isHidden=true  
  branch : feature/u010_20250417  
  version : 1.4.0-beta  
- u011  
  Get search conditions linked with the get parameters.  
  branch : feature/i011_20250426  
  version : 1.5.0-beta  
- u012  
  Article Preview  
- u013  
  Get each article carry the publishing timestamp.  
  branch : feature/u013_20250430  
  version : 1.6.0-beta  
- u014  
  User View Admin page  
- u015  
  Take referer data.  
- u016  
  Gadget(left) of an article list aligned by the timestamps of the latest comments.  
  branch : feature/u016_20250520  
  version : 1.7.0-beta  
- u017  
  Introduce a flag and a tag for German language sphere.  
  branch : feature/u017_20250426  
  version : 1.5.0-beta  
- u018  
  Introduce RSS for German language.  
  branch : feature/u018_20250426  
  version : 1.5.0-beta  
- u019  
  Function to rebuild sitemap.xml.  
- u020  
  Function to build dummy HTMLs for respective articles.  
- u021  
  Generate RSS automatically upon deployment.  
  Reported : 20250428  
  branch : feature/u021_20250430  
  version : 1.6.0-beta  
- u022  
  "Go to top" following widget  
  Reported : 20250507  
- u023  
  Add (海外の反応) to RSS articles titles  
  Reported : 20250508

  
## Layout Updates

- u004  
  Prepare RSS icon  
  branch : feature/u004_20250409  
  version : 1.2.0-beta
- u005  
  Prepare Link to X  
  branch : feature/u005_20250409  
  version : 1.2.0-beta  
- a001  
  Prepare blog.with2.net(人気ブログランキング) banners  
  branch : feature/a001_20250413  
  version : 1.2.2-beta  
- a002  
  Gadget(right) of external links.  
  branch : feature/a002_20250417  
  version : 1.3.1-beta  
- a003
  Stop showing 4chan reference temporarily while that forum is under control of the crackers.  
  branch : feature/a003_20250418  
  version : 1.4.2-beta  
- a004
  modify ExtLinks gadget design  
  branch : feature/a004_20250419  
  version : 1.4.3-beta, 1.4.5-beta  
- a005
  apply background color  
  branch : feature/a005_20250420  
  version : 1.4.4-beta  
- a006  
  registration requests to two other antennas  
  branch : feature/a006_20250421  
  version : 1.4.8-beta  
- a007  
  Remove a003  
  Reported : 20250502  
  branch : feature/a007_20250502  
  version : 1.6.2-beta  
- a008  
  Add RSS widget for misc genre  
  Reported : 20250503  
  branch : feature/a008_20250503  
  version : 1.6.3-beta  

