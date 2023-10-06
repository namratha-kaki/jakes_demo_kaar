package com.jakes.jakesstore.setup;

import com.jakes.jakesstore.constants.JakesstoreConstants;
import com.jakes.jakesstore.service.dataimport.impl.JakesSampleDataImportService;
import de.hybris.platform.commerceservices.dataimport.impl.CoreDataImportService;
import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.commerceservices.setup.data.ImportData;
import de.hybris.platform.commerceservices.setup.events.CoreDataImportedEvent;
import de.hybris.platform.commerceservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SystemSetup(extension = JakesstoreConstants.EXTENSIONNAME)
public class JakesstoreSystemSetup extends AbstractSystemSetup {

    public static final String JAKES = "jakes";

    private static final String IMPORT_CORE_DATA = "importCoreData";
    private static final String IMPORT_SAMPLE_DATA = "importSampleData";
    private static final String ACTIVATE_SOLR_CRON_JOBS = "activateSolrCronJobs";

    private CoreDataImportService coreDataImportService;
    private JakesSampleDataImportService jakesSampleDataImportService;

    @SystemSetupParameterMethod
    @Override
    public List<SystemSetupParameter> getInitializationOptions() {
        final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();

        params.add(createBooleanSystemSetupParameter(IMPORT_CORE_DATA, "Import Core Data", true));
        params.add(createBooleanSystemSetupParameter(IMPORT_SAMPLE_DATA, "Import Sample Data", true));
        params.add(createBooleanSystemSetupParameter(ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", true));

        return params;
    }

    /**
     * This method will be called during the system initialization.
     *
     * @param context the context provides the selected parameters and values
     */
    @SystemSetup(type = SystemSetup.Type.PROJECT, process = SystemSetup.Process.ALL)
    public void createProjectData(final SystemSetupContext context) {
        final List<ImportData> importData = new ArrayList<ImportData>();

        final ImportData jakesImportData = new ImportData();
        jakesImportData.setProductCatalogName(JAKES);
        jakesImportData.setContentCatalogNames(Arrays.asList(JAKES));
        jakesImportData.setStoreNames(Arrays.asList(JAKES));
        importData.add(jakesImportData);

        getCoreDataImportService().execute(this, context, importData);
        getEventService().publishEvent(new CoreDataImportedEvent(context, importData));

        getJakesSampleDataImportService().execute(this, context, importData);
        getJakesSampleDataImportService().importCommerceOrgData(context);

        getEventService().publishEvent(new SampleDataImportedEvent(context, importData));
    }

    protected CoreDataImportService getCoreDataImportService() {
        return coreDataImportService;
    }

    @Required
    public void setCoreDataImportService(CoreDataImportService coreDataImportService) {
        this.coreDataImportService = coreDataImportService;
    }

    protected JakesSampleDataImportService getJakesSampleDataImportService() {
        return jakesSampleDataImportService;
    }

    @Required
    public void setJakesSampleDataImportService(JakesSampleDataImportService jakesSampleDataImportService) {
        this.jakesSampleDataImportService = jakesSampleDataImportService;
    }
}
