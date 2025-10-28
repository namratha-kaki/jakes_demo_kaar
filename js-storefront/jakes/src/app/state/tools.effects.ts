import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as ToolsActions from './tools.actions';
import {  map, catchError, switchMap } from 'rxjs/operators';
import { ToolsConnector } from '../core/connectors/tools/tools.connector';
import { of } from 'rxjs';

@Injectable()
export class ToolsEffects {
  constructor(private actions$: Actions, private connector: ToolsConnector) {}

  loadTools$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ToolsActions.loadTools),
      switchMap(() =>
        this.connector.getAll().pipe(
          map(tools => ToolsActions.loadToolsSuccess({ tools })),
          catchError(error => of(ToolsActions.loadToolsFailure({ error })))
        )
      )
    )
  );

  loadToolsByYear$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ToolsActions.loadToolsByYear),
      switchMap(action =>
        this.connector.getByYear(action.year).pipe(
          map(tools => ToolsActions.loadToolsSuccess({ tools })),
          catchError(error => of(ToolsActions.loadToolsFailure({ error })))
        )
      )
    )
  );

}
