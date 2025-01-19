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
        <form onSubmit={handleSubmit}>
            <div className="flex-container">
                <button type="submit">Submit</button>
                <div>
                    <div className="flex-container">
                        <p className="item-title">Image</p>
                        <ImageSelectingModal
                            postKey={postKey.append("imageName")}
                            imageName={content.imageName}
                        />
                    </div>
                    <div className="flex-container">
                        <p className="item-title">Article ID</p>
                        <IdsEditingForm
                            postKey={postKey.append("articleId")}
                            ids={content.articleId}
                            isFixed={true}
                        />
                    </div>
                    <div>
                        <p className="item-title-large">Source Info</p>
                        <p>
                            <span className="item-title">URL</span>
                            <TextEditingForm
                                postKey={postKey.append("sourceUrl")}>
                                {content.sourceUrl}
                            </TextEditingForm>
                        </p>
                        <p>
                            <span className="item-title">Title</span>
                            <TextEditingForm
                                postKey={postKey.append("sourceTitle")}>
                                {content.sourceTitle}
                            </TextEditingForm>
                        </p>
                        <p>
                            <span className="item-title">Date</span>
                            <DateSelectingForm
                                postKey={postKey.append("sourceDate")}>
                                {content.sourceDate}
                            </DateSelectingForm>
                        </p>
                    </div>
                    <div className="flex-container">
                        <p className="item-title">Published?</p>
                        <BooleanSelectingForm
                            postKey={postKey.append("isPublished")}>
                            {content.isPublished}
                        </BooleanSelectingForm>
                    </div>
                    <div className="flex-container">
                        <p className="item-title">Editors</p>
                        <TagSelectingForm
                            allowsMultipleOptions={true}
                            postKey={postKey.append("editors")}
                            candidates={topicTagsOptions}
                            isFixed={true}
                            selectedTagIds={[]}
                        />
                    </div>
                    <div>
                        <p className="item-title">Topic Tags</p>
                        <TagSelectingForm
                            allowsMultipleOptions={true}
                            postKey={postKey.append("topicTags")}
                            candidates={topicTagsOptions}
                            selectedTagIds={content.topicTags}
                        />
                    </div>
                    <div>
                        <p className="item-title">Countries</p>
                        <TagSelectingForm
                            allowsMultipleOptions={true}
                            postKey={postKey.append("countries")}
                            candidates={countryTagsOptions}
                            selectedTagIds={content.countries}
                        />
                    </div>
                    <div>
                        <p className="item-title">Article Title</p>
                        <TextEditingForm
                            postKey={postKey.append("articleTitle")}>
                            {content.title}
                        </TextEditingForm>
                    </div>
                    <div>
                        <p className="item-title">Article Text</p>
                        <TextEditingForm
                            postKey={postKey.append("articleText")}
                            isLarge={true}
                        >
                            {content.text}
                        </TextEditingForm>
                    </div>
                    <div>
                        <p className="item-title-large">Original Comments</p>
                        <AdminCommentEditor
                            postKey={postKey.append("originalComments")}
                            content={content.sourceComments}
                            countryTagsOptions={countryTagsOptions}
                        />
                    </div>
                </div>
            </div>
        </form>
    );
}

