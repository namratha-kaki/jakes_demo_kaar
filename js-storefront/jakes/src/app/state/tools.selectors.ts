import { createSelector, createFeatureSelector } from '@ngrx/store';
import { ToolsState } from './tools.reducer';

export const selectToolsState = createFeatureSelector<ToolsState>('toolsState');

export const selectAllTools = createSelector(selectToolsState, state => state.tools);
export const selectLoading = createSelector(selectToolsState, state => state.loading);
export const selectYears = createSelector(selectAllTools, tools => {
  const years = new Set<number>();
  tools.forEach(t => {
    if (t.releaseDate) {
      const year = new Date(t.releaseDate).getFullYear();
      if (!isNaN(year)) years.add(year);
    }
  });
  return Array.from(years).sort((a, b) => b - a);
});
