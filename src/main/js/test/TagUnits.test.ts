import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";
import {TagUnit} from "../admin/fragment/atomic/tagselectingform/tagUnit";

test("restoring object", () => {
    const el1: TagUnit = new TagUnit({["id"]: "1", ["tagJapanese"]: "1jp", ["tagEnglish"]: "1en", ["articles"]: 0, ["lastUpdate"]: "20250101"});
    const el2: TagUnit = new TagUnit({["id"]: "2", ["tagJapanese"]: "2jp", ["tagEnglish"]: "2en", ["articles"]: 2, ["lastUpdate"]: "20250101"});
    const els = new Array<TagUnit>(el1, el2);
    const tagUnits = new TagUnits(els);
    const stringified = tagUnits.stringify();
    expect(TagUnits.ofJson(stringified).stringify()).toBe(stringified);
    console.log("expected : " + stringified);
    console.log("product : " + TagUnits.ofJson(stringified).stringify());
});

test("int ID", () => {
