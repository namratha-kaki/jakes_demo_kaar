package com.jakes.jakesstore.service.dataimport.impl;

import de.hybris.platform.commerceservices.dataimport.impl.SampleDataImportService;
import de.hybris.platform.core.initialization.SystemSetupContext;

public class JakesSampleDataImportService extends SampleDataImportService {

    /**
     * Imports the data related to Commerce Org.
     *
     * @param context the context used.
     */
    public void importCommerceOrgData(final SystemSetupContext context) {
        final String extensionName = context.getExtensionName();

        getSetupImpexService().importImpexFile(String.format("/%s/import/sampledata/commerceorg/user-groups.impex", extensionName),
                false);
    }

}
