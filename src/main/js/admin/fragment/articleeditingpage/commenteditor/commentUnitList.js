
import { CommentUnit } from './commentUnit'

export class CommentUnitList {

    static fromHash(array) {
        console.log("comment unit list : " + JSON.stringify(array));
        var arrayListOfComments = array.map(child => CommentUnit.fromHash(child));
        return new CommentUnitList(arrayListOfComments);
    }

    static empty() {
        return new CommentUnitList([]);
    }

    constructor(arrayListOfComments) {
        this.arrayListOfComments = arrayListOfComments ? arrayListOfComments : [];
    }

    getElementNumber() {
        return this.arrayListOfComments.length;
    }

    getCommentUnit(index) {
        return this.arrayListOfComments[index];
    }

    sort() {
        var newList = new CommentUnitList();
        for(var i = 0; i < this.arrayListOfComments.length; i++) {
            var unit = this.arrayListOfComments[i];
            var refId = unit.getLatestReferringId();
            if(refId < 0) {
                newList = newList.appendUnit(unit);
                console.log("added : " + unit.commentId);
                console.log("comments : " + newList.getElementNumber());
            } else {
                var referredUnit = newList.getById(refId);
                if(!referredUnit) {
                    newList = newList.appendUnit(unit);
                    continue;
                }
                var appendedUnit = unit.applyDepth(referredUnit.depth + 1);
                newList = newList.insertRightBeforeNextConversation(refId, appendedUnit);
            }

        }
        return newList;

    }

    appendUnit(unit) {
        var newArray = this.arrayListOfComments.slice();
        console.log("slice length before : " + newArray.length);
        newArray.push(unit);
        console.log("slice length after : " + newArray.length);
        return new CommentUnitList(newArray);
    }

    getById(commentId) {
        console.log("finding :" + commentId);
        for(var u of this.arrayListOfComments) {
            console.log("looking :" + u.commentId);
            if(u.commentId == commentId) { return u; }
        }
        return null;
    }

    insertRightBeforeNextConversation(commentId, unit) {
        var originIndex = -1;
        var originUnit;
        console.log("insertRightBeforeNextConversation referring to " + commentId + " by " + unit.commentId);
        for(var i = 0; i < this.arrayListOfComments.length; i++) {
            if(this.arrayListOfComments[i].commentId == commentId) {
                originIndex = i;
                originUnit = this.arrayListOfComments[i];
            }
        }
        console.log("originIndex " + originIndex);
        if(originIndex < 0) {
            console.log("originIndex not found");
            return this;
        }

        var newArray = this.arrayListOfComments.slice();
        for(var i = originIndex + 1; i < this.arrayListOfComments.length; i++) {
            console.log("index " + i + " depth " + this.arrayListOfComments[i].depth);
            if(this.arrayListOfComments[i].depth <= originUnit.depth) {
                newArray.splice(i, 0, unit);
                return new CommentUnitList(newArray);
            }
        }
        newArray.push(unit);
        return new CommentUnitList(newArray);

    }

}
