import {ElementId} from "./elementId";

export class RealElementId implements ElementId {

    static readonly CONNECTOR: string = ".";

    id: string;
    parent: ElementId;

    constructor(
        id: string,
        parent: ElementId
    ) {
        this.id = id;
        this.parent = parent;
    }

    append(
        child: string
    ): ElementId {
        return new RealElementId(child, this);
    }

    toStringKey(): string {
        if (this.parent != null) return this.parent.toStringKey() + RealElementId.CONNECTOR + this.id;
        return this.id;
    }

}
