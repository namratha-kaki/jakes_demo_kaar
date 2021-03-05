import { BrowserModule, BrowserTransferStateModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { translations, translationChunksConfig } from '@spartacus/assets';
import { B2bStorefrontModule, defaultB2bOccConfig } from '@spartacus/setup';
import { provideDefaultConfig } from '@spartacus/core';
import { AdministrationRootModule } from '@spartacus/organization/administration/root';
import { provideConfig } from '@spartacus/core';
import { organizationTranslations } from '@spartacus/organization/administration/assets';
import { organizationTranslationChunksConfig } from '@spartacus/organization/administration/assets';
import { OrderApprovalRootModule } from '@spartacus/organization/order-approval/root';
import { orderApprovalTranslations } from '@spartacus/organization/order-approval/assets';
import { orderApprovalTranslationChunksConfig } from '@spartacus/organization/order-approval/assets';
import { OccConfig } from '@spartacus/core';
import { environment } from './../environments/environment';

const occConfig: OccConfig = { backend: { occ: {} } };

// only provide the `occ.baseUrl` key if it is explicitly configured, otherwise the value of
// <meta name="occ-backend-base-url" > is ignored.
// This in turn breaks the call to the API aspect in public cloud environments
if (environment.occBaseUrl) {
  occConfig.backend.occ.baseUrl = environment.occBaseUrl;
}
if (environment.prefix) {
  occConfig.backend.occ.prefix = environment.prefix;
}
else {
  occConfig.backend.occ.prefix = '/occ/v2/';
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'serverApp' }),
    B2bStorefrontModule.withConfig({
      backend: occConfig.backend,
      featureModules: {
        organizationOrderApproval: {
        module: () => import('@spartacus/organization/order-approval').then(
          (m) => m.OrderApprovalModule
        ),
        },
        organizationAdministration: {
          module: () => import('@spartacus/organization/administration').then(
          (m) => m.AdministrationModule
        ),
        },
      },
      context: {
        urlParameters: ['baseSite', 'language', 'currency'],
        baseSite: ['powertools-spa'],
        currency: ['USD'],
        language: ['en'],
      },
      i18n: {
        resources: translations,
        chunks: translationChunksConfig,
        fallbackLang: 'en'
      },
      features: {
        level: '3.1'
      }
    }),
    BrowserTransferStateModule,
    AdministrationRootModule,
    OrderApprovalRootModule
  ],
  providers: [provideDefaultConfig(defaultB2bOccConfig),
    provideConfig({
      i18n: {
        resources: organizationTranslations,
        chunks: organizationTranslationChunksConfig,
      },
    }),
    
    provideConfig({
      i18n: {
        resources: orderApprovalTranslations,
        chunks: orderApprovalTranslationChunksConfig,
      },
    })],
  bootstrap: [AppComponent]
})
export class AppModule { }
