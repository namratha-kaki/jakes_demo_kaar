import { createAction, props } from '@ngrx/store';
import { ToolData } from '../core/models/tool.model'; 

export const loadTools = createAction('[Tools] Load Tools');
export const loadToolsSuccess = createAction('[Tools] Load Tools Success', props<{ tools: ToolData[] }>());
export const loadToolsFailure = createAction('[Tools] Load Tools Failure', props<{ error: any }>());
export const loadToolsByYear = createAction('[Tools] Load Tools By Year', props<{ year: number }>());



