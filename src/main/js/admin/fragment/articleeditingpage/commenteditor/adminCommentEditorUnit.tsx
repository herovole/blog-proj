import React from 'react';
import {IdsEditingForm} from '../../atomic/idsEditingForm';
import {TextEditingForm} from '../../atomic/textEditingForm';
import {BooleanSelectingForm} from '../../atomic/booleanSelectingForm';
import {TagSelectingForm} from '../../atomic/tagselectingform/tagSelectingForm';
import {TagUnits} from '../../atomic/tagselectingform/tagUnits';
import {ElementId} from "../../../../domain/elementId/elementId";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";

type AdminCommentEditorUnitProps = {
    postKey: ElementId;
    content: SourceCommentUnit;
    countryTagsOptions: TagUnits;
}
export const AdminCommentEditorUnit: React.FC<AdminCommentEditorUnitProps> = ({
                                                                                  postKey,
                                                                                  content,
                                                                                  countryTagsOptions
                                                                              }) => {

    if (content.body && !countryTagsOptions.isEmpty()) {
        return (
            <div className="comment-section">
                <div className="source-comment-individual">
                    <div className="comment-edit-item-line"><span className="comment-edit-label">CommentID : </span>
                        <IdsEditingForm
                            postKey={postKey.append("commentId")}
                            ids={content.body.commentId}
                        />
                    </div>
                    <div className="comment-edit-item-line"><span className="comment-edit-label">Country : </span>
                        <TagSelectingForm
                            postKey={postKey.append("country")}
                            candidates={countryTagsOptions || TagUnits.empty()}
                            selectedTagIds={content.body.country}
                        />
                    </div>
                    <div className="comment-edit-item-line"><span className="comment-edit-label">IsHidden : </span>
                        <BooleanSelectingForm postKey={postKey.append("isHidden")}>
                            {content.body.isHidden}
                        </BooleanSelectingForm>
                    </div>
                    <div className="comment-edit-item-line"><span
                        className="comment-edit-label">Referring CommentIds : </span>
                        <IdsEditingForm
                            postKey={postKey.append("referringCommentIds")}
                            ids={content.body.referringCommentIds}
                        />
                    </div>
                    <div className="source-comment-text">
                        <TextEditingForm
                            postKey={postKey.append("text")}
                            isLarge={true}
                        >
                            {content.body.commentText}
                        </TextEditingForm>
                    </div>
                </div>
            </div>
        );
    } else {
        return <div>Loading...</div>
    }
}

