import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ToolData } from '../../models/tool.model';
import { ToolsAdapter } from './tools.adapter';

@Injectable({ providedIn: 'root' })
export class ToolsConnector {
  constructor(private adapter: ToolsAdapter) {}

  getAll(): Observable<ToolData[]> {
    return this.adapter.getAll();
  }
}
