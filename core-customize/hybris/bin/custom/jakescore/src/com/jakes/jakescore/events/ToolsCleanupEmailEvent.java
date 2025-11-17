package com.jakes.jakescore.events;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

public class ToolsCleanupEmailEvent extends AbstractCommerceUserEvent {

    private int deletedCount;

    public ToolsCleanupEmailEvent(final BaseSiteModel site, final int deletedCount) {
        setSite(site);
        this.deletedCount=deletedCount;
    }

    public int getDeletedCount() {
        return deletedCount;
    }

    public void setDeletedCount(int deletedCount) {
        this.deletedCount = deletedCount;
    }
}
