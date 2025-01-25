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


type AdminArticleBodyProps = {
    postKey: ElementId;
    content: Article;
    topicTagsOptions: TagUnits;
    countryTagsOptions: TagUnits;
}
export const AdminArticleBody: React.FC<AdminArticleBodyProps> = ({
                                                                      postKey,
                                                                      content,
                                                                      topicTagsOptions,
                                                                      countryTagsOptions
                                                                  }) => {
    const [refresh, setRefresh] = React.useState(false);
    const [message, setMessage] = React.useState("");
    const articleService: ArticleService = new ArticleService();

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {
        event.preventDefault(); // Prevent page reload
        const formData = new FormData(event.target as HTMLFormElement);
        const output: BasicApiResult = await articleService.editArticle(formData);
        if (output.isSuccessful()) {
            setMessage(output.getMessage("記事編集"));
            setRefresh(r => !r);
        } else {
            setMessage(output.getMessage("記事編集"));
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <button type="submit">Submit</button>
                <div>
                    <div className="flex-container">
                        <p className="article-edit-title">Image</p>
                        <ImageSelectingModal
                            imageName={content.imageName}
                        />
                    </div>
                    <div className="flex-container">
                        <p className="article-edit-title">Article ID</p>
                        <IdsEditingForm
                            postKey={postKey.append("articleId")}
                            isFixed={true}
                        >{content.articleId}</IdsEditingForm>
                    </div>
                    <div>
                        <p className="article-edit-title-large">Source Info</p>
                        <p>
                            <span className="article-edit-title">URL</span>
                            <TextEditingForm
                                postKey={postKey.append("sourceUrl")}>
                                {content.sourceUrl}
                            </TextEditingForm>
                        </p>
                        <p>
                            <span className="article-edit-title">Title</span>
                            <TextEditingForm
                                postKey={postKey.append("sourceTitle")}>
                                {content.sourceTitle}
                            </TextEditingForm>
                        </p>
                        <p>
                            <span className="article-edit-title">Date</span>
                            <DateSelectingForm
                                postKey={postKey.append("sourceDate")}>
                                {content.sourceDate}
                            </DateSelectingForm>
                        </p>
                    </div>
                    <div className="flex-container">
                        <p className="article-edit-title">Published?</p>
                        <BooleanSelectingForm
                            postKey={postKey.append("isPublished")}>
                            {content.isPublished}
                        </BooleanSelectingForm>
                    </div>
                    <div className="flex-container">
                        <p className="article-edit-title">Editors</p>
                        <TagSelectingForm
                            allowsMultipleOptions={true}
                            postKey={postKey.append("editors")}
                            candidates={topicTagsOptions}
                            isFixed={true}
                            children={[]}
                        />
                    </div>
                    <div>
                        <p className="article-edit-title">Topic Tags</p>
                        <TagSelectingForm
                            allowsMultipleOptions={true}
                            postKey={postKey.append("topicTags")}
                            candidates={topicTagsOptions}
                            children={content.topicTags}
                        />
                    </div>
                    <div>
                        <p className="article-edit-title">Countries</p>
                        <TagSelectingForm
                            allowsMultipleOptions={true}
                            postKey={postKey.append("countries")}
                            candidates={countryTagsOptions}
                            children={content.countries}
                        />
                    </div>
                    <div>
                        <p className="article-edit-title">Article Title</p>
                        <TextEditingForm
                            postKey={postKey.append("articleTitle")}>
                            {content.title}
                        </TextEditingForm>
                    </div>
                    <div>
                        <p className="article-edit-title">Article Text</p>
                        <TextEditingForm
                            postKey={postKey.append("articleText")}
                            width={"400px"}
                            height={"70px"}
                        >
                            {content.text}
                        </TextEditingForm>
                    </div>
                    <div>
                        <p className="article-edit-title-large">Source Comments</p>
                        <AdminCommentEditor
                            postKey={postKey.append("originalComments")}
                            content={content.sourceComments}
                            countryTagsOptions={countryTagsOptions}
                        />
                    </div>
                </div>
            </form>
            <div>
                <p className="article-edit-title-large">User Comments</p>
                <PublicUserCommentView
                    commentUnits={content.sourceComments}
                />
            </div>
        </div>
    );
}

