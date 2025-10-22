// import { Component } from '@angular/core';
// import { Observable } from 'rxjs';
// import { ToolData } from '../core/models/tool.model';
// import { AppState } from '../state/app.state';
// import { select, Store } from '@ngrx/store';
// import { loadTools } from '../state/tools.actions';

// @Component({
//   selector: 'app-tools-list',
//   templateUrl: './tools-list.component.html',
//   styleUrls: ['./tools-list.component.scss']
// })
// export class ToolsListComponent {

//     tools$: Observable<ToolData[]>;

//   constructor(private store: Store<AppState>) {
//     this.tools$ = store.pipe(select(state => state.toolsState.tools));
//   }

//   ngOnInit(): void {
//     this.store.dispatch(loadTools());
//   }
// }
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { ToolData } from '../core/models/tool.model';
import { Store, select } from '@ngrx/store';
import { loadTools } from '../state/tools.actions';
import { AppState } from '../state/app.state';

@Component({
  selector: 'app-tools-list',
  templateUrl: './tools-list.component.html',
  styleUrls: ['./tools-list.component.scss']
})
export class ToolsListComponent {
  tools$: Observable<ToolData[]>;

  constructor(private store: Store<AppState>) {
    this.tools$ = store.pipe(select(state => state.toolsState.tools));
  }

  ngOnInit(): void {
    this.store.dispatch(loadTools());
  }

}
