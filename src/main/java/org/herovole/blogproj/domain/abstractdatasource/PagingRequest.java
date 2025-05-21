package org.herovole.blogproj.domain.abstractdatasource;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;

@ToString
@Getter
@EqualsAndHashCode
public class PagingRequest {

    private static final String API_KEY_PAGER_ITEMS_PER_PAGE = "itemsPerPage";
    private static final String API_KEY_PAGER_PAGE = "page";

    public static PagingRequest fromFormContent(FormContent formContent) {
        FormContent postItemsPerPage = formContent.getChildren(API_KEY_PAGER_ITEMS_PER_PAGE);
        int itemsPerPage = Integer.parseInt(postItemsPerPage.getValue());
        FormContent postPage = formContent.getChildren(API_KEY_PAGER_PAGE);
        int page = Integer.parseInt(postPage.getValue());
        return of(page, itemsPerPage);
    }

    public static PagingRequest of(int page, int itemsPerPage) {
        return new PagingRequest(page, itemsPerPage);
    }

    private final int page;
    private final int itemsPerPage;

    private PagingRequest(int page, int itemsPerPage) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        if (page < 1 || itemsPerPage < 1) throw new DomainInstanceGenerationException("page " + page + "/itemsPerPage " + itemsPerPage);
    }

    public int getLimit() {
        return this.itemsPerPage;
    }

    public long getOffset() {
        return (long) this.itemsPerPage * (this.page - 1);
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
        return of(this.page + 1, this.itemsPerPage);
    }

}
