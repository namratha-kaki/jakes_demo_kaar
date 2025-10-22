import { JakesParagraphModule } from './components/jakes-paragraph/jakes-paragraph.module';
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { EffectsModule } from "@ngrx/effects";
import { StoreModule } from "@ngrx/store";
import { AppRoutingModule } from "@spartacus/storefront";
import { AppComponent } from './app.component';
import { SpartacusModule } from './spartacus/spartacus.module';
import { ToolsListComponent } from './tools-list/tools-list.component';
import { ToolsAdapter } from './core/connectors/tools/tools.adapter';
import { OccToolsAdapter } from './core/connectors/tools/occ-tools.adapter';
import { TOOLS_NORMALIZER, ToolsNormalizer } from './core/connectors/tools/converters/tools.normalizer';
import { ToolsListModule } from './tools-list/tools-list.module';
import { toolsReducer } from './state/tools.reducer';
import { ToolsEffects } from './state/tools.effeccts';
import { ConfigModule } from '@spartacus/core';

@NgModule({
  declarations: [
    AppComponent,
    ToolsListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    StoreModule.forRoot({toolsState:toolsReducer}),
    EffectsModule.forRoot([ToolsEffects]),
    SpartacusModule,
    JakesParagraphModule,
    ToolsListModule,
        ConfigModule.withConfig({
routing: {
    routes: {
        product: { paths: ['product/:name/custom-route/:productCode'] }
    }
},

})
  ],
  providers: [
        { provide: ToolsAdapter, useClass: OccToolsAdapter },
    { provide: TOOLS_NORMALIZER, useClass: ToolsNormalizer, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
