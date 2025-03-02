import React from 'react';
import {IdsEditingForm} from '../../atomic/idsEditingForm';
import {TextEditingForm} from '../../atomic/textEditingForm';
import {BooleanSelectingForm} from '../../atomic/booleanSelectingForm';
import {TagSelectingForm} from '../../atomic/tagselectingform/tagSelectingForm';
import {TagUnits} from '../../atomic/tagselectingform/tagUnits';
import {ElementId} from "../../../../domain/elementId/elementId";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {ResourceManagement} from "../../../../service/resourceManagement";

type AdminCommentEditorUnitProps = {
    postKey: ElementId;
    content: SourceCommentUnit | null;
}
export const AdminCommentEditorUnit: React.FC<AdminCommentEditorUnitProps> = ({
                                                                                  postKey,
                                                                                  content,
                                                                              }) => {

    React.useEffect(() => {
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
    }, []);
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

    return (
        <div className="comment-section">
            <div className="source-comment-individual">
                <div className="comment-edit-item-line"><span
                    className="comment-edit-label">CommentSerialNumber : </span>
                    <IdsEditingForm postKey={postKey.append("commentId")} isFixed={true}>
                        {content ? content.body.commentSerialNumber : ""}
                    </IdsEditingForm>
                </div>
                <div className="comment-edit-item-line"><span className="comment-edit-label">CommentID : </span>
                    <IdsEditingForm postKey={postKey.append("commentId")}>
                        {content ? content.body.commentId : ""}
                    </IdsEditingForm>
                </div>
                <div className="comment-edit-item-line"><span className="comment-edit-label">Country : </span>
                    <TagSelectingForm
                        postKey={postKey.append("country")}
                        candidates={countryTagsOptions || TagUnits.empty()}
                    >
                        {content ? content.body.country : ""}
                    </TagSelectingForm>
                </div>
                <div className="comment-edit-item-line"><span className="comment-edit-label">IsHidden : </span>
                    <BooleanSelectingForm postKey={postKey.append("isHidden")}>
                        {content ? content.body.isHidden : ""}
                    </BooleanSelectingForm>
                </div>
                <div className="comment-edit-item-line"><span
                    className="comment-edit-label">Referring CommentIds : </span>
                    <IdsEditingForm postKey={postKey.append("referringCommentIds")}>
                        {content ? content.body.referringCommentIds : ""}
                    </IdsEditingForm>
                </div>
                <div className="source-comment-text">
                    <TextEditingForm
                        postKey={postKey.append("text")}
                        width="400px"
                        height="70px"
                    >
                        {content ? content.body.commentText : ""}
                    </TextEditingForm>
                </div>
            </div>
        </div>
    );
}

