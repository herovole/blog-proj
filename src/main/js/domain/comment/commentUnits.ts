import {UserCommentUnit} from "./userCommentUnit"
import {SourceCommentUnit} from "./sourceCommentUnit"
import {CommentUnit} from "./commentUnit";

export class CommentUnits {

    static fromHashAsSourceCommentUnits(array) {
        console.log("comment unit list : " + JSON.stringify(array));
        const arrayListOfComments = array.map(child => SourceCommentUnit.fromHash(child));
        return new CommentUnits(arrayListOfComments);
    }

    static fromHashAsUserCommentUnits(array) {
        console.log("user comment unit list : " + JSON.stringify(array));
        const arrayListOfComments = array.map(child => UserCommentUnit.fromHash(child));
        return new CommentUnits(arrayListOfComments);
    }


    static empty() {
        return new CommentUnits([]);
    }

    private readonly arrayListOfComments: ReadonlyArray<CommentUnit>;

    constructor(arrayListOfComments) {
        this.arrayListOfComments = arrayListOfComments || [];
    }

    getElementNumber() {
        return this.arrayListOfComments.length;
    }

    getCommentUnit(index) {
        return this.arrayListOfComments[index];
    }

    sort() {
        let newList = new CommentUnits();
        for (const element of this.arrayListOfComments) {
            const unit = element;
            const refId = unit.getLatestReferringId();
            if (refId < 0) {
                newList = newList.appendUnit(unit);
                console.log("added : " + unit.commentId);
                console.log("comments : " + newList.getElementNumber());
            } else {
                const referredUnit = newList.getByInArticleCommentId(refId);
                if (!referredUnit) {
                    newList = newList.appendUnit(unit);
                    continue;
                }
                const appendedUnit = unit.applyDepth(referredUnit.depth + 1);
                newList = newList.insertRightBeforeNextConversation(refId, appendedUnit);
            }

        }
        return newList;

    }

    appendUnit(unit) {
        let newArray = this.arrayListOfComments.slice();
        console.log("slice length before : " + newArray.length);
        newArray.push(unit);
        console.log("slice length after : " + newArray.length);
        return new CommentUnits(newArray);
    }

    getByInArticleCommentId(commentId) {
        console.log("finding :" + commentId);
        for (let u of this.arrayListOfComments) {
            console.log("looking :" + u.getInArticleCommentId());
            if (u.getInArticleCommentId() === commentId) {
                return u;
            }
        }
        return null;
    }

    insertRightBeforeNextConversation(commentId, unit) {
        let originIndex = -1;
        let originUnit;
        console.log("insertRightBeforeNextConversation referring to " + commentId + " by " + unit.getInArticleCommentId());
        for (let i = 0; i < this.arrayListOfComments.length; i++) {
            if (this.arrayListOfComments[i].getInArticleCommentId() === commentId) {
                originIndex = i;
                originUnit = this.arrayListOfComments[i];
            }
        }
        console.log("originIndex " + originIndex);
        if (originIndex < 0) {
            console.log("originIndex not found");
            return this;
        }

        let newArray = this.arrayListOfComments.slice();
        for (let i = originIndex + 1; i < this.arrayListOfComments.length; i++) {
            console.log("index " + i + " depth " + this.arrayListOfComments[i].depth);
            if (this.arrayListOfComments[i].depth <= originUnit.depth) {
                newArray.splice(i, 0, unit);
                return new CommentUnits(newArray);
            }
        }
        newArray.push(unit);
        return new CommentUnits(newArray);

    }

}
