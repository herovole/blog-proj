package org.herovole.blogproj.domain.abstractdatasource;

import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@EqualsAndHashCode
public class PagingRequest {

    private static final String API_KEY_PAGER_ITEMS_PER_PAGE = "itemsPerPage";
    private static final String API_KEY_PAGER_PAGE = "page";

    public static PagingRequest fromFormContent(FormContent formContent) {
        FormContent postItemsPerPage = formContent.getChildren(API_KEY_PAGER_ITEMS_PER_PAGE);
        int itemsPerPage = Integer.parseInt(URLDecoder.decode(postItemsPerPage.getValue(), StandardCharsets.UTF_8));
        FormContent postPage = formContent.getChildren(API_KEY_PAGER_PAGE);
        int page = Integer.parseInt(URLDecoder.decode(postPage.getValue(), StandardCharsets.UTF_8));
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
        if (page < 1 || size < 1) throw new DomainInstanceGenerationException("page " + page + "/size " + size);
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
