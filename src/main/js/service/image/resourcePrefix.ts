import {ImageService} from "./imageService";
import {GetResourcePrefixOutput} from "./getResourcePrefixOutput";

export class ResourcePrefix {
    private static readonly LOCAL_STORAGE_KEY: string = "resourcePrefix";
    private static readonly ARTICLES_KEY: string = "articles";
    private static readonly SYSTEM_KEY: string = "system";
    private static instance: ResourcePrefix;

    private readonly imageService: ImageService;
    private prefix: string | null;

    private constructor() {
        this.imageService = new ImageService();
        this.prefix = null;
    }

    static getInstance(): ResourcePrefix {
        if (!ResourcePrefix.instance) {
            ResourcePrefix.instance = new ResourcePrefix();
        }
        return ResourcePrefix.instance;
    }

    private async withSlash(): Promise<string> {
        if (this.prefix) return this.prefix + "/";
        if (localStorage.getItem(ResourcePrefix.LOCAL_STORAGE_KEY)) {
            this.prefix = localStorage.getItem(ResourcePrefix.LOCAL_STORAGE_KEY) as string;
        } else {
            const apiResult: GetResourcePrefixOutput = await this.imageService.getResourcePrefix();
            localStorage.setItem(ResourcePrefix.LOCAL_STORAGE_KEY, apiResult.getPrefix());
            this.prefix = localStorage.getItem(ResourcePrefix.LOCAL_STORAGE_KEY) as string;
        }
        return this.prefix + "/";
    }

    public async articlesWithSlash(): Promise<string> {
        const resourcePrefix = await this.withSlash();
        return resourcePrefix + ResourcePrefix.ARTICLES_KEY + "/";
    }

    public async systemWithSlash(): Promise<string> {
        const resourcePrefix = await this.withSlash();
        return resourcePrefix + ResourcePrefix.SYSTEM_KEY + "/";
    }
}
