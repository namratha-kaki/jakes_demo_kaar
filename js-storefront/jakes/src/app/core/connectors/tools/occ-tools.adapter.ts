import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToolsAdapter } from './tools.adapter';
import { Observable } from 'rxjs';
import { ToolData } from '../../models/tool.model';
import { ConverterService } from '@spartacus/core';
import { TOOLS_NORMALIZER } from './converters/tools.normalizer';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class OccToolsAdapter extends ToolsAdapter {
  private baseUrl = '/demojakesocc/sample';
  private accessToken = 'xYlchgoz5W43-_H4puGLKhFDL4g';

  constructor(private http: HttpClient, private converter: ConverterService) {
    super();
  }

  getAll(): Observable<ToolData[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.accessToken}`,
      'Accept': 'application/json'
    });

    return this.http.get<any[]>(`${this.baseUrl}/all`, { headers }).pipe(
      map((response) =>
        response.map((item) =>
          this.converter.convert(item, TOOLS_NORMALIZER)
        )
      )
    );
  }

  getByYear(year: number): Observable<ToolData[]> {
        const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.accessToken}`,
      'Accept': 'application/json'
    });
    
    return this.http.get<any[]>(`${this.baseUrl}/toolsByYear?year=${year}`, { headers }).pipe(
      map(response => response.map(item => this.converter.convert(item, TOOLS_NORMALIZER)))
    );
  }
}
