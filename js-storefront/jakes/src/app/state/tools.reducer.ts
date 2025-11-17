import { createReducer, on } from '@ngrx/store';
import { loadTools, loadToolsSuccess, loadToolsFailure, loadToolsByYear } from './tools.actions';
import { ToolData } from '../core/models/tool.model'; 

export interface ToolsState {
  tools: ToolData[];
  loading: boolean;
  error: any;
}

export const initialState: ToolsState = {
  tools: [],
  loading: false,
  error: null
};

export const toolsReducer = createReducer(
  initialState,
  on(loadTools, state => ({ ...state, loading: true })),
  on(loadToolsByYear, state => ({ ...state, loading: true })),
  on(loadToolsSuccess, (state, { tools }) => ({ ...state, loading: false, tools })),
  on(loadToolsFailure, (state, { error }) => ({ ...state, loading: false, error }))
);
