import {ImageService} from "./image/imageService";
import {GetResourcePrefixOutput} from "./image/getResourcePrefixOutput";
import {TagService} from "./tags/tagService";
import {SearchTagsInput} from "./tags/searchTagsInput";
import {SearchTagsOutput} from "./tags/searchTagsOutput";
import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";
import {Zurvan} from "../domain/zurvan";

export class ResourceManagement {
    private static readonly LOCAL_STORAGE_RESOURCE_PREFIX_KEY: string = "resourcePrefix";
    private static readonly ARTICLES_KEY: string = "articles";
    private static readonly SYSTEM_KEY: string = "system";
    private readonly imageService: ImageService = new ImageService();
    private imagePrefix: string | null = null;
    private isLoadingPrefix: boolean = false;

    private static readonly LOCAL_STORAGE_TOPIC_TAGS_KEY: string = "topicTags";
    private static readonly LOCAL_STORAGE_COUNTRY_TAGS_KEY: string = "countryTags";
    private readonly tagService: TagService = new TagService();
    private topicTags: TagUnits = TagUnits.empty();
    private countryTags: TagUnits = TagUnits.empty();
    private isLoadingTopicTags: boolean = false;
    private isLoadingCountryTags: boolean = false;

    //Updates when a specific article page is opened.
    private referredTopicTags: ReadonlyArray<string> = [];
    private referredCountryTags: ReadonlyArray<string> = [];

    private static instance: ResourceManagement | null;

    private constructor() {
    }

    static getInstance(): ResourceManagement {
        if (!ResourceManagement.instance) {
            ResourceManagement.instance = new ResourceManagement();
        }
        return ResourceManagement.instance;
    }

    public static initialize(): void {
        ResourceManagement.instance = null;
    }

    public getSiteNameJp(): string {
        return "輸入雑暇"
    }

    public getSiteNameEn(): string {
        return "Imported Leisure"
    }

    private async prefixWithSlash(): Promise<string> {
        while (this.isLoadingPrefix) {
            await Zurvan.delay(0.05);
        }
        if (this.imagePrefix) return this.imagePrefix + "/";
        if (localStorage.getItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY)) {
            this.imagePrefix = localStorage.getItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY) as string;
        } else {
            this.isLoadingPrefix = true;
            const apiResult: GetResourcePrefixOutput = await this.imageService.getResourcePrefix();
            if (apiResult.isSuccessful()) {
                localStorage.setItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY, apiResult.getPrefix());
                this.imagePrefix = localStorage.getItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY) as string;
            } else {
                console.error("failed to fetch api result");
            }
            this.isLoadingPrefix = false;
        }
        return this.imagePrefix + "/";
    }

    public async articlesImagePrefixWithSlash(): Promise<string> {
        const resourcePrefix = await this.prefixWithSlash();
        return resourcePrefix + ResourceManagement.ARTICLES_KEY + "/";
    }

    public async systemImagePrefixWithSlash(): Promise<string> {
        const resourcePrefix = await this.prefixWithSlash();
        return resourcePrefix + ResourceManagement.SYSTEM_KEY + "/";
    }

    public async getTopicTags(): Promise<TagUnits> {
        while (this.isLoadingTopicTags) {
            await Zurvan.delay(0.05);
        }
        if (!this.topicTags.isEmpty()) return this.topicTags;
        const lsTagUnits: TagUnits = TagUnits.ofJson(localStorage.getItem(ResourceManagement.LOCAL_STORAGE_TOPIC_TAGS_KEY));
        if (!lsTagUnits.isEmpty()) {
            this.topicTags = lsTagUnits;
        } else {
            this.isLoadingTopicTags = true;
            const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const topicOutput: SearchTagsOutput = await this.tagService.searchTopicTags(topicInput);
            if (topicOutput.isSuccessful()) {
                localStorage.setItem(ResourceManagement.LOCAL_STORAGE_TOPIC_TAGS_KEY, JSON.stringify(topicOutput.getTagUnits()));
                this.topicTags = topicOutput.getTagUnits();
            } else {
                console.error("failed to fetch topic tags");
            }
            this.isLoadingTopicTags = false;
        }
        return this.topicTags;
    }

    public async getCountryTags(): Promise<TagUnits> {
        while (this.isLoadingCountryTags) {
            await Zurvan.delay(0.05);
        }
        if (!this.countryTags.isEmpty()) return this.countryTags;
        const lsTagUnits = TagUnits.ofJson(localStorage.getItem(ResourceManagement.LOCAL_STORAGE_COUNTRY_TAGS_KEY));
        if (!lsTagUnits.isEmpty()) {
            this.countryTags = lsTagUnits;
        } else {
            this.isLoadingCountryTags = true;
            const countryInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const countryOutput: SearchTagsOutput = await this.tagService.searchCountries(countryInput);
            if (countryOutput.isSuccessful()) {
                localStorage.setItem(ResourceManagement.LOCAL_STORAGE_COUNTRY_TAGS_KEY, JSON.stringify(countryOutput.getTagUnits()));
                this.countryTags = countryOutput.getTagUnits();
            } else {
                console.error("failed to fetch country tags");
            }
            this.isLoadingCountryTags = false;
        }
        return this.countryTags;
    }

    public initReferredTopicTags(): void {
        this.referredTopicTags = [];
    }

    public setReferredTopicTags(tags: ReadonlyArray<string>): void {
        this.referredTopicTags = tags;
    }

    public getRandomReferredTopicTag(): string | null {
        return 0 < this.referredTopicTags.length ?
            this.referredTopicTags[Math.floor(Math.random() * this.referredTopicTags.length)] :
            null;
    }

    public initReferredCountryTags(): void {
        this.referredCountryTags = [];
    }

    public setReferredCountryTags(tags: ReadonlyArray<string>): void {
        this.referredCountryTags = tags;
    }

    public getRandomReferredCountryTag(): string | null {
        return 0 < this.referredCountryTags.length ?
            this.referredCountryTags[Math.floor(Math.random() * this.referredCountryTags.length)] :
            null;
    }
}
