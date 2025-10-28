import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ToolData } from '../../models/tool.model';

@Injectable({ providedIn: 'root' })
export abstract class ToolsAdapter {
  abstract getAll(): Observable<ToolData[]>;
   abstract getByYear(year: number): Observable<ToolData[]>;
}
