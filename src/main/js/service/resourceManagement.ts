import {ImageService} from "./image/imageService";
import {GetResourcePrefixOutput} from "./image/getResourcePrefixOutput";
import {TagService} from "./tags/tagService";
import {SearchTagsInput} from "./tags/searchTagsInput";
import {SearchTagsOutput} from "./tags/searchTagsOutput";
import {TagUnits} from "../admin/fragment/atomic/tagselectingform/tagUnits";

export class ResourceManagement {
    private static readonly LOCAL_STORAGE_RESOURCE_PREFIX_KEY: string = "resourcePrefix";
    private static readonly ARTICLES_KEY: string = "articles";
    private static readonly SYSTEM_KEY: string = "system";
    private readonly imageService: ImageService = new ImageService();
    private imagePrefix: string | null = null;

    private static readonly LOCAL_STORAGE_TOPIC_TAGS_KEY: string = "topicTags";
    private static readonly LOCAL_STORAGE_COUNTRY_TAGS_KEY: string = "countryTags";
    private readonly tagService: TagService = new TagService();
    private topicTags: TagUnits = TagUnits.empty();
    private countryTags: TagUnits = TagUnits.empty();

    private static instance: ResourceManagement;

    private constructor() {
    }

    static getInstance(): ResourceManagement {
        if (!ResourceManagement.instance) {
            ResourceManagement.instance = new ResourceManagement();
        }
        return ResourceManagement.instance;
    }

    private async prefixWithSlash(): Promise<string> {
        if (this.imagePrefix) return this.imagePrefix + "/";
        if (localStorage.getItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY)) {
            this.imagePrefix = localStorage.getItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY) as string;
        } else {
            const apiResult: GetResourcePrefixOutput = await this.imageService.getResourcePrefix();
            localStorage.setItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY, apiResult.getPrefix());
            this.imagePrefix = localStorage.getItem(ResourceManagement.LOCAL_STORAGE_RESOURCE_PREFIX_KEY) as string;
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
        if (!this.topicTags.isEmpty()) return this.topicTags;
        const lsTagUnits: TagUnits = TagUnits.ofJson(localStorage.getItem(ResourceManagement.LOCAL_STORAGE_TOPIC_TAGS_KEY));
        if (!lsTagUnits.isEmpty()) {
            this.topicTags = lsTagUnits;
        } else {
            const topicInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const topicOutput: SearchTagsOutput = await this.tagService.searchTopicTags(topicInput);
            if (topicOutput.isSuccessful()) {
                localStorage.setItem(ResourceManagement.LOCAL_STORAGE_TOPIC_TAGS_KEY, JSON.stringify(topicOutput.getTagUnits()));
                this.topicTags = topicOutput.getTagUnits();
            } else {
                console.error("failed to fetch topic tags");
            }
        }
        return this.topicTags;
    }

    public async getCountryTags(): Promise<TagUnits> {
        if (!this.countryTags.isEmpty()) return this.countryTags;
        const lsTagUnits = TagUnits.ofJson(localStorage.getItem(ResourceManagement.LOCAL_STORAGE_COUNTRY_TAGS_KEY));
        if (!lsTagUnits.isEmpty()) {
            this.countryTags = lsTagUnits;
        } else {
            const countryInput: SearchTagsInput = new SearchTagsInput(1, 10000, false);
            const countryOutput: SearchTagsOutput = await this.tagService.searchCountries(countryInput);
            if (countryOutput.isSuccessful()) {
                localStorage.setItem(ResourceManagement.LOCAL_STORAGE_COUNTRY_TAGS_KEY, JSON.stringify(countryOutput.getTagUnits()));
                this.countryTags = countryOutput.getTagUnits();
            } else {
                console.error("failed to fetch country tags");
            }
        }
        return this.countryTags;
    }

}
