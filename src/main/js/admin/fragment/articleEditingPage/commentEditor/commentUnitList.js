
import { CommentUnit } from './commentUnit'

export class CommentUnitList {

    static fromHash(array) {
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

    getCommentUnit(index) () {
        return this.arrayListOfComments[index];
    }

    sort() {
        var newList = new CommentUnitList();
        for(var i = 0; i < this.arrayListOfComments.length; i++) {
            var unit = this.arrayListOfComments[i];
            var refId = unit.getLatestReferringId();
            if(refId < 0) {
                newList = newList.appendUnit(unit);
            } else {
                var referredUnit = newList.getById(refId);
                var addedUnit = unit.applyDepth(referredUnit.depth + 1);
                newList = newList.insertRightBeforeNextSameLevel(refId, unit);
            }

        }
        return newList;

    }

    appendUnit(unit) {
        var newArray = this.arrayListOfComments.slice();
        newArray.push[unit];
        return new CommentUnitList(newArray);
    }

    getById(commentId) {
        for(var u of this.arrayListOfComments) {
            if(u.commentId == commentId) { return u; }
        }
        return null;
    }

    insertRightBeforeNextSameLevel(commentId, unit) {
        var originIndex;
        var originUnit;

        for(var i = 0; i < this.arrayListOfComments.length; i++) {
            if(this.arrayListOfComments[i].commentId == commentId) {
                originIndex = i;
                originUnit = this.arrayListOfComments[i];
            }
        }
        if(!originIndex) {
            return this;
        }

        var newArray = this.arrayListOfComments.slice();
        for(var i = originIndex + 1; i < this.arrayListOfComments.length; i++) {
            if(this.arrayListOfComments[i].depth == originUnit.depth) {
                newArray.splice(i, 0, unit);
            }
        }
        return new CommentUnitList(newArray);

    }

}
