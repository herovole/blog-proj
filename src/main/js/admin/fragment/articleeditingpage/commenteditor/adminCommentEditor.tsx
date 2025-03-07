import React, {forwardRef} from 'react';
import {AdminCommentEditorUnit} from './adminCommentEditorUnit'
import {ElementId} from "../../../../domain/elementId/elementId";
import {CommentUnit} from "../../../../domain/comment/commentUnit";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";

type AdminCommentEditorHandle = {
    emptyAddedComments: () => void;
}
type AdminCommentEditorProps = {
    postKey: ElementId;
    content: ReadonlyArray<CommentUnit>;
}
export const AdminCommentEditor = forwardRef<AdminCommentEditorHandle, AdminCommentEditorProps>(({
                                                                                                     postKey,
                                                                                                     content,
                                                                                                 }, ref) => {

    const [countAddedComments, setCountAddedComments] = React.useState<number>(0);

    React.useImperativeHandle(ref, () => ({
        emptyAddedComments: () => setCountAddedComments(0),
    }));

    const handleAddComment = (): void => {
        setCountAddedComments(n => n + 1);
    }

    const elementNumber = content.length;

    return (
        <div>
            <div>
                {content.map((commentUnit, i) => {
                    const unit = commentUnit as SourceCommentUnit;
                    return (
                        <div key={unit.body.commentId} className="flex-container">
                            {[...Array(unit.depth)].map((_) => (
                                <span key={_} className="comment-left-space"/>
                            ))}
                            <AdminCommentEditorUnit
                                postKey={postKey.append(i.toString())}
                                content={unit}
                            />
                        </div>
                    );
                })}

                {Array.from({length: countAddedComments}).map((_, index) => (
                    <div key={"key" + index.toString()}>
                        <AdminCommentEditorUnit
                            postKey={postKey.append((elementNumber + index).toString())}
                            content={null}
                        />
                    </div>
                ))}

                <p>
                    <button type="button" onClick={handleAddComment}>Add Comment</button>
                </p>
            </div>
        </div>
    );
});

