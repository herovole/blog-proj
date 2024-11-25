package org.herovole.blogproj.domain.abstractdatasource;

import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;

@EqualsAndHashCode
public class PagingRequest {

    private static final String API_KEY_PAGER_ITEMS_PER_PAGE = "itemsPerPage";
    private static final String API_KEY_PAGER_PAGE = "page";

    public static PagingRequest fromPostContent(PostContent postContent) {
        PostContent postItemsPerPage = postContent.getChildren(API_KEY_PAGER_ITEMS_PER_PAGE);
        int itemsPerPage = Integer.parseInt(postItemsPerPage.getValue());
        PostContent postPage = postContent.getChildren(API_KEY_PAGER_PAGE);
        int page = Integer.parseInt(postPage.getValue());
        return of(page, itemsPerPage);
    }

    public static PagingRequest of(int page, int size) {
        return new PagingRequest(page, size);
    }

    private final int page;
    private final int size;

    private PagingRequest(int page, int size) {
        this.page = page;
        this.size = size;
        if (page < 1 || size < 1) throw new DomainInstanceGenerationException();
    }

    public int getLimit() {
        return this.size;
    }

    public long getOffset() {
        return (long) this.size * (this.page - 1);
    }

    public long getLastIndexZeroOrigin() {
        return this.getOffset() + this.getLimit() - 1;
    }

    public boolean contains(long index) {
        final long offset = this.getOffset();
        final long end = this.getOffset() + this.getLimit();
        return offset <= index && index < end;
    }

    public PagingRequest nextPage() {
        return of(this.page + 1, this.size);
    }

}
