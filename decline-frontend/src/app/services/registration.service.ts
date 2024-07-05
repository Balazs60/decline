import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { RegistrationData } from '../../types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private apiService: ApiService) { }

  register = (url: string, body: RegistrationData): Observable<any> => {
    return this.apiService.post(url, body);
  };
}
