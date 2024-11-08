
export class ElementId {

    static CONNECTOR = ".";

    constructor(
        id, // String
        parent = null // ElementId
    ) {
        this.id = id;
        this.parent = parent;
    }

    append(
        child // String
    ) {
        return new ElementId(child, this);
    }

    toStringKey() {
        if(this.parent != null) return this.parent.toStringKey() + ElementId.CONNECTOR + this.id;
        return this.id;
    }

}
