import {CommentUnit} from "./commentUnit"

export interface UserCommentUnit extends CommentUnit {
    body: {
        commentSerialNumber: number;
        commentId: number;
        articleId: number;
        handleName: string;
        commentText: string;
        isHidden: boolean;
        referringCommentIds: ReadonlyArray<number>;
        likes: number;
        dislikes: number;
        dailyUserId: string;
        postTimestamp: string

        publicUserId: number | null;
        ip: string;
    };
    depth: number;
}
