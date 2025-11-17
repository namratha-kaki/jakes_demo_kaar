import { ToolData } from '../core/models/tool.model';

export interface ToolsState {
  tools: ToolData[];
  loading: boolean;
  error: any;
}

export const initialToolsState: ToolsState = {
  tools: [],
  loading: false,
  error: null,
};
