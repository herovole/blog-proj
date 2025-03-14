import {CommentUnit} from "./commentUnit";

export interface SourceCommentUnit extends CommentUnit {
    body: {
        commentSerialNumber: number;
        commentId: number;
        commentText: string;
        country: string;
        isHidden: boolean
        referringCommentIds: ReadonlyArray<number> | null;
    };
    depth: number;
}
