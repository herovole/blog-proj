import React from 'react';

import {IdsEditingForm} from '../atomic/idsEditingForm';
import {TextEditingForm} from '../atomic/textEditingForm';
import {ImageSelectingModal} from '../image/imageSelectingModal';
import {DateSelectingForm} from '../atomic/dateSelectingForm';
import {BooleanSelectingForm} from '../atomic/booleanSelectingForm';
import {AdminCommentEditor} from './commenteditor/adminCommentEditor';
import {TagSelectingForm} from '../atomic/tagselectingform/tagSelectingForm';
import {TagUnits} from '../atomic/tagselectingform/tagUnits';
import {ElementId} from "../../../domain/elementId/elementId";
import {Article} from "../../../domain/article";
import {ArticleService} from "../../../service/articles/articleService";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {PublicUserCommentView} from "../../../public/fragment/article/usercomment/publicUserCommentView";
import {CommentUnit} from "../../../domain/comment/commentUnit";
import {ResourceManagement} from "../../../service/resourceManagement";
import {useNavigate} from "react-router-dom";
import {DateHourMinuteSelectingForm} from "../atomic/dateHourMinuteSelectingForm";


type AdminArticleBodyProps = {
    postKey: ElementId;
    content?: Article | null;
    reload?: () => void;
    pageArticleList?: string;
}
export const AdminArticleBody: React.FC<AdminArticleBodyProps> = ({
                                                                      postKey,
                                                                      content = null,
                                                                      reload = () => {
                                                                      },
                                                                      pageArticleList = "",
                                                                  }) => {
    const navigate = useNavigate();
    const [message, setMessage] = React.useState("");
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const refSourceComments = React.useRef<{ emptyAddedComments: () => void } | null>(null);
    const articleService: ArticleService = new ArticleService();

    React.useEffect(() => {
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
    }, []);

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {
        event.preventDefault(); // Prevent page reload
        const formData = new FormData(event.target as HTMLFormElement);
        const output: BasicApiResult = await articleService.editArticle(formData);
        if (output.isSuccessful()) {
            setMessage(output.getMessage("記事編集"));
            if (refSourceComments) {
                refSourceComments?.current?.emptyAddedComments();
            }
            if (content) {
                // If the operation is to modify an existing article
                reload();
            } else {
                // If the operation is to create a new article
                navigate(pageArticleList);
            }
        } else {
            setMessage(output.getMessage("記事編集"));
        }
    };
    if (topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <div>loading...</div>
    } else {
        return (
            <div>
                <form onSubmit={handleSubmit}>
                    <p>
                        <button type="submit">Submit</button>
                        <div className="comment-form-process">{message}</div>
                    </p>
                    <input type="hidden" name="requiresAuth" value={1}/>
                    <div>
                        <div className="flex-container">
                            <p className="article-edit-title">Image</p>
                            <ImageSelectingModal
                                postKey={postKey.append("imageName")}
                                imageName={content ? content.imageName : ""}
                            />
                        </div>
                        <div className="flex-container">
                            <p className="article-edit-title">Article ID</p>
                            <IdsEditingForm
                                postKey={postKey.append("articleId")}
                                isFixed={true}
                            >{content ? content.articleId : null}</IdsEditingForm>
                        </div>
                        <div>
                            <p className="article-edit-title-large">Source Info</p>
                            <p>
                                <span className="article-edit-title">URL</span>
                                <TextEditingForm
                                    postKey={postKey.append("sourceUrl")}
                                >
                                    {content ? content.sourceUrl : ""}
                                </TextEditingForm>
                            </p>
                            <p>
                                <span className="article-edit-title">Title</span>
                                <TextEditingForm
                                    postKey={postKey.append("sourceTitle")}
                                >
                                    {content ? content.sourceTitle : ""}
                                </TextEditingForm>
                            </p>
                            <p>
                                <span className="article-edit-title">Date</span>
                                <DateSelectingForm
                                    postKey={postKey.append("sourceDate")}>
                                    {content ? content.sourceDate : null}
                                </DateSelectingForm>
                            </p>
                            <p className="article-edit-title-large"/>
                        </div>
                        <div className="flex-container">
                            <p className="article-edit-title">Published?</p>
                            <BooleanSelectingForm
                                postKey={postKey.append("isPublished")}>
                                {content ? content.isPublished : false}
                            </BooleanSelectingForm>
                        </div>
                        <div className="flex-container">
                            <p className="article-edit-title">Registration Timestamp</p>
                            <DateHourMinuteSelectingForm
                                postKey={postKey.append("registrationTimestamp")}>
                                {content ? content.registrationTimestamp: null}
                            </DateHourMinuteSelectingForm>
                        </div>
                        <div className="flex-container">
                            <p className="article-edit-title">Editors</p>
                            <TagSelectingForm
                                allowsMultipleOptions={true}
                                postKey={postKey.append("editors")}
                                candidates={topicTagsOptions}
                                isFixed={true}>
                                {[]}
                            </TagSelectingForm>
                        </div>
                        <div>
                            <p className="article-edit-title">Topic Tags</p>
                            <TagSelectingForm
                                allowsMultipleOptions={true}
                                postKey={postKey.append("topicTags")}
                                candidates={topicTagsOptions}
                            >
                                {content ? content.topicTags : null}
                            </TagSelectingForm>
                        </div>
                        <div>
                            <p className="article-edit-title">Countries</p>
                            <TagSelectingForm
                                allowsMultipleOptions={true}
                                postKey={postKey.append("countries")}
                                candidates={countryTagsOptions}
                            >
                                {content ? content.countries : null}
                            </TagSelectingForm>
                        </div>
                        <div>
                            <p className="article-edit-title">Article Title</p>
                            <TextEditingForm
                                postKey={postKey.append("articleTitle")}>
                                {content ? content.title : ""}
                            </TextEditingForm>
                        </div>
                        <div>
                            <p className="article-edit-title">Article Text</p>
                            <TextEditingForm
                                postKey={postKey.append("articleText")}
                                width={"400px"}
                                height={"70px"}
                            >
                                {content ? content.text : ""}
                            </TextEditingForm>
                        </div>
                        <div>
                            <p className="article-edit-title-large">Source Comments</p>
                            <AdminCommentEditor
                                postKey={postKey.append("sourceComments")}
                                content={content ? content.sourceComments : new Array<CommentUnit>()}
                                ref={refSourceComments}
                            />
                        </div>
                    </div>
                </form>
                <div>
                    <p className="article-edit-title-large">User Comments</p>
                    <PublicUserCommentView
                        commentUnits={content ? content.userComments : new Array<CommentUnit>()}
                    />
                </div>
            </div>
        );
    }
}

