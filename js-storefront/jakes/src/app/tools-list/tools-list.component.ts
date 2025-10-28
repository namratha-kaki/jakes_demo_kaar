import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { ToolData } from '../core/models/tool.model';
import { Store, select } from '@ngrx/store';
import { loadTools, loadToolsByYear } from '../state/tools.actions';
import { AppState } from '../state/app.state';
import { selectLoading, selectYears } from '../state/tools.selectors';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-tools-list',
  templateUrl: './tools-list.component.html',
  styleUrls: ['./tools-list.component.scss']
})
export class ToolsListComponent {
  tools$: Observable<ToolData[]>;
  years$: Observable<number[]> | undefined;
  loading$: Observable<boolean> | undefined;

  constructor(
    private store: Store<AppState>,
    private route: ActivatedRoute
  ) {
    this.tools$ = store.pipe(select(state => state.toolsState.tools));
  }

  ngOnInit(): void {
    // Read query param ?year=XXXX
    this.route.queryParamMap.subscribe(params => {
      const year = params.get('year');

      if (year) {
        this.store.dispatch(loadToolsByYear({ year: +year }));
      } else {
        this.store.dispatch(loadTools());
      }
    });
    this.years$ = this.store.pipe(select(selectYears));
    this.loading$ = this.store.pipe(select(selectLoading));
  }

  onYearSelect(year?: number) {
    if (year == null) {
      this.store.dispatch(loadTools());
    } else {
      this.store.dispatch(loadToolsByYear({ year }));
    }
  }
}

