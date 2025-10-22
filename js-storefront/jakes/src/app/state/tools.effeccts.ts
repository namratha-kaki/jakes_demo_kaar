// import { Injectable } from '@angular/core';
// import { Actions, createEffect, ofType } from '@ngrx/effects';
// import { ToolsService } from '../core/services/tools.service';
// import { loadTools, loadToolsSuccess, loadToolsFailure } from './tools.actions';
// import { mergeMap, map, catchError } from 'rxjs/operators';
// import { of } from 'rxjs';

// @Injectable()
// export class ToolsEffects {
//   constructor(private actions$: Actions, private toolsService: ToolsService) {}

//   loadTools$ = createEffect(() =>
//     this.actions$.pipe(
//       ofType(loadTools),
//       mergeMap(() =>
//         this.toolsService.getAllTools().pipe(
//           map(tools => loadToolsSuccess({ tools })),
//           catchError(error => of(loadToolsFailure({ error })))
//         )
//       )
//     )
//   );
// }



import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { loadTools, loadToolsSuccess, loadToolsFailure } from './tools.actions';
import { mergeMap, map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { ToolsConnector } from '../core/connectors/tools/tools.connector';

@Injectable()
export class ToolsEffects {
  constructor(private actions$: Actions, private toolsConnector: ToolsConnector) {}

  loadTools$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadTools),
      mergeMap(() =>
        this.toolsConnector.getAll().pipe(
          map(tools => loadToolsSuccess({ tools })),
          catchError(error => of(loadToolsFailure({ error })))
        )
      )
    )
  );
}

