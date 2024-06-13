import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Options } from '../../types';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private httpCleint: HttpClient
  ) { }

  get<T>(url:string):Observable<T>{
    return this.httpCleint.get<T>(url) as Observable<T>;
  }
}
