

export class CommentUnitList {

    static fromJsonStringList(arrayJsonStringList) {
        var arrayListOfComments = arrayJsonStringList.map(jsonString => CommentUnit.fromJsonString(jsonString));
        return new CommentUnitList(arrayListOfComments);
    }

    static empty() {
        return new CommentUnitList([]);
    }

    constructor(arrayListOfComments) {
        this.arrayListOfComments = arrayListOfComments ? arrayListOfComments : [];
    }

}
