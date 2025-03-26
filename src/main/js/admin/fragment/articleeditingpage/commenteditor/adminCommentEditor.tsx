import React, {forwardRef} from 'react';
import {AdminCommentEditorUnit} from './adminCommentEditorUnit'
import {ElementId} from "../../../../domain/elementId/elementId";
import {CommentUnit} from "../../../../domain/comment/commentUnit";
import {SourceCommentUnit} from "../../../../domain/comment/sourceCommentUnit";
import {ArticleService} from "../../../../service/articles/articleService";
import {ConvertImportedTextInput} from "../../../../service/articles/convertImportedTextInput";
import {ConvertImportedTextOutput} from "../../../../service/articles/convertImportedTextOutput";

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
    const [comments, setComments] = React.useState<ReadonlyArray<CommentUnit>>(content);
    const articleService: ArticleService = new ArticleService();
    const [countAddedComments, setCountAddedComments] = React.useState<number>(0);
    const [importedText, setImportedText] = React.useState<string | ArrayBuffer | null | undefined>(null);

    React.useImperativeHandle(ref, () => ({
        emptyAddedComments: () => setCountAddedComments(0),
    }));

    const handleAddComment = (): void => {
        setCountAddedComments(n => n + 1);
    }

    const handleImportedFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files ? event.target.files[0] : null;
        if (!file) return;

        const reader = new FileReader();
        reader.onload = (e) => {
            setImportedText(e.target?.result);
        };
        reader.readAsText(file);
    };

    const handleImportedFileUpload = async () => {
        if (!importedText || importedText instanceof ArrayBuffer) {
            alert("File Content Absent.");
            return;
        }

        const input : ConvertImportedTextInput = new ConvertImportedTextInput(importedText);
        const output : ConvertImportedTextOutput = await articleService.convertImportedText(input);
        setComments(output.getSourceComments());
    };


    const elementNumber = comments.length;

    return (
        <div>
            <div>
                <input type="file" accept=".txt" onChange={handleImportedFileChange}/>
                <button onClick={handleImportedFileUpload}>Import by .txt</button>
            </div>
            <div>
                {comments.map((commentUnit, i) => {
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

