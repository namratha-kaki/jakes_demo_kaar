import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToolsAdapter } from './tools.adapter';
import { Observable } from 'rxjs';
import { ToolData } from '../../models/tool.model';
import { ConverterService } from '@spartacus/core';
import { TOOLS_NORMALIZER } from './converters/tools.normalizer';
import { map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class OccToolsAdapter extends ToolsAdapter {
  private readonly baseUrl = '/jakeocc/jakes';
  private readonly accessToken = environment.authToken;
  private readonly headers = new HttpHeaders({
    'Authorization': `Bearer ${this.accessToken}`,
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  });

  constructor(
    private http: HttpClient,
    private converter: ConverterService
  ) {
    super();
  }

  getAll(): Observable<ToolData[]> {
    return this.http.get<any[]>(`${this.baseUrl}/tools`, { headers: this.headers }).pipe(
      map((response) =>
        response.map((item) =>
          this.converter.convert(item, TOOLS_NORMALIZER)
        )
      )
    );
  }

  getByYear(year: number): Observable<ToolData[]> {
    return this.http.get<any[]>(`${this.baseUrl}/tools/year/${year}`, { headers: this.headers }).pipe(
      map((response) =>
        response.map((item) =>
          this.converter.convert(item, TOOLS_NORMALIZER)
        )
      )
    );
  }

  saveTool(tool: ToolData): Observable<ToolData> {
    console.log('Saving Tool');
    console.log('Tool object:', tool);
    console.log('Stringified:', JSON.stringify(tool, null, 2));

    return this.http.post<any>(`${this.baseUrl}/tools/save`, tool, {
      headers: this.headers,
      observe: 'response'
    }).pipe(
      map((response) => {
        console.log('Response:', response);
        return response.body;
      })
    );
  }
}