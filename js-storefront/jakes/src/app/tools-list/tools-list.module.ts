import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CmsConfig, ConfigModule } from '@spartacus/core';
import { ToolsListComponent } from './tools-list.component';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
                 ConfigModule.withConfig(<CmsConfig>{
              cmsComponents: {
                CMSParagraphComponent: {
                  component: ToolsListComponent
                },
              }
            })
  ]
})
export class ToolsListModule { }
