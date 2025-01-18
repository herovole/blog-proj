import React from 'react';
import {AdminCommentEditorUnit} from './adminCommentEditorUnit'
import {ElementId} from "../../../../domain/elementId/elementId";
import {TagUnits} from "../../atomic/tagselectingform/tagUnits";
import {CommentUnits} from "../../../../domain/comment/commentUnits";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";

type AdminCommentEditorProps = {
    postKey: ElementId;
    content: CommentUnits;
    countryTagsOptions: TagUnits;
}
export const AdminCommentEditor: React.FC<AdminCommentEditorProps> = ({
                                                                          postKey,
                                                                          content,
                                                                          countryTagsOptions
                                                                      }) => {

    const [countAddedComments, setCountAddedComments] = React.useState<number>(0);

    const handleAddComment = (): void => {
        setCountAddedComments(n => n + 1);
    }

    const elementNumber = content.commentUnits.length;

        return (
            <div>
                <div>


                    {content.commentUnits.map((commentUnit, i) => {
                        const unit: SourceCommentUnit = commentUnit as SourceCommentUnit;
                        return (
                            <div key={i} className="flex-container">
                                {[...Array(unit.depth)].map((_, j) => (
                                    <span key={j} className="left-space"/>
                                ))}
                                <AdminCommentEditorUnit
                                    postKey={postKey.append(i.toString())}
                                    countryTagsOptions={countryTagsOptions}
                                    content={unit}
                                />
                            </div>
                        );
                    })}

                    {Array.from({length: countAddedComments}).map((_, index) => (
                        <AdminCommentEditorUnit
                            postKey={postKey.append((elementNumber + index).toString())}
                            countryTagsOptions={countryTagsOptions}
                            content={SourceCommentUnit.empty()}
                        />
                    ))}

                    <p>
                        <button type="button" onClick={this.handleAddComment}>Add Comment</button>
                    </p>
                </div>
            </div>
        );
    }
}

