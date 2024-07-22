import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { AnswerStatisticData } from '../../types';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private apiService: ApiService) { }

  getAnswerStatistic = (url: string): Observable<AnswerStatisticData> => {
    return this.apiService.get(url)
  }
}
