import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { ToolData } from '../core/models/tool.model';
import { Store, select } from '@ngrx/store';
import { loadTools, loadToolsByYear } from '../state/tools.actions';
import { AppState } from '../state/app.state';
import { selectLoading, selectYears } from '../state/tools.selectors';
import { ActivatedRoute } from '@angular/router';
import { ToolsConnector } from '../core/connectors/tools/tools.connector';

@Component({
  selector: 'app-tools-list',
  templateUrl: './tools-list.component.html',
  styleUrls: ['./tools-list.component.scss']
})
export class ToolsListComponent {
  tools$: Observable<ToolData[]>;
  years$: Observable<number[]> | undefined;
  loading$: Observable<boolean> | undefined;
  newTool: ToolData = { code: '', name: '', description: '', releaseDate: undefined };
  releaseDateString: string = '';

  editingTool: { [key: string]: boolean } = {};
  editedDates: { [key: string]: string } = {};

  constructor(
    private store: Store<AppState>,
    private route: ActivatedRoute,
    private connector: ToolsConnector
  ) {
    this.tools$ = store.pipe(select(state => state.toolsState.tools));
  }

  ngOnInit(): void {
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
  
  onSaveTool() {
    console.log('Saving tool...', this.newTool);
    
    if (this.releaseDateString) {
      this.newTool.releaseDate = new Date(this.releaseDateString + 'T12:00:00.000Z');
    } else {
      this.newTool.releaseDate = undefined;
    }
    
    console.log('Release date being sent:', this.newTool.releaseDate);
    
    this.connector.saveTool(this.newTool).subscribe({
      next: (response) => {
        console.log('Success response:', response);
        alert('Tool saved successfully');
        this.store.dispatch(loadTools()); 
        this.newTool = { code: '', name: '', description: '', releaseDate: undefined };
        this.releaseDateString = '';
      },
      error: err => {
        console.error('Full error:', err);
        alert('Failed to save tool: ' + JSON.stringify(err.error?.errors || err.message));
      }
    });
  }

  formatDateForDisplay(date: Date | undefined): string {
    if (!date) return '';
    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  enableDateEdit(tool: ToolData) {
    this.editingTool[tool.code] = true;
    this.editedDates[tool.code] = this.formatDateForDisplay(tool.releaseDate);
  }

  cancelDateEdit(tool: ToolData) {
    this.editingTool[tool.code] = false;
    delete this.editedDates[tool.code];
  }

  saveDateEdit(tool: ToolData) {
    const newDateString = this.editedDates[tool.code];
    
    if (!newDateString) {
      alert('Please enter a valid date');
      return;
    }

    const updatedTool: ToolData = {
      ...tool,
      releaseDate: new Date(newDateString + 'T12:00:00.000Z')
    };

    console.log('Updating tool date:', updatedTool);

    this.connector.saveTool(updatedTool).subscribe({
      next: (response) => {
        console.log('Date updated successfully:', response);
        alert('Date updated successfully');
        this.editingTool[tool.code] = false;
        delete this.editedDates[tool.code];
        this.store.dispatch(loadTools()); 
      },
      error: err => {
        console.error('Error updating date:', err);
        alert('Failed to update date: ' + (err.error?.error || err.message));
      }
    });
  }

  isEditing(tool: ToolData): boolean {
    return this.editingTool[tool.code] === true;
  }

  getEditedDate(tool: ToolData): string {
    return this.editedDates[tool.code] || '';
  }

  onDateInputChange(tool: ToolData, event: any) {
    this.editedDates[tool.code] = event.target.value;
  }
}
