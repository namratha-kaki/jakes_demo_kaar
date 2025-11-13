package com.jakes.jakescore.cronjob;

import com.jakes.jakescore.events.ToolsCleanupEmailEvent;
import com.jakes.jakescore.model.ToolsCleanupCronJobModel;
import com.jakes.jakescore.model.ToolsItemModel;
import com.jakes.jakescore.service.ToolsService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.site.BaseSiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ToolsCleanUpJob extends AbstractJobPerformable<ToolsCleanupCronJobModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ToolsCleanUpJob.class);

    private ToolsService toolsService;
    private EventService eventService;
    private BaseSiteService baseSiteService;

    @Override
    public PerformResult perform(ToolsCleanupCronJobModel cronJob) {
        LOG.info("Starting ToolsCleanupJob");

        boolean dryRun = Boolean.TRUE.equals(cronJob.getDryRun());
        int daysBeforeRemoval = cronJob.getDaysBeforeRemoval() != null ? cronJob.getDaysBeforeRemoval() : 0;
        int batchSize = cronJob.getBatchSize() != null ? cronJob.getBatchSize() : 100;

        LOG.info("Configuration - DryRun: {}, DaysBeforeRemoval: {}, BatchSize: {}",
                dryRun, daysBeforeRemoval, batchSize);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -daysBeforeRemoval);
            Date cutoffDate = calendar.getTime();
            LOG.info("Cutoff date: {} (Tools released before this date will be removed)", cutoffDate);

            List<ToolsItemModel> allTools = toolsService.getAllTools();
            int removedCount = 0;
            int processedCount = 0;
            int totalTools = allTools.size();

            for (ToolsItemModel tool : allTools) {
                if (processedCount >= batchSize) {
                    LOG.info("Batch size limit ({}) reached. Stopping processing.", batchSize);
                    break;
                }

                Date releaseDate = tool.getReleaseDate();
                if (releaseDate != null && releaseDate.before(cutoffDate)) {
                    if (dryRun) {
                        LOG.info("DRY RUN Would remove tool: {} (Code: {}, Release Date: {})",
                                tool.getName(), tool.getCode(), releaseDate);
                    } else {
                        LOG.info("Removing tool: {} (Code: {}, Release Date: {})",
                                tool.getName(), tool.getCode(), releaseDate);
                        toolsService.removeTool(tool);
                    }
                    removedCount++;
                    processedCount++;
                }
            }

            if (dryRun) {
                LOG.info("ToolsCleanupJob [DRY RUN] completed. Would remove {} out of {} tools",
                        removedCount, totalTools);
            } else {
                LOG.info("ToolsCleanupJob completed successfully. Removed {} out of {} tools",
                        removedCount, totalTools);
            }

            if (!dryRun && removedCount>0) {
                BaseSiteModel site = baseSiteService.getBaseSiteForUID("jakes");
                ToolsCleanupEmailEvent event = new ToolsCleanupEmailEvent(site, removedCount);
                eventService.publishEvent(event);
                LOG.info("✅ Published ToolsCleanupEmailEvent for {} removed tools", removedCount);
            } else if (removedCount==0) {
                LOG.info("No tools were removed so Email event will not be triggered");
                
            }

            LOG.info("ToolsCleanupJob Finished");
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);

        } catch (Exception e) {
            LOG.error(" Error during ToolsCleanupJob execution", e);
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
        }
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }
}
