import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JakesParagraphComponent } from './jakes-paragraph.component';
import { provideDefaultConfig, CmsConfig  } from "@spartacus/core";
import { RouterModule } from '@angular/router';
import { CmsParagraphModule } from "@spartacus/storefront";

@NgModule({
  declarations: [
    JakesParagraphComponent
  ],
  imports: [
    CommonModule, RouterModule, CmsParagraphModule
  ],
  providers: [
    provideDefaultConfig(<CmsConfig>{
      cmsComponents: {
        CMSParagraphComponent: {
          component: JakesParagraphComponent,
        },
      },
    }),
  ],
  exports : [JakesParagraphComponent]
})
export class JakesParagraphModule { }
