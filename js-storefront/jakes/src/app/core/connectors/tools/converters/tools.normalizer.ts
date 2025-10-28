import { Injectable, InjectionToken } from '@angular/core';
import { Converter } from '@spartacus/core';
import { ToolData } from '../../../models/tool.model';

export const TOOLS_NORMALIZER = new InjectionToken<Converter<any, ToolData>>('ToolsNormalizer');

@Injectable({ providedIn: 'root' })
export class ToolsNormalizer implements Converter<any, ToolData> {
  convert(source: any, target?: ToolData): ToolData {
    if (!target) {
      target = { code: '', name: '', description: '' };
    }
    target.code = source.code;
    target.name = source.name;
    target.description = source.description;
    target.releaseDate = source.releaseDate;
    return target;
  }
}
