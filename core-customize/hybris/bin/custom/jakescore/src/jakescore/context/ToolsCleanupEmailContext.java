//package jakescore.context;
//
//import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
//import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
//import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
//import de.hybris.platform.core.model.c2l.LanguageModel;
//import de.hybris.platform.core.model.user.CustomerModel;
//import com.jakes.jakescore.model.ToolsCleanupEmailProcessModel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ToolsCleanupEmailContext extends AbstractEmailContext<ToolsCleanupEmailProcessModel>
//{
//    private static final Logger LOG = LoggerFactory.getLogger(ToolsCleanupEmailContext.class);
//
//    @Override
//    public void init(final ToolsCleanupEmailProcessModel processModel, final EmailPageModel emailPageModel)
//    {
//        super.init(processModel, emailPageModel);
//
//        LOG.info("Initializing ToolsCleanupEmailContext - deletedCount: {}", processModel.getDeletedCount());
//
//        // Add variables to the Velocity template
//        put("deletedCount", processModel.getDeletedCount());
//        put("siteName", getSite(processModel) != null ? getSite(processModel).getName() : "Default Site");
//    }
//
//    @Override
//    protected BaseSiteModel getSite(final ToolsCleanupEmailProcessModel processModel)
//    {
//        return processModel.getSite();
//    }
//
//    @Override
//    protected CustomerModel getCustomer(final ToolsCleanupEmailProcessModel processModel)
//    {
//        return null;
//    }
//
//    @Override
//    protected boolean isValidEmailContext()
//    {
//        // Override default validation — no customer needed for this email
//        return true;
//    }
//
//
//    @Override
//    protected LanguageModel getEmailLanguage(final ToolsCleanupEmailProcessModel processModel)
//    {
//        return processModel.getLanguage();
//    }
//}
package jakescore.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import com.jakes.jakescore.model.ToolsCleanupEmailProcessModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsCleanupEmailContext extends AbstractEmailContext<ToolsCleanupEmailProcessModel>
{
    private static final Logger LOG = LoggerFactory.getLogger(ToolsCleanupEmailContext.class);

    @Override
    public void init(final ToolsCleanupEmailProcessModel processModel, final EmailPageModel emailPageModel)
    {
        super.init(processModel, emailPageModel);

        LOG.info("ToolsCleanupEmailContext initialized, deletedCount={}", processModel.getDeletedCount());

        put("deletedCount", processModel.getDeletedCount());
        put("siteName", getSite(processModel) != null ? getSite(processModel).getName() : "Default Site");
        put("email", "admin@jakes.com");
        put("displayName", "System Admin");
    }

    @Override
    protected BaseSiteModel getSite(final ToolsCleanupEmailProcessModel processModel)
    {
        return processModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(final ToolsCleanupEmailProcessModel processModel)
    {
        return null; // No customer for this system email
    }

    @Override
    protected LanguageModel getEmailLanguage(final ToolsCleanupEmailProcessModel processModel)
    {
        return processModel.getLanguage();
    }
}
