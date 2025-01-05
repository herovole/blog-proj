import {ElementId} from "./elementId";
import {RealElementId} from "./realElementId";

export class RootElementId implements ElementId {

    static valueOf(elementId: string): ElementId {
        return new RootElementId(elementId);
    }

    id: string;

    constructor(id: string) {
        this.id = id;
    }

    append(child: string): ElementId {
        return new RealElementId(child, this);
    }

    toStringKey(): string {
        return this.id;
    }
}