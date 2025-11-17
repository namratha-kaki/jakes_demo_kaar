import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomCartItemListComponent } from './custom-cart-item.component';
import { CartSharedModule } from '@spartacus/cart/base/components';
import { ConfigModule, I18nModule } from '@spartacus/core';

@NgModule({
  declarations: [CustomCartItemListComponent],
  imports: [
    CommonModule,
    CartSharedModule,
    I18nModule,   
    ConfigModule.withConfig({
      cmsComponents: {
        CartComponent: {
          component: CustomCartItemListComponent,
        },
      },
    }),
  ],
  exports: [CustomCartItemListComponent],
})
export class CustomCartItemModule {
  
}



