# Local Environment Setup
## For SAP Commerce
* Download SAP Commerce 2211.9 from the SAP Software Center `https://me.sap.com/softwarecenter`.
* Setup SAP Commerce and its dependencies (e.g., JDK). See `https://help.sap.com/docs/SAP_COMMERCE_CLOUD_PUBLIC_CLOUD/8be4e0379b294fbabf36d26e7ca4169d/8c05883f86691014a139ef08f6f77404.html` for reference.
* Setup custom extensions. 
  * In the git repository directory, custom extensions are located in `/core-customize/hybris/bin/custom`. Create a symbolic link for `{git repo directory}/core-customize/hybris/bin/custom` to a corresponding folder in `{SAP Commerce instance directory}/hybris/bin`.
* Setup config files.
  * Create symbolic links for `{git repo directory}/core-customize/hybris/config/local.properties` and `{git repo directory}/core-customize/hybris/config/localextensions.xml` to the corresponding files in `{SAP Commerce instance directory}/hybris/config`
# Symbolic link generation for custom folder :
* ln -s /home/dsg/workspaces/jakesptools/jakes-sapcommerce/core-customize/hybris/bin/custom/jakesbackoffice /home/dsg/workspaces/jakesptools/commerce/hybris/bin/custom/jakesbackoffice
# Symbolic link generation for config folder :
* ln -s ~/home/dsg/workspaces/jakesptools/jakes-sapcommerce/core-customize/hybris/config/ ~/home/dsg/workspaces/jakesptools/commerce/hybris
* Build and Initialize SAP Commerce as usual.

## For Composable Storefront (Spartacus)
* Install Composable Storefront development dependencies. See
`https://help.sap.com/docs/SAP_COMMERCE_COMPOSABLE_STOREFRONT/cfcf687ce2544bba9799aa6c8314ecd0/5de67850bd8d487181fef9c9ba59a31d.html#front-end-development-requirements`.
  * For a reference, using `Angular CLI 15.2.9`, `Node.js 18.6.1`, and `npm 9.8.0` works at the time of writing.
* Install Composable Storefront Libraries from the Repository Based Shipment Channel. See `https://help.sap.com/docs/SAP_COMMERCE_COMPOSABLE_STOREFRONT/cfcf687ce2544bba9799aa6c8314ecd0/5de67850bd8d487181fef9c9ba59a31d.html#installing-composable%0Astorefront-libraries-from-the-repository-based-shipment-channel`.
  * Note: Angular application root directory in the link above refers to `{git repo directory}/js-storefront/jakes`
* Navigate to `{git repo directory}/js-storefront/jakes`.
* Execute `npm install` to install dependencies.
* Execute `npm start`. The storefront can be access by browsing to `http://localhost:4200`.
