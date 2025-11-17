package com.jakes.jakescore.events;

import com.jakes.jakescore.model.ToolsCleanupEmailProcessModel;
import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsCleanupEmailEventListener extends AbstractAcceleratorSiteEventListener<ToolsCleanupEmailEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ToolsCleanupEmailEventListener.class);

    private ModelService modelService;
    private BusinessProcessService businessProcessService;
    private BaseSiteService baseSiteService;
    private CommonI18NService commonI18NService;

    @Override
    protected void onSiteEvent(final ToolsCleanupEmailEvent event) {
        LOG.info("📩 Received ToolsCleanupEmailEvent for site: {} ({} tools deleted)",
                event.getSite() != null ? event.getSite().getUid() : "null",
                event.getDeletedCount());

        try {
            // Create unique process instance
            final String processCode = "ToolsCleanupEmailProcess-" + System.currentTimeMillis();
            final ToolsCleanupEmailProcessModel processModel =
                    (ToolsCleanupEmailProcessModel) businessProcessService.createProcess(
                            processCode, "toolsCleanupEmailProcess");

            BaseSiteModel site = event.getSite() != null ? event.getSite() : baseSiteService.getCurrentBaseSite();
            processModel.setSite(site);

            if (site != null && site.getStores() != null && !site.getStores().isEmpty()) {
                processModel.setStore(site.getStores().iterator().next());
            } else {
                LOG.warn("⚠️ No store found for site '{}'", site != null ? site.getUid() : "null");
            }

            processModel.setLanguage(commonI18NService.getCurrentLanguage());
            processModel.setCurrency(commonI18NService.getCurrentCurrency());
            processModel.setDeletedCount(event.getDeletedCount());

            modelService.save(processModel);
            businessProcessService.startProcess(processModel);

            LOG.info("✅ Started ToolsCleanupEmailProcess [{}] for site '{}' ({} removed tools)",
                    processCode,
                    site != null ? site.getUid() : "null",
                    event.getDeletedCount());
        } catch (Exception e) {
            LOG.error("❌ Failed to start ToolsCleanupEmailProcess", e);
        }
    }

    @Override
    protected SiteChannel getSiteChannelForEvent(final ToolsCleanupEmailEvent event) {
        return event.getSite() != null ? event.getSite().getChannel() : null;
    }

    @Override
    protected boolean shouldHandleEvent(final ToolsCleanupEmailEvent event) {
        if (event.getSite() == null) {
            LOG.info("⚠️ ToolsCleanupEmailEvent has no site — handling event anyway.");
            return true;
        }
        return super.shouldHandleEvent(event);
    }

    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }

    public void setBusinessProcessService(final BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public void setBaseSiteService(final BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    public void setCommonI18NService(final CommonI18NService commonI18NService) {
        this.commonI18NService = commonI18NService;
    }
}

