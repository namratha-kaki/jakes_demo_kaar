import { NgModule } from '@angular/core';
import { ConfigModule, OccConfig } from '@spartacus/core';

const defaultToolsOccConfig: OccConfig = {
};

@NgModule({
  imports: [ConfigModule.withConfig(defaultToolsOccConfig)],
})
export class ToolsOccConfigModule {}
