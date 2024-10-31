package org.herovole.blogproj.domain.abstractdatasource;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class PagingRequest {
    private final int page;
    private final int size;

    public PagingRequest(int page, int size) throws DomainInstanceGenerationException {
        this.page = page;
        this.size = size;
        if(page < 0 || size < 1) throw new DomainInstanceGenerationException();
    }

    public boolean isInPreparation() {
        return this.page == 0;
    }

    public int getLimit() {return this.size; }

    public long getOffset() {return (long)this.size * (this.page - 1); }

    public long getLastIndexZeroOrigin() {
        return this.getOffset() + this.getLimit() - 1;
    }

    public boolean contains(long index) {
        final long offset = this.getOffset();
        final long end = this.getOffset() + this.getLimit();
        return offset <= index && index < end;
    }

    public PagingRequest nextPage() throws DomainInstanceGenerationException {
        return new PagingRequest(this.page + 1, this.size);
    }

}
